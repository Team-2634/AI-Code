package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Shooter;

public class CommandShooter implements Subsystem   {
    public static Command test(double speed) {
        return Commands.run(() -> Shooter.shooterForwardcustom(speed));
    }
}