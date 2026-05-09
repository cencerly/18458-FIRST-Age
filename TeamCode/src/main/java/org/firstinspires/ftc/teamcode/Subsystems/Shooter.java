package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Shooter {

    public static double kP = 0.004, kI = 0.00, kD = 0.0000002, kF = 0.03;
    public static double kP2 = 0.004, kI2 = 0.00, kD2 = 0.0000002, kF2 = 0;
    public static double kP3 = .004, kI3 = 0, kD3 = 0.0000002;
    public static double kF3 = 0.00016;

    private PIDController velController;
    public DcMotorEx shooter, shooter2;

    public double reverseRPM = 3000;
    public double targetRPM = 3000;
    public double farTargetRPM = 6000;
    public double ticksPerSecond;
    private final Gamepad Driver1;

    Telemetry telemetry;
    Hood hood;

    private static final double TICKS_PER_REV = 28;

    public Shooter(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Driver1 = opMode.gamepad1;
        telemetry = opMode.telemetry;
        Hood hood = new Hood(opMode);

        shooter = hardwareMap.get(DcMotorEx.class, "shooter1");
        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");

        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        velController = new PIDController(kP, kI, kD);

    }
    public double currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

    boolean hoodUp;


    public void teleOp() {
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        if (Driver1.left_trigger > .5) {
            hoodUp = !hoodUp;
        }

        if (hoodUp) {
            shoot();
        }
        if (!hoodUp) {
            farShoot();
        }
    }

    public void reverseShooter() {
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        velController.setPID(-kP, -kI, -kD);

        double pid = velController.calculate(currentRPM, reverseRPM);
        shooter.setPower(pid);
        shooter2.setPower(pid);

        shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("CloseCurrent RPM", currentRPM);
        telemetry.addData("CloseTarget RPM", targetRPM);
        telemetry.addData("Power", pid);
    }

    public void runShooter() {
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        velController.setPID(kP, kI, kD);

        double pid = velController.calculate(currentRPM, targetRPM);
        shooter.setPower(pid);
        shooter2.setPower(pid);

        shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("CloseCurrent RPM", currentRPM);
        telemetry.addData("CloseTarget RPM", targetRPM);
        telemetry.addData("Power", pid);
    }

    public void runFarShooter() {
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        velController.setPID(-kP, -kI, -kD);

        double pid = velController.calculate(currentRPM, targetRPM);
        shooter.setPower(pid);
        shooter2.setPower(pid);

        shooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("CloseCurrent RPM", currentRPM);
        telemetry.addData("CloseTarget RPM", targetRPM);
        telemetry.addData("Power", pid);
    }

    public void runAutoShooter() {
        shooter.setPower(.75);
        shooter2.setPower(.75);
    }

    public void stopShooter() {
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shooter.setPower(0);
        shooter2.setPower(0);
    }
    public void shoot() {
        if (Driver1.left_bumper) {
            runShooter();
        } else {
            stopShooter();
        }
    }
    public void farShoot() {
        if (Driver1.x) {
            runFarShooter();
        } else {
            stopShooter();
        }
    }

}
