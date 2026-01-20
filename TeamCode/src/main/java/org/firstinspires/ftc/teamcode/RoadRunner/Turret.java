package org.firstinspires.ftc.teamcode.RoadRunner;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Turret {

    private DcMotorEx turret;
    private Limelight3A limelight;
    private MecanumDrive drive; // Reference to your RoadRunner drive

    public static double kP = 0.02;
    public static double maxPower = 0.5;

    static final double TICKS_PER_REV = 537.7;
    static final double GEAR_RATIO = 7.0;
    static final double TICKS_PER_DEG =
            (TICKS_PER_REV * GEAR_RATIO) / 360.0;

    private Pose2d targetPos = new Pose2d(-64, 60, Math.toRadians(125));

    public Turret(HardwareMap hw, MecanumDrive drive) {
        this.drive = drive;
        turret = hw.get(DcMotorEx.class, "turret");
        limelight = hw.get(Limelight3A.class, "limelight");

        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void update() {
        // Get live pose from RoadRunner
        Pose2d robotPose = drive.localizer.getPose();

        double currentTurretAngle = getTurretAngleDeg();

        // Calculate field-relative angle to target
        double dx = targetPos.position.x - robotPose.position.x;
        double dy = targetPos.position.y - robotPose.position.y;
        double fieldHeadingToTarget = Math.toDegrees(Math.atan2(dy, dx));

        // Convert robot heading from radians to degrees
        double robotHeadingDeg = Math.toDegrees(robotPose.heading.toDouble());

        // Calculate required turret angle
        double targetTurretAngle = fieldHeadingToTarget - robotHeadingDeg;

        // Incorporate Limelight correction if target is visible
        if (limelight.getLatestResult().isValid()) {
            targetTurretAngle -= limelight.getLatestResult().getTx();
        }

        // Normalize angle to [-180, 180]
        targetTurretAngle = normalizeAngle(targetTurretAngle);

        double error = targetTurretAngle - currentTurretAngle;
        double power = error * kP;

        turret.setPower(Range.clip(power, -maxPower, maxPower));
    }

    public void setTargetPosition(Pose2d pos) {
        targetPos = pos;
    }

    public void setTargetPosition(double x, double y) {
        targetPos = new Pose2d(x, y, 0);
    }

    public double getTurretAngleDeg() {
        return turret.getCurrentPosition() / TICKS_PER_DEG;
    }

    public void stop() {
        turret.setPower(0);
    }

    // Helper method to normalize angles to [-180, 180]
    private double normalizeAngle(double degrees) {
        while (degrees > 180) degrees -= 360;
        while (degrees < -180) degrees += 360;
        return degrees;
    }
}