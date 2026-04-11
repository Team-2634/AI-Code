// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.swerve.SwerveRequest;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.internal.DriverStationModeThread;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandIntake;
import frc.robot.subsystems.CommandShooter;
//import frc.robot.subsystems.CommandShooter;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {

    public static Pigeon2 mainGyro = new Pigeon2(0);
    private final XboxController Controller1 = new XboxController(0);

    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final static CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
//    private final SendableChooser<Command> m_chooser = new SendableChooser<>();

    public RobotContainer() {
        //m_chooser.setDefaultOption("Testing Auto", getAutonomousCommand());

        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        // joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        // if(joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric))){
            // SmartDashboard.putBoolean("Mark", true); 
            // drivetrain.runOnce(drivetrain::seedFieldCentric);
            // SmartDashboard.putBoolean("Mark2", true); 

        // }
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    // AUTO CODE FUNCTIONS

    public Command getAutonomousDriveCommand() { // basic driving auto
        SmartDashboard.putString("status", "running");
        System.out.print("[DEBUG] autonomous init ran.");

        // Simple drive forward auton
        final var idle = new SwerveRequest.Idle();
        return Commands.sequence(
            // Reset our field centric heading to match the robot
            // facing away from our alliance station wall (0 deg).
            drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
            // Then slowly drive forward (away from us) for 5 seconds.
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-0.5 /* * MaxSpeed */)
                    .withVelocityY(0 /* * MaxSpeed */)
                    .withRotationalRate(0 * MaxAngularRate)
            )
            .withTimeout(0.5),
            // Finally idle for the rest of auton
            drivetrain.applyRequest(() -> idle)
        );
            
    }
 
    public Command autoShooter(double speed) { // basic shooter motor
        return Commands.sequence(     
            CommandShooter.autoShoot(speed)
            .withTimeout(5.0),
            CommandShooter.autoShoot(0)
        );        
    }

    public Command autoIntake(){ // basic intake motor auto
        return Commands.sequence(
            CommandIntake.runIntakeMotor().withTimeout(5.0),
            CommandIntake.stopIntakeMotor()
        );
    }

    // Bot is placed at the left start, # degrees right of the wall.
    public Command autoCommandLeftSpawn(){ 
            SmartDashboard.putString("AutoSequence","LeftSpawn");

            return Commands.sequence( 
                drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),

              drivetrain.applyRequest(() -> // The first drive command towards us
                drive.withVelocityX(0.25)
                     .withVelocityY(0.25)
                     .withRotationalRate(0)
              ).withTimeout(3),

              drivetrain.applyRequest(() -> // The second drive command away from the wall
                drive.withVelocityX(-0.25)
                     .withVelocityY(-0.25)
              ).withTimeout(3),

              CommandShooter.autoShoot(0.25).withTimeout(5.0), // launch balls (TODO: limelight to give accurate shooting)

              drivetrain.applyRequest(() -> // realign robt with ramp
                drive.withRotationalRate(0.25)
              ).withTimeout(5),

              drivetrain.applyRequest(() -> // drive onto the ramp and field
                drive.withVelocityX(0.25)
                  .withVelocityY(0)
              ).withTimeout(5)
            
        );
        
    }
/*
    public Command autoCommandRightSpawn(){

    }

    public Command autoCommandCenterSpawn(){

    }

*/
}
