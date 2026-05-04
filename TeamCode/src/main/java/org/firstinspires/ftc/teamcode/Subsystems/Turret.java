/*package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.List;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turret extends SubsystemBase {
    private DcMotorEx turret;

    private final PIDFController pidfController;
    private final SimpleMotorFeedforward ffController;

    private String Alliance;

    private final Telemetry telemetry;

    public Turret(HardwareMap hMap, Telemetry telemetry, String Alliance) {
        turretMotor = hMap.get(DcMotorEx.class, Constants.turretMotorID);
        turretMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pidfController = new PIDFController(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
        ffController = new SimpleMotorFeedforward(TurretConstants.kS, TurretConstants.kV, TurretConstants.kA);

        this.telemetry = telemetry;

        this.Alliance = Alliance.toUpperCase();
    }

    private void AllianceSelector() {
        if(Alliance.equals("BLUE")) {
            allianceColor = GlobalConstants.AllianceColor.BLUE;
        }

        else {
            allianceColor = GlobalConstants.AllianceColor.RED;
        }
    }

    @Override
    public void periodic() {
        telemetry.addData("Turret Current Open Loop", turretMotor.getPower());
        telemetry.addData("Turret Current Position in Degr", Math.toRadians(getCurrentPosition()));
        telemetry.addData("Get Curret Position in Radians", getCurrentPosition());
        telemetry.addData("At Setpoint Method:", isAtSetpoint());
        telemetry.addData("Turret Raw", turretMotor.getCurrentPosition());
        //telemetry.addData("Turret New Pose: ", turretMotor.getCurrentPosition() / ((537.0 * (174.0/36.0)) / 360.0));
    }

    public static double normalizeAngle(double angle) {
        angle %= 360;
        if (angle > 180) angle -= 360;
        if (angle < -180) angle += 360;
        return angle;
    }

    public double getCurrentPosition() {
        return (((turretMotor.getCurrentPosition() / 537.7) / TurretConstants.kTurretRatio) * 360); //removed
    }

    public double getCurrentPositionVishals() {
        return turretMotor.getCurrentPosition() / ((537.0 * (174.0/36.0)) / (2*Math.PI));
    }

    public double getCurrentVelocity() {
        return Math.toRadians(normalizeAngle(((turretMotor.getVelocity() / 537.7) / TurretConstants.kTurretRatio) * 360));
    }

    public double getPositionError() {
        return pidfController.getPositionError();
    }

    public void setManualPower(double speed) {
        telemetry.addData("Turret Setpoint Open Loop", speed);
        turretMotor.setPower(speed);
    }

    public void resetPosition() {
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPosition(double radians) {

        telemetry.addData("Turret Setpoint Position", Math.toDegrees(radians));
        telemetry.addData("Turret Primary Position Error", pidfController.getPositionError());
        telemetry.addData("Turret Primary At Setpoint?", pidfController.atSetPoint());
        telemetry.addData("Turret Motor Power", turretMotor.getPower());

        if(GlobalConstants.kTuningMode) {
            pidfController.setPIDF(TurretConstants.kP, TurretConstants.kI, TurretConstants.kD, TurretConstants.kF);
        }

        pidfController.setSetPoint(radians);
        turretMotor.setPower(MathUtility.clamp(pidfController.calculate(getCurrentPosition(), radians), -.65, .45) + ffController.calculate(Math.toRadians(200))); //-.65 & .45
    }

    public double computeAngle(Pose robotPose, Pose targetPose, double turretOffsetX, double turretOffsetY) {
        double robotX = robotPose.getX();
        double robotY = robotPose.getY();
        double robotHeading = robotPose.getHeading();

        double turretX = robotX + turretOffsetX * Math.cos(robotHeading) - turretOffsetY * Math.sin(robotHeading);
        double turretY = robotY + turretOffsetX * Math.sin(robotHeading) + turretOffsetY * Math.cos(robotHeading);

        double dx = targetPose.getX() - turretX;
        double dy = targetPose.getY() - turretY;

        double targetAngleGlobal = Math.atan2(dy, dx);
        double desiredTurretAngle = targetAngleGlobal - robotHeading;

        return AngleUnit.normalizeRadians(desiredTurretAngle);
    }

    public Pose getTargetPose(GlobalConstants.AllianceColor allianceColor) {
        return allianceColor == GlobalConstants.AllianceColor.BLUE ? new Pose(9, 138) : new Pose(9, 138).mirror();
    }

    public boolean isAtSetpoint() {
        return pidfController.atSetPoint() && getCurrentVelocity() == 0;
    }
}*/