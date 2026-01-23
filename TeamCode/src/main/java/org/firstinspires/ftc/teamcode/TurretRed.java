package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class TurretRed {

    private DcMotorEx turret;
    private MecanumDrive drive;

    public static double kP = 0.02;
    public static double maxPower = 0.5;

    static final double TICKS_PER_REV = 145.1;
    static final double GEAR_RATIO = 7.0;
    static final double TICKS_PER_DEG = (TICKS_PER_REV * GEAR_RATIO) / 360.0;

    // Alliance goal positions
    private static final Vector2d RED_GOAL = new Vector2d(-64, 60);
    private static final Vector2d BLUE_GOAL = new Vector2d(-64, -60);

    // Alliance selection
    public enum Alliance {
        RED,
        BLUE
    }

    private Alliance currentAlliance;
    private Vector2d targetGoal;

    public TurretRed(HardwareMap hw, MecanumDrive drive, Alliance alliance) {
        this.drive = drive;
        this.currentAlliance = alliance;

        // Set the target goal based on alliance
        setAlliance(alliance);

        turret = hw.get(DcMotorEx.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Main update loop - call this continuously in your OpMode loop
     * This automatically aims the turret at the goal based on current robot position
     */
    public void update() {
        // Get live robot pose from RoadRunner
        Pose2d robotPose = drive.localizer.getPose();

        // Get current turret angle relative to robot
        double currentTurretAngle = getTurretAngleDeg();

        // Calculate vector from robot to goal
        double dx = targetGoal.x - robotPose.position.x;
        double dy = targetGoal.y - robotPose.position.y;

        // Calculate field-relative angle to target (in degrees)
        double fieldHeadingToTarget = Math.toDegrees(Math.atan2(dy, dx));

        // Get robot's current heading in degrees
        double robotHeadingDeg = Math.toDegrees(robotPose.heading.toDouble());

        // Calculate required turret angle relative to robot
        // This is the key: field heading to target minus robot heading
        double targetTurretAngle = fieldHeadingToTarget - robotHeadingDeg;

        // Normalize angle to [-180, 180] range
        targetTurretAngle = normalizeAngle(targetTurretAngle);

        // Calculate error and apply proportional control
        double error = targetTurretAngle - currentTurretAngle;
        error = normalizeAngle(error); // Normalize error as well

        double power = error * kP;
        turret.setPower(Range.clip(power, -maxPower, maxPower));
    }

    /**
     * Set which alliance you're on (RED or BLUE)
     */
    public void setAlliance(Alliance alliance) {
        this.currentAlliance = alliance;
        this.targetGoal = (alliance == Alliance.RED) ? RED_GOAL : BLUE_GOAL;
    }

    /**
     * Get the current target goal position
     */
    public Vector2d getTargetGoal() {
        return targetGoal;
    }

    /**
     * Get current alliance
     */
    public Alliance getAlliance() {
        return currentAlliance;
    }

    /**
     * Get current turret angle in degrees
     */
    public double getTurretAngleDeg() {
        return turret.getCurrentPosition() / TICKS_PER_DEG;
    }

    /**
     * Get distance to target in field units
     */
    public double getDistanceToTarget() {
        Pose2d robotPose = drive.localizer.getPose();
        double dx = targetGoal.x - robotPose.position.x;
        double dy = targetGoal.y - robotPose.position.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Check if turret is aimed at target (within tolerance)
     */
    public boolean isAimedAtTarget(double toleranceDegrees) {
        Pose2d robotPose = drive.localizer.getPose();
        double currentTurretAngle = getTurretAngleDeg();

        double dx = targetGoal.x - robotPose.position.x;
        double dy = targetGoal.y - robotPose.position.y;
        double fieldHeadingToTarget = Math.toDegrees(Math.atan2(dy, dx));
        double robotHeadingDeg = Math.toDegrees(robotPose.heading.toDouble());
        double targetTurretAngle = normalizeAngle(fieldHeadingToTarget - robotHeadingDeg);

        double error = normalizeAngle(targetTurretAngle - currentTurretAngle);
        return Math.abs(error) < toleranceDegrees;
    }

    /**
     * Stop the turret
     */
    public void stop() {
        turret.setPower(0);
    }

    /**
     * Normalize angle to [-180, 180] range
     */
    private double normalizeAngle(double degrees) {
        while (degrees > 180) degrees -= 360;
        while (degrees < -180) degrees += 360;
        return degrees;
    }
}