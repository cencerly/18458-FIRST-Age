package org.firstinspires.ftc.teamcode.Subsystems;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Config
public class TurretBlue {
    private DcMotorEx turret;
    private MecanumDrive drive;

    static final double MAX_ANGLE    = 200.0;
    static final double MIN_ANGLE    = -139.0;
    static final double TICKS_PER_REV = 8192.0;
    static final double GEAR_RATIO   = 1.0;
    static final double TICKS_PER_DEG = (TICKS_PER_REV * GEAR_RATIO) / 360.0;

    public static double kP = 0.04;
    public static double kD = 0.001;
    public static double kI = 0.0;

    public static double maxPower = 0.6;
    public static double minPower = 0.03;

    private static final double DEADBAND_DEG = 8.0;

    // ── Goals ─────────────────────────────────────────────────────────────────
    private static final Vector2d RED_GOAL  = new Vector2d(-57,  60);
    private static final Vector2d BLUE_GOAL = new Vector2d(-65, -58);

    public enum Alliance { RED, BLUE }

    private Alliance currentAlliance;
    private Vector2d targetGoal;
    private boolean  enabled = false;

    // ── PID state ──────────────────────────────────────────────────────────────
    private double errorIntegral = 0.0;
    private double lastTarget    = Double.NaN;
    private final ElapsedTime timer = new ElapsedTime();

    // ── Constructor ────────────────────────────────────────────────────────────
    public TurretBlue(HardwareMap hw, MecanumDrive drive, Alliance alliance) {
        this.drive = drive;
        setAlliance(alliance);

        turret = hw.get(DcMotorEx.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    public void enable()  { enabled = true; timer.reset();
    }

    public void update() {
        if (!enabled) {
            turret.setPower(0);
            return;
        }

        double currentAngle = getTurretAngleDeg();
        double rawTarget    = computeTargetTurretAngle();

        double target = unwrapTarget(rawTarget, currentAngle);
        target = Range.clip(target, MIN_ANGLE, MAX_ANGLE);

        double error = target - currentAngle;

        // ── Reset integral if the goal jumped significantly ─────────────────
        if (!Double.isNaN(lastTarget) && Math.abs(target - lastTarget) > 30.0) {
            errorIntegral = 0.0;
        }
        lastTarget = target;

        // ── Deadband ────────────────────────────────────────────────────────
        if (Math.abs(error) < DEADBAND_DEG) {
            turret.setPower(0);
            errorIntegral = 0.0;
            return;
        }

        // ── Integral (anti-windup clamped to ±20 deg·s) ────────────────────
        double dt = timer.seconds();
        timer.reset();
        if (dt > 0 && dt < 0.5) { // ignore stale first tick
            errorIntegral += error * dt;
            errorIntegral  = Range.clip(errorIntegral, -20.0, 20.0);
        }

        // ── Derivative via motor velocity (smoother than finite difference) ─
        // getVelocity() returns ticks/sec; convert to deg/sec.
        double velocityDegPerSec = turret.getVelocity() / TICKS_PER_DEG;

        // ── PID output ──────────────────────────────────────────────────────
        double power = (kP * error)
                + (kI * errorIntegral)
                - (kD * velocityDegPerSec);

        // ── Static friction floor (prevents stalling near target) ───────────
        // BUG FIX: without this, small errors produce power below stall torque
        // and the turret just sits there humming instead of correcting.
        if (Math.abs(power) > 0.001 && Math.abs(power) < minPower) {
            power = Math.copySign(minPower, power);
        }

        turret.setPower(Range.clip(power, -maxPower, maxPower));
    }

    // ── Angle helpers ─────────────────────────────────────────────────────────
    public double getTurretAngleDeg() {
        return turret.getCurrentPosition() / TICKS_PER_DEG;
    }

    private double computeTargetTurretAngle() {
        Pose2d pose     = drive.localizer.getPose();
        double dx       = targetGoal.x - pose.position.x;
        double dy       = targetGoal.y - pose.position.y;
        double fieldHdg = Math.toDegrees(Math.atan2(dy, dx));
        double robotHdg = Math.toDegrees(pose.heading.toDouble());
        return normalizeAngle(fieldHdg - robotHdg);
    }
    private double unwrapTarget(double normalizedTarget, double currentAngle) {
        double best = normalizedTarget;
        for (double candidate : new double[]{
                normalizedTarget - 360,
                normalizedTarget,
                normalizedTarget + 360}) {
            if (Math.abs(candidate - currentAngle) < Math.abs(best - currentAngle)) {
                best = candidate;
            }
        }
        return best;
    }

    private double normalizeAngle(double degrees) {
        while (degrees >  180) degrees -= 360;
        while (degrees < -180) degrees += 360;
        return degrees;
    }

    // ── Alliance / goal ───────────────────────────────────────────────────────
    public void setAlliance(Alliance alliance) {
        this.currentAlliance = alliance;
        this.targetGoal      = (alliance == Alliance.BLUE) ? BLUE_GOAL : RED_GOAL;
        errorIntegral = 0.0; // reset integral on goal change
    }

    public Alliance getAlliance()    { return currentAlliance; }
    public Vector2d getTargetGoal() { return targetGoal; }

    // ── Status queries ────────────────────────────────────────────────────────
    public boolean isAimedAtTarget(double toleranceDegrees) {
        double error = normalizeAngle(computeTargetTurretAngle() - getTurretAngleDeg());
        return Math.abs(error) < toleranceDegrees;
    }

    public boolean isAtLimit() {
        double angle = getTurretAngleDeg();
        return angle >= MAX_ANGLE || angle <= MIN_ANGLE;
    }

    public double getDistanceToTarget() {
        Pose2d pose = drive.localizer.getPose();
        double dx   = targetGoal.x - pose.position.x;
        double dy   = targetGoal.y - pose.position.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void stop() { turret.setPower(0);
    }

}