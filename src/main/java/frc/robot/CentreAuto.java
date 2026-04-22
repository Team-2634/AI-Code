package frc.robot;

import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class CentreAuto extends SequentialCommandGroup {

    public static final Pose2d CENTRE_START_POSE = new Pose2d(
        3.2368616262482157,
        4.015592011412268,
        Rotation2d.fromDegrees(0)
    );

    public CentreAuto(CommandSwerveDrivetrain drivetrain) {
        addCommands(
            // 1. Reset pose to match path start
            Commands.runOnce(() -> drivetrain.resetPose(CENTRE_START_POSE)),

            // 2. Wait for pose to settle
            Commands.waitSeconds(3),

            // 3. Follow the path
            Commands.defer(() -> {
                try {
                    PathPlannerPath path = PathPlannerPath.fromPathFile("Blue_Centre_1");
                    return AutoBuilder.followPath(path);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("WARNING: Could not load Blue_Centre_1 path");
                    return Commands.print("Path file not found: Blue_Centre_1");
                }
            }, Set.of(drivetrain)),

            // 4. Shoot
            Shooter.autoShootCommand()
        );
    }
}