package frc.robot;

import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class autoTarget extends Command {

    private final CommandSwerveDrivetrain m_drive;
    private final SwerveRequest.FieldCentric m_request = new SwerveRequest.FieldCentric();

    private static final double kP = 0.05; // tune this if rotation is too fast/slow

    public autoTarget(CommandSwerveDrivetrain drive) {
        m_drive = drive;
    }

    public void targetTag() {
        int tagID = limelight.getTagId();
        double turnOffset = limelight.getCamXNC();

        if (tagID == 9) {
            if (turnOffset > 0.5) {
                m_drive.setControl(
                    m_request.withVelocityX(0.0)
                            .withVelocityY(0.0)
                            .withRotationalRate(-turnOffset * kP)
                );
            } else if (turnOffset < -0.5) {
                m_drive.setControl(
                    m_request.withVelocityX(0.0)
                            .withVelocityY(0.0)
                            .withRotationalRate(-turnOffset * kP)
                );
            } else {
                m_drive.setControl(
                    m_request.withVelocityX(0.0)
                            .withVelocityY(0.0)
                            .withRotationalRate(0.0)
                );
            }
        }
    }
}