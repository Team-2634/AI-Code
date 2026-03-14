package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Intake;

public class CommandIntake {
    public static Command runIntakeMotor(){
        return Commands.run(() -> Intake.intakeForward());
    }

    public static Command stopIntakeMotor(){
        return Commands.run(() -> Intake.intakeStop());
    }
}
