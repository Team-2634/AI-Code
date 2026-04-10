package frc.robot;

public class limelightTagData {

    public enum AprilTagHeight {
        HUB(44.25),
        TOWER(21.75),
        OUTPOST(21.75),
        TRENCH(35.0);

        public final double heightMeters;

        AprilTagHeight(double heightMeters) {
            this.heightMeters = heightMeters;
        }
    };

    private static final AprilTagHeight[] TAG_HEIGHT_MAP = {
        null,                       // index 0 (unused)
        AprilTagHeight.TRENCH,      // tag 1
        AprilTagHeight.HUB,         // tag 2
        AprilTagHeight.HUB,         // tag 3
        AprilTagHeight.HUB,         // tag 4
        AprilTagHeight.HUB,         // tag 5
        AprilTagHeight.TRENCH,      // tag 6
        AprilTagHeight.TRENCH,      // tag 7
        AprilTagHeight.HUB,         // tag 8
        AprilTagHeight.HUB,         // tag 9
        AprilTagHeight.HUB,         // tag 10
        AprilTagHeight.HUB,         // tag 11
        AprilTagHeight.TRENCH,      // tag 12
        AprilTagHeight.OUTPOST,     // tag 13
        AprilTagHeight.OUTPOST,     // tag 14
        AprilTagHeight.TOWER,       // tag 15
        AprilTagHeight.TOWER,       // tag 16
        AprilTagHeight.TRENCH,      // tag 17
        AprilTagHeight.HUB,         // tag 18
        AprilTagHeight.HUB,         // tag 19
        AprilTagHeight.HUB,         // tag 20
        AprilTagHeight.HUB,         // tag 21
        AprilTagHeight.TRENCH,      // tag 22
        AprilTagHeight.TRENCH,      // tag 23
        AprilTagHeight.HUB,         // tag 24
        AprilTagHeight.HUB,         // tag 25
        AprilTagHeight.HUB,         // tag 26
        AprilTagHeight.HUB,         // tag 27
        AprilTagHeight.TRENCH,      // tag 28
        AprilTagHeight.OUTPOST,     // tag 29
        AprilTagHeight.OUTPOST,     // tag 30
        AprilTagHeight.TOWER,       // tag 31
        AprilTagHeight.TOWER,       // tag 32
    };

    public static double getTagHeight(int tagId) {
        
        if (tagId >= 1 && tagId <= 32) {
            return TAG_HEIGHT_MAP[tagId].heightMeters;
        }
        return -1;
    };
}