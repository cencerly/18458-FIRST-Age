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

    public static double kP = 0.09;
    public static double kD = 0.025;
    private double lastError = 0;
    public static double maxPower = 0.5;

    static final double MAX_ANGLE = 200.0;
    static final double MIN_ANGLE = -139.0;

    static final double TICKS_PER_REV = 145.1;
    static final double GEAR_RATIO = 7.0;
    static final double TICKS_PER_DEG = (TICKS_PER_REV * GEAR_RATIO) / 360.0;

    // Alliance goal positions
    private static final Vector2d RED_GOAL = new Vector2d(-57, 60);
    private static final Vector2d BLUE_GOAL = new Vector2d(-64, -60);

    // Alliance selection
    public enum Alliance {
        RED,
        BLUE
    }

    private Alliance currentAlliance;
    private Vector2d targetGoal;

    // Enable state and field angle offset
    private boolean enabled = false;
    private double fieldAngleOffset = 0;

    public TurretRed(HardwareMap hw, MecanumDrive drive, Alliance alliance) {
        this.drive = drive;
        this.currentAlliance = alliance;

        setAlliance(alliance);

        turret = hw.get(DcMotorEx.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Enable the turret from any field position
     * Call this whenever you want to start tracking, even after relocalization
     */
    public void enable() {
        Pose2d robotPose = drive.localizer.getPose();
        double robotHeadingDeg = Math.toDegrees(robotPose.heading.toDouble());

        // Capture field-relative offset so tracking is correct from any starting position
        fieldAngleOffset = robotHeadingDeg - (turret.getCurrentPosition() / TICKS_PER_DEG);

        enabled = true;
        lastError = 0;
    }

    /**
     * Disable the turret and stop motor
     */

    /**
     * Check if turret is enabled
     */

    /**
     * Main update loop - call this continuously in your OpMode loop
     * Does nothing if not enabled
     */
    public void update() {
        if (!enabled) {
            turret.setPower(0);
            return;
        }

        Pose2d robotPose = drive.localizer.getPose();
        double currentTurretAngle = getTurretAngleDeg();

        double dx = targetGoal.x - robotPose.position.x;
        double dy = targetGoal.y - robotPose.position.y;

        double fieldHeadingToTarget = Math.toDegrees(Math.atan2(dy, dx));
        double robotHeadingDeg = Math.toDegrees(robotPose.heading.toDouble());

        double error = getError(fieldHeadingToTarget, robotHeadingDeg, currentTurretAngle);
        double derivative = error - lastError;
        lastError = error;

        double power = (kP * error) + (kD * derivative);
        turret.setPower(Range.clip(power, -maxPower, maxPower));
    }

    private double getError(double fieldHeadingToTarget, double robotHeadingDeg, double currentTurretAngle) {
        double targetTurretAngle = normalizeAngle(fieldHeadingToTarget - robotHeadingDeg);

        // Flip to other side if past limit
        if (targetTurretAngle > MAX_ANGLE) {
            targetTurretAngle -= 360;
        } else if (targetTurretAngle < MIN_ANGLE) {
            targetTurretAngle += 360;
        }

        // Clamp target so PID never pushes past the limit
        targetTurretAngle = Range.clip(targetTurretAngle, MIN_ANGLE, MAX_ANGLE);

        return targetTurretAngle - currentTurretAngle;
    }

    /**
     * Get current turret angle in degrees, field-relative
     */
    public double getTurretAngleDeg() {
        return (turret.getCurrentPosition() / TICKS_PER_DEG) + fieldAngleOffset;
    }

    /**
     * Check if turret is at a rotation limit
     */
    public boolean isAtLimit() {
        double angle = getTurretAngleDeg();
        return angle >= MAX_ANGLE || angle <= MIN_ANGLE;
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