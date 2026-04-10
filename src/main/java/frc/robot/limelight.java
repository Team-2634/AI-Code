package frc.robot;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class limelight {

    private static Pigeon2 mainGyro = new Pigeon2(0);

    static double CAMERA_MOUNT_HEIGHT = 18.5; // The height of the camera from the ground (inches)
    static double CAMERA_MOUNT_Y_OFFSET = 4.5; // Y offset of camera from center of robot (inches)
    static double CAMERA_MOUNT_ANGLE = 0.0; // The angle at which the camera is mounted (degrees)

    public static double getCamX() { // Get the horizontal offset from the crosshair to the target
        return LimelightHelpers.getTX("limelight");
    }

    public static double getCamY() { // Get the vertical offset from the crosshair to the target   
        return LimelightHelpers.getTY("limelight");
    }

    public static double getCamXNC() { // Get the horizontal offset from the camera optic to the target (normalized)
        return LimelightHelpers.getTXNC("limelight");
    }

    public static double getCamYNC() { // Get the vertical offset from the camera optic to the target (normalized)
        return LimelightHelpers.getTYNC("limelight");
    }

    public static double getArea() { // Get the area the target covers in the camera's field of view
        return LimelightHelpers.getTA("limelight");
    }

    public static double getRobotX(){
        return getRobotY() * (Math.tan(getCamXNC() * (Math.PI/180)));
    }

    public static double getRobotY(){
        return (limelightTagData.getTagHeight(getTagId()) - CAMERA_MOUNT_HEIGHT) / (Math.tan(getCamYNC() * (Math.PI/180))) + CAMERA_MOUNT_Y_OFFSET;
    }

    public static double[] getRobotPos(){
        double camY = getRobotY();
        double camX = camY * (Math.tan(getCamXNC() * (Math.PI/180)));
        double camYaw = mainGyro.getYaw().getValueAsDouble();

        double robotPosX = camX * Math.cos(camYaw) - camY * camX * Math.sin(camYaw);
        double robotPosY = camX * Math.sin(camYaw) - camY * camX * Math.cos(camYaw);

        return new double[]{robotPosX, robotPosY};
    }

    private static final NetworkTable limelight = 
        NetworkTableInstance.getDefault().getTable("limelight");

    public static int getTagId() {
        return (int) limelight.getEntry("tid").getDouble(-1);
    }

    public static void dumpAllTargetData(){ // Print all data outputs to the console
        System.out.println(
            " ID: " + getTagId() + 
            " X: " + getCamX() + 
            " Y: " + getCamY() + 
            " XNC: " + getCamXNC() + 
            " YNC: " + getCamYNC() + 
            " Area: " + getArea() +
            " Robot X " + getRobotX() +
            " Robot Y " + getRobotY() +
            " Position " + getRobotPos()
            );
    }

    public static void printImportantData() { // Prints only the important data.
        System.out.println(
            " ID: " + getTagId() + 
            " XNC: " + getCamXNC() + 
            " YNC: " + getCamYNC() + 
            " Position " + getRobotPos()
            );
    }
}
