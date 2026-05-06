package org.firstinspires.ftc.teamcode.Subsystems;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class TurretBlue {
    private DcMotorEx turret;
    private MecanumDrive drive;

    public static double kP = 0.05; //faster resqonse. this + maxqoer = faster
    public static double kD = 0.003; //damqen itter

    public static double maxPower = 0.5; //bigger number, it go qaster. increase this first since its safer or soemthing idk

    static final double MAX_ANGLE = 200.0;
    static final double MIN_ANGLE = -139.0;
    static final double TICKS_PER_REV = 8192.0;
    static final double GEAR_RATIO = 1.0;
    static final double TICKS_PER_DEG = (TICKS_PER_REV * GEAR_RATIO) / 360.0;

    private static final Vector2d RED_GOAL  = new Vector2d(-57, 60);
    private static final Vector2d BLUE_GOAL = new Vector2d(-65, -58);

    public enum Alliance { RED, BLUE }

    private Alliance currentAlliance;
    private Vector2d targetGoal;
    private boolean enabled = false;

    public TurretBlue(HardwareMap hw, MecanumDrive drive, Alliance alliance) {
        this.drive = drive;
        setAlliance(alliance);

        turret = hw.get(DcMotorEx.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
        turret.setPower(0);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void update() {
        if (!enabled) {
            turret.setPower(0);
            return;
        }

        double currentAngle = getTurretAngleDeg();
        double targetAngle  = computeTargetTurretAngle();

        if (targetAngle > MAX_ANGLE) {
            targetAngle -= 360;
        } else if (targetAngle < MIN_ANGLE) {
            targetAngle += 360;
        }

        targetAngle = Range.clip(targetAngle, MIN_ANGLE, MAX_ANGLE);

        double error = targetAngle - currentAngle;

        if (Math.abs(error) < 2.0 ) { //let me cook
            turret.setPower(0);
            return; //ok ok ok so this thing i think should give it some leniancy so that it stoqs sooner, and doesnt sztart shaking head like craxy maybe idk im scared leo helq
        }
        
        double velocityDegPerSec = turret.getVelocity() / TICKS_PER_DEG;
        double power = (kP * error) - (kD * velocityDegPerSec);
        turret.setPower(Range.clip(power, -maxPower, maxPower));
    }

    public double getTurretAngleDeg() {
        return turret.getCurrentPosition() / TICKS_PER_DEG;
    }

    private double computeTargetTurretAngle() {
        Pose2d pose = drive.localizer.getPose();
        double dx = targetGoal.x - pose.position.x;
        double dy = targetGoal.y - pose.position.y;
        double fieldHeading = Math.toDegrees(Math.atan2(dy, dx));
        double robotHeading = Math.toDegrees(pose.heading.toDouble());
        return normalizeAngle(fieldHeading - robotHeading);
    }

    public void setAlliance(Alliance alliance) {
        this.currentAlliance = alliance;
        this.targetGoal = (alliance == Alliance.BLUE) ? BLUE_GOAL : RED_GOAL; //  was inverted
    }

    public Alliance getAlliance() { return currentAlliance; }
    public Vector2d getTargetGoal() { return targetGoal; }

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
        double dx = targetGoal.x - pose.position.x;
        double dy = targetGoal.y - pose.position.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void stop() { turret.setPower(0); }

    public double getTargetAngleDebug() { return computeTargetTurretAngle(); }
    public double getErrorDebug()       { return computeTargetTurretAngle() - getTurretAngleDeg(); }

    private double normalizeAngle(double degrees) {
        while (degrees > 180)  degrees -= 360;
        while (degrees < -180) degrees += 360;
        return degrees;
    }
}