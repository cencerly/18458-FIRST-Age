package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Shooter {

    public static double kP = 0.004, kI = 0.00, kD = 0.0000002, kF = 0.03;
    public static double kP2 = 0.004, kI2 = 0.00, kD2 = 0.0000002, kF2 = 0;
    public static double kP3 = .003, kI3 = 0, kD3 = 0;
    public static double kF3 = 0.00016;  // ~1.0 power / 6000 RPM

    private PIDController velController;
    public DcMotorEx shooter, shooter2;

    public double reverseRPM = -1500;
    public double targetRPM = 3000;
    public double farTargetRPM = 6000;
    public double ticksPerSecond;
    private final Gamepad Driver1;

    Telemetry telemetry;

    private static final double TICKS_PER_REV = 28;

    public Shooter(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Driver1 = opMode.gamepad1;
        telemetry = opMode.telemetry;

        shooter = hardwareMap.get(DcMotorEx.class, "shooter1");
        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");

        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        velController = new PIDController(kP, kI, kD);

    }
    public double currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;


    public void teleOp() {
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        if (Driver1.left_bumper) {
            runShooter();
        } else {
            stopShooter();
        }
        if (Driver1.b) {
            reverseShooter();
        }
    }

    public void reverseShooter() {
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0; // positive measurement

        velController.setPID(kP3, kI3, kD3);

        double reverseTarget = 1500; // negative target = spin backwards
        double pid = velController.calculate(currentRPM, reverseTarget);

        shooter.setPower(-pid);
        shooter2.setPower(-pid);

        telemetry.addData("Reverse Current RPM", currentRPM);
        telemetry.addData("Reverse Target RPM", reverseTarget);
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
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        velController.setPID(kP2, kI2, kD2);

        double pid = velController.calculate(currentRPM, farTargetRPM);
        double feedforward = kF3 * farTargetRPM;          // F scales with target RPM
        double power = pid + feedforward;

        shooter.setPower(power);
        shooter2.setPower(power);

        telemetry.addData("FarCurrent RPM", currentRPM);
        telemetry.addData("FarTarget RPM", farTargetRPM);
        telemetry.addData("FF Power", feedforward);        // helpful for tuning
        telemetry.addData("Total Power", power);
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

}
