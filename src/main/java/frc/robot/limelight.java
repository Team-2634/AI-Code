package frc.robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;


public class limelight {

    static double CAMERA_MOUNT_HEIGHT = 18.5; // The height of the camera from the ground (inches)
    static double CAMERA_MOUNT_Y_OFFSET = 4.5; // Y offset of camera from center of robot (inches)
    static double CAMERA_MOUNT_ANGLE = 0.0; // The angle at which the camera is mounted (degrees)

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

    public static double robotX(){
        return robotY() * (Math.tan(getXNC() * (Math.PI/180)));
    }

    public static double robotY(){
        return (limelightTagData.getTagHeight(getTagId()) - CAMERA_MOUNT_HEIGHT) / (Math.tan(getYNC() * (Math.PI/180))) + CAMERA_MOUNT_Y_OFFSET;
    }

    public static double[] RobotXY(){
        double y = (limelightTagData.getTagHeight(getTagId()) - CAMERA_MOUNT_HEIGHT) / (Math.tan(getYNC() * (Math.PI/180))) + CAMERA_MOUNT_Y_OFFSET;
        double x = y * (Math.tan(getXNC() * (Math.PI/180)));
        return new double[]{x, y};
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
            " Area: " + getArea() +
            " Position " + RobotXY()
            );
    }
}
