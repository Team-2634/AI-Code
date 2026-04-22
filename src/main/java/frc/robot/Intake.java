package frc.robot;

import frc.robot.Robot;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;


public class Intake {
    
    public static TalonFX intakeMotor1 = new TalonFX(10);
    public static TalonFX IntakeMove = new TalonFX(33);

    public static double intakeUpDOwnPosition(){
        return IntakeMove.getPosition().getValueAsDouble();
    }

    

       public static void intakeFoward(){
        intakeMotor1.set(-0.4);
    }

    public static void intakeStop(){
        intakeMotor1.set(0);
    }

    public static void intakeReverse(){
        intakeMotor1.set(0);
    }


    public static void intakeDirectionUp(){
    IntakeMove.set(1);
    if ((intakeUpDOwnPosition()) >= 0.144){
        IntakeMove.set(0);
    }

    }
     public static void intakeDirectionDown(){
         IntakeMove.set(-0.3);

        if ((intakeUpDOwnPosition()) <= 0.479){
             IntakeMove.set(1);
        }
    }
    
    public static void intakeDirectionStop(){
        IntakeMove.set(0);
    }
}