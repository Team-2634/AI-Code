package frc.robot;

import static edu.wpi.first.units.Units.*;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class autoTarget extends Command {

    private final CommandXboxController joystick = new CommandXboxController(0);
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed

    private final CommandSwerveDrivetrain m_drive;
    private final SwerveRequest.FieldCentric m_request = new SwerveRequest.FieldCentric();

    private static final double kP = 10.0; // tune this if rotation is too fast/slow

    public autoTarget(CommandSwerveDrivetrain drive) {
        m_drive = drive;
    }

    public void execute() {
        int tagID = limelight.getTagId();
        double turnOffset = limelight.getCamXNC();
        int tagIDTarget = 9;
        double safelimit = 3.0;
        double rotationOutput = Math.max(-0.5, Math.min(0.5, -turnOffset * kP));

        System.out.println("TAG ID: " + tagID + "| OFFSET: " + turnOffset);

        if (tagID == tagIDTarget) {

            if (turnOffset > safelimit) {
                System.out.println("TARGET TO RIGHT");
                m_drive.setControl(
                    m_request/*.withVelocityX(-joystick.getLeftY() * MaxSpeed)
                            .withVelocityY(-joystick.getLeftX() * MaxSpeed) */
                            .withRotationalRate(rotationOutput)
                );
            } else if (turnOffset < -safelimit) {
                System.out.println("TARGET TO LEFT");
                m_drive.setControl(
                    m_request/*.withVelocityX(-joystick.getLeftY() * MaxSpeed)
                            .withVelocityY(-joystick.getLeftX() * MaxSpeed) */
                            .withRotationalRate(rotationOutput)
                );
            } else {
                System.out.println("TARGET IS CENTERED");
                m_drive.setControl(
                    m_request/*.withVelocityX(-joystick.getLeftY() * MaxSpeed)
                            .withVelocityY(-joystick.getLeftX() * MaxSpeed)*/
                            .withRotationalRate(0.0)
                );
            }
        }
    }
}