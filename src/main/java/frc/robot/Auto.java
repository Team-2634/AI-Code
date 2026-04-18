package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.*;






public class Auto extends Command {

    private final static CommandSwerveDrivetrain drivetrain = RobotContainer.drivetrain;

    private final static SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(0.0)
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final double TARGET_Y = 39.4; // 1 meter in inches
    private final double TARGET_X = 0.0;
    private final double TOLERANCE = 1.0; // inches
    private final static double DRIVE_SPEED = 0.3; // meters per second

    public Auto() {
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if (limelight.getTagId() == 9) {
            double[] pos = limelight.getRobotPos();
            double x = pos[0];
            double y = pos[1];

            if (y > TARGET_Y + TOLERANCE) {
                drivetrain.applyRequest(() ->
                    drive.withVelocityX(DRIVE_SPEED)
                         .withVelocityY(0.0)
                         .withRotationalRate(0.0)
                );
            } else if (y < TARGET_Y - TOLERANCE) {
                drivetrain.applyRequest(() ->
                    drive.withVelocityX(-DRIVE_SPEED)
                         .withVelocityY(0.0)
                         .withRotationalRate(0.0)
                );
            } else if (x > TARGET_X + TOLERANCE) {
                drivetrain.applyRequest(() ->
                    drive.withVelocityX(0.0)
                         .withVelocityY(-DRIVE_SPEED)
                         .withRotationalRate(0.0)
                );
            } else if (x < TARGET_X - TOLERANCE) {
                drivetrain.applyRequest(() ->
                    drive.withVelocityX(0.0)
                         .withVelocityY(DRIVE_SPEED)
                         .withRotationalRate(0.0)
                );
            }
        }
    }

    @Override
    public boolean isFinished() {
        if (limelight.getTagId() != 9) return false;

        double[] pos = limelight.getRobotPos();
        return Math.abs(pos[0] - TARGET_X) <= TOLERANCE &&
               Math.abs(pos[1] - TARGET_Y) <= TOLERANCE;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.applyRequest(() ->
            drive.withVelocityX(0.0)
                 .withVelocityY(0.0)
                 .withRotationalRate(0.0)
        );
    }

    public static void AutoFront(){
        drivetrain.applyRequest(() ->
                    drive.withVelocityX(DRIVE_SPEED)
                         .withVelocityY(0.-1)
                         .withRotationalRate(0.0)
                );

        Timer.delay(2);
        
        drivetrain.applyRequest(() ->
                    drive.withVelocityX(DRIVE_SPEED)
                         .withVelocityY(0.0)
                         .withRotationalRate(0.0)
                );


    }
}