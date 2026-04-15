package frc.robot;

import frc.robot.Robot;
import com.ctre.phoenix6.hardware.TalonFX;


public class Intake {
    
    public static TalonFX intakeMotor1 = new TalonFX(10);

       public static void intakeFoward(){
        intakeMotor1.set(-0.4);
    }

    public static void intakeStop(){
        intakeMotor1.set(0);
    }

    public static void intakeReverse(){
        intakeMotor1.set(0);
    }

}