package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;


public class Shooter {

    public static TalonFX shooterMotor1 = new TalonFX(10);
    public static TalonFX shooterMotor2 = new TalonFX(11);

    public static void shooterForwardSlow(){ // slow launcher speed
        shooterMotor1.set(0.75);
        shooterMotor2.set(0.75);
    }

    public static void shooterForwardFast(){ // fast launcher speed
        shooterMotor1.set(1);
        shooterMotor2.set(1);
    }
    
    public static void shooterForwardcustom(double speed){ // custom launcher speed
        shooterMotor1.set(speed);
        shooterMotor2.set(speed);
    }

    public static void shooterStop(){ // no launcher speed
        shooterMotor1.set(0);
        shooterMotor2.set(0);
    }
}

