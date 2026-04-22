package frc.robot;

import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.Set;
import frc.robot.*;

import frc.robot.subsystems.CommandSwerveDrivetrain;

public class SideAuto extends SequentialCommandGroup {

    public static final Pose2d LEFT_START_POSE = new Pose2d(
        0.75, 6.68, Rotation2d.fromDegrees(60)
    );
    public static final Pose2d RIGHT_START_POSE = new Pose2d(
        0.75, 4.37, Rotation2d.fromDegrees(-60)
    );

    public SideAuto(CommandSwerveDrivetrain drivetrain, boolean isLeftSide) {

        Pose2d startPose = isLeftSide ? LEFT_START_POSE : RIGHT_START_POSE;
        String pathName  = isLeftSide ? "Blue Left 1" : "Blue Right 1";

        addCommands(
            // 1. Reset odometry to known starting position
            Commands.runOnce(() -> drivetrain.resetPose(startPose)),

            // 2. Wait one loop for pose to settle
            Commands.waitSeconds(3),

            // 3. Follow the path (exception handled inside deferred command)
            Commands.defer(() -> {
                try {
                    PathPlannerPath path = PathPlannerPath.fromPathFile(pathName);
                    return AutoBuilder.followPath(path);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("WARNING: Could not load path: " + pathName);
                    return Commands.print("Path file not found: " + pathName);
                }
            },Set.of(drivetrain)),

            // 4. TODO: Add your shoot command here
            Shooter.autoShootCommand()
        );
    }
}
