package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;


public class Shooter {

    public static TalonFX shooterMotor1 = new TalonFX(12);
    public static TalonFX shooterMotor2 = new TalonFX(11);

    public static TalonFX agitatorMotorL = new TalonFX(21);
    public static TalonFX agitatorMotorR = new TalonFX(22);

    public static void shooterForwardSlow(){ // slow launcher speed
        shooterMotor1.set(0.75);
        shooterMotor2.set(0.75);
        agitatorMotorL.set(0.1);
        agitatorMotorR.set(-0.1);

    }

    public static void shooterFowardFast(){ // fast launcher speed
        shooterMotor1.set(1);
        shooterMotor2.set(1);
        agitatorMotorL.set(0.05);
        agitatorMotorR.set(-0.05);
    }

    public static void Unstuck(){
        shooterMotor1.set(-0.2);
        shooterMotor2.set(-0.2);
        agitatorMotorL.set(-0.05);
        agitatorMotorR.set(0.05);


    }
    
    public static double shooterFowardcustom(double speed){ // custom launcher speed
        shooterMotor1.set(speed);
        shooterMotor2.set(speed);
        return speed;    
    }

    public static void shooterStop(){ // no launcher speed
        shooterMotor1.set(0);
        shooterMotor2.set(0);
        agitatorMotorL.set(0);
        agitatorMotorR.set(0);
    }

    public static Command spinUpCommand() {
    return Commands.runOnce(() -> {
        shooterMotor1.set(1);
        shooterMotor2.set(1);
    });
}

public static Command feedNoteCommand() {
    return Commands.runOnce(() -> {
        agitatorMotorL.set(0.05);
        agitatorMotorR.set(-0.05);
    });
}

public Command stopCommand() {
    return Commands.runOnce(() -> shooterStop());
}

public static Command autoShootCommand() {
    return Commands.sequence(
        spinUpCommand(),
        Commands.waitSeconds(1.5),
        feedNoteCommand()
    );
}
}

