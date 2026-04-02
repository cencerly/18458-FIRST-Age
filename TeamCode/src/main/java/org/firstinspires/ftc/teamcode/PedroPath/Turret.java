package org.firstinspires.ftc.teamcode.PedroPath;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class Turret {

    private final DcMotorEx turret;
    private final Follower follower;

    public static double kP = 0.02;
    public static double maxPower = 0.5;

    static final double TICKS_PER_REV = 145.1;
    static final double GEAR_RATIO = 7.0;
    static final double TICKS_PER_DEG = (TICKS_PER_REV * GEAR_RATIO) / 360.0;

    private static final double[] RED_GOAL  = {130, 130};
    private static final double[] BLUE_GOAL = {11, 133};

    public enum Alliance { RED, BLUE }

    private double[] targetGoal;

    public Turret(HardwareMap hardwareMap, Follower follower, Alliance alliance) {
        this.follower = follower;
        setAlliance(alliance);

        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setDirection(DcMotorSimple.Direction.REVERSE);
        turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void update() {
        Pose robotPose = follower.getPose();

        double currentTurretAngle = getTurretAngleDeg();

        double dx = targetGoal[0] - robotPose.getX();
        double dy = targetGoal[1] - robotPose.getY();

        double fieldHeadingToTarget = Math.toDegrees(Math.atan2(dy, dx));
        double robotHeadingDeg = Math.toDegrees(robotPose.getHeading());

        double targetTurretAngle = normalizeAngle(fieldHeadingToTarget - robotHeadingDeg);
        double error = normalizeAngle(targetTurretAngle - currentTurretAngle);

        turret.setPower(Range.clip(error * kP, -maxPower, maxPower));
    }

    public void setAlliance(Alliance alliance) {
        // fixed - BLUE alliance targets BLUE_GOAL, RED targets RED_GOAL
        targetGoal = (alliance == Alliance.BLUE) ? BLUE_GOAL : RED_GOAL;
    }

    public double getTurretAngleDeg() {
        return turret.getCurrentPosition() / TICKS_PER_DEG;
    }

    public boolean isAimedAtTarget(double toleranceDegrees) {
        Pose robotPose = follower.getPose();

        double dx = targetGoal[0] - robotPose.getX();
        double dy = targetGoal[1] - robotPose.getY();

        double fieldHeadingToTarget = Math.toDegrees(Math.atan2(dy, dx));
        double robotHeadingDeg = Math.toDegrees(robotPose.getHeading());
        double targetTurretAngle = normalizeAngle(fieldHeadingToTarget - robotHeadingDeg);

        double error = normalizeAngle(targetTurretAngle - getTurretAngleDeg());
        return Math.abs(error) < toleranceDegrees;
    }

    public double getDistanceToTarget() {
        Pose robotPose = follower.getPose();
        double dx = targetGoal[0] - robotPose.getX();
        double dy = targetGoal[1] - robotPose.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void stop() {
        turret.setPower(0);
    }

    private double normalizeAngle(double degrees) {
        while (degrees > 180) degrees -= 360;
        while (degrees < -180) degrees += 360;
        return degrees;
    }

    public void init() {
        turret.getCurrentPosition();
    }
}
