// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.sound.midi.Sequence;

import com.ctre.phoenix6.HootAutoReplay;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.DriveMotorArrangement;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.Pigeon2.*;
import frc.robot.*;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private final RobotContainer m_robotContainer;


    private final Pigeon2 mainGryo = new Pigeon2(0);
    private final XboxController Controller1 = new XboxController(0);

    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    public Robot() {
        m_robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run(); 
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.autoShooter(0.75);
        

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
            SmartDashboard.putString("run","code is running");
            System.out.print("[DEBUG] autonomous init starting.");

        }            
        System.out.print("[DEBUG] autonomous init ran.");


    }

    @Override
    public void autonomousPeriodic() {
        
    }

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
    RobotContainer.drivetrain.seedFieldCentric(Rotation2d.kZero);    
    //RobotContainer.drivetrain.runOnce(RobotContainer.drivetrain::seedFieldCentric);
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }

    @Override
    public void teleopPeriodic() {

         if (Controller1.getLeftTriggerAxis() > 0.5){
      Shooter.shooterForwardSlow();
    }   
      else if (Controller1.getRightTriggerAxis() > 0.5){
      Shooter.shooterFowardFast();
    }   

    else {
        Shooter.shooterStop();
    }


    

    if (Controller1.getXButtonPressed()){
      Intake.intakeFoward();
    }

    else if(Controller1.getXButtonReleased()){
      Intake.intakeStop();
    }
    }

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}

}

