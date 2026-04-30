package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
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

    private PIDController velController;
    public DcMotorEx shooter, shooter2;

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

        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);

        velController = new PIDController(kP, kI, kD);
    }

    public void teleOp() {
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        if (Driver1.left_bumper) {
            runShooter();
        } else if (Driver1.x) {
            reverseShooter();
        } else {
            stopShooter();
        }
    }

    public void runShooter() {
        ticksPerSecond = shooter.getVelocity();
        currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0; // NO local variable here

        velController.setPID(kP, kI, kD);

        double pid = velController.calculate(currentRPM, targetRPM);
        shooter.setPower(pid);
        shooter2.setPower(pid);

        telemetry.addData("CloseCurrent RPM", currentRPM);
        telemetry.addData("CloseTarget RPM", targetRPM);
        telemetry.addData("Power", pid);
    }
    public double currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

    public void runFarShooter() {

        ticksPerSecond = shooter2.getVelocity();

        velController.setPID(kP2, kI2, kD2);

        double pid = velController.calculate(currentRPM, farTargetRPM);
        double power = pid;
        shooter.setPower(power);
        shooter2.setPower(power);
    }


    public void reverseShooter() {
        shooter.setPower(-1);
        shooter2.setPower(-1);
    }

    public void directSet(double p) {
        shooter.setPower(p);
        shooter2.setPower(p);
    }

    public void stopShooter() {
        shooter.setPower(0);
        shooter2.setPower(0);
    }
}
