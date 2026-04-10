package frc.robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;


public class limelight {

    double CAMERA_MOUNT_HEIGHT = 0.0; // The height of the camera from the ground (inches)
    double CAMERA_MOUNT_ANGLE = 0.0; // The angle at which the camera is mounted (degrees)

    public static double getX() { // Get the horizontal offset from the crosshair to the target
        return LimelightHelpers.getTX("limelight");
    }

    public static double getY() { // Get the vertical offset from the crosshair to the target   
        return LimelightHelpers.getTY("limelight");
    }

    public static double getXNC() { // Get the horizontal offset from the camera optic to the target (normalized)
        return LimelightHelpers.getTXNC("limelight");
    }

    public static double getYNC() { // Get the vertical offset from the camera optic to the target (normalized)
        return LimelightHelpers.getTYNC("limelight");
    }

    public static double getArea() { // Get the area the target covers in the camera's field of view
        return LimelightHelpers.getTA("limelight");
    }
    

    public static void robotX(){

    }

    public static void robotY(){

    }

    private static final NetworkTable limelight = 
        NetworkTableInstance.getDefault().getTable("limelight");

    public static int getTagId() {
        return (int) limelight.getEntry("tid").getDouble(-1);
    }

    public static void dumpTargetData(){ // Print all the data to the console
        System.out.println(
            " ID: " + getTagId() + 
            " X: " + getX() + 
            " Y: " + getY() + 
            " XNC: " + getXNC() + 
            " YNC: " + getYNC() + 
            " Area: " + getArea()
            );
    }
}
