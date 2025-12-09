package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Shooter {

    public static double kP = 0.001, kI = 0, kD = 0, kF = 0.00016;
    private PIDController velController;
    public DcMotorEx shooter, shooter2;

    public double targetRPM = 4000;
    public double ticksPerSecond;
    private final Gamepad Driver1;

    VoltageSensor voltageSensor;
    Telemetry telemetry;

    private static final double TICKS_PER_REV = 28;

    public Shooter(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Driver1 = opMode.gamepad1;
        telemetry = opMode.telemetry;

        shooter = hardwareMap.get(DcMotorEx.class, "shooter1");
        shooter2 = hardwareMap.get(DcMotorEx.class, "shooter2");

        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);

        voltageSensor = hardwareMap.voltageSensor.iterator().next();

        velController = new PIDController(kP, kI, kD);
    }

    public void teleOp() {
        if (Driver1.left_bumper) {
            runShooter();
        } else {
            stopShooter();
        }
    }

    public void runShooter() {

        ticksPerSecond = shooter.getVelocity();

        double currentRPM = (ticksPerSecond / TICKS_PER_REV) * 60.0;

        velController.setPID(kP, kI, kD);

        double pid = velController.calculate(currentRPM, targetRPM);
        double ff = kF * targetRPM;
        double power = pid + ff;
        shooter.setPower(power);
        shooter2.setPower(power);

        telemetry.addData("Current RPM", currentRPM);
        telemetry.addData("Target RPM", targetRPM);
        telemetry.addData("Power", power);
        telemetry.update();
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
