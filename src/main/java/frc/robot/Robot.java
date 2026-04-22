// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.sound.midi.Sequence;

import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.DriveMotorArrangement;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.Pigeon2.*;
import frc.robot.*;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.ctre.phoenix6.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private final RobotContainer m_robotContainer;

    private final Pigeon2 mainGyro = new Pigeon2(0);
    private final XboxController Controller1 = new XboxController(0);
    
    Pose2d poseA = new Pose2d();
Pose2d poseB = new Pose2d();

StructPublisher<Pose2d> publisher = NetworkTableInstance.getDefault()
  .getStructTopic("MyPose", Pose2d.struct).publish();
StructArrayPublisher<Pose2d> arrayPublisher = NetworkTableInstance.getDefault()
  .getStructArrayTopic("MyPoseArray", Pose2d.struct).publish();

    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    public Robot() {
        m_robotContainer = new RobotContainer();
        
    }

    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run(); 
        
        publisher.set(poseA);
        arrayPublisher.set(new Pose2d[] {poseA, poseB});

       
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {


        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
            SmartDashboard.putString("run","code is running");
            System.out.print("[DEBUG] autonomous init starting.");

        }            
        System.out.print("[DEBUG] autonomous init ran.");

    }

    @Override
    public void autonomousPeriodic() {
        //Shooter.shooterForwardSlow();
        //Intake.intakeFoward();
        
    }

    @Override
    public void autonomousExit() {
    //Shooter.shooterStop();
    //Intake.intakeStop();

    }

    boolean fc = false;

    @Override
    public void teleopInit() {
        RobotContainer.drivetrain.seedFieldCentric();

        // RobotContainer.drivetrain.seedFieldCentric(
        //     DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Blue
        //         ?Rotation2d.kZero
        //         :Rotation2d.fromDegrees(180)
        // );
    //RobotContainer.drivetrain.seedFieldCentric(Rotation2d.kZero);    
    //RobotContainer.drivetrain.runOnce(RobotContainer.drivetrain::seedFieldCentric);
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }

    @Override
    public void teleopPeriodic() {

        //Controller Buttones
         if (Controller1.getLeftTriggerAxis() > 0.5){
      Shooter.shooterForwardSlow();
    }   
      else if (Controller1.getRightTriggerAxis() > 0.5){
      Shooter.shooterFowardFast();
    }   
    else if (Controller1.getAButton()){
        Shooter.Unstuck();
    }
    else {
        Shooter.shooterStop();
    }

    if (Controller1.getXButtonPressed()){
      Intake.intakeFoward();
    }
    else if(Controller1.getXButtonReleased()){
      Intake.intakeStop();
    }

    if (Controller1.getYButtonPressed()){
        Shooter.Unstuck();
        Intake.intakeReverse();
    }
    else if (Controller1.getYButtonReleased()){
        Shooter.shooterStop();
        Intake.intakeStop();
    }

    if (Controller1.getRightBumperButtonPressed()){
        Intake.intakeDirectionUp();
    }
    if (Controller1.getLeftBumperButtonPressed()){
        Intake.intakeDirectionDown();
    }
    if (Controller1.getStartButton()){
        Intake.intakeDirectionStop();
    }

    if (Controller1.getLeftStickButtonPressed()){
    }

    if (Controller1.getBButton()){
        Shooter.Diddy();
    }
    }

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}

}

