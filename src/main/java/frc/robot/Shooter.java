package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;


public class Shooter {

    public static TalonFX shooterMotor1 = new TalonFX(10);
    public static TalonFX shooterMotor2 = new TalonFX(11);

    public static TalonFX TylerMotor = new TalonFX(21);
    public static TalonFX JavierMotor = new TalonFX(22);

    public static void shooterForwardSlow(){ // slow launcher speed
        shooterMotor1.set(0.75);
        shooterMotor2.set(0.75);
        TylerMotor.set(0.1);
        JavierMotor.set(-0.1);

    }

    public static void shooterFowardFast(){ // fast launcher speed
        shooterMotor1.set(1);
        shooterMotor2.set(1);
        TylerMotor.set(0.1);
        JavierMotor.set(-0.1);
    }
    
    public static double shooterFowardcustom(double speed){ // custom launcher speed
        shooterMotor1.set(speed);
        shooterMotor2.set(speed);
        return speed;    
    }

    public static void shooterStop(){ // no launcher speed
        shooterMotor1.set(0);
        shooterMotor2.set(0);
        TylerMotor.set(0);
        JavierMotor.set(0);
    }
}

