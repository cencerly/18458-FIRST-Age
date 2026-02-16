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
public class DistanceTrackingShooter {

    public static double kP = 0.005, kI = 0.0001, kD = 0.0001, kF = 0.00016;
    private PIDController velController;
    public DcMotorEx shooter, shooter2;

    public double targetRPM = 172;
    public double ntargetRPM = -160;
    public double ticksPerSecond;
    private final Gamepad Driver1;

    VoltageSensor voltageSensor;
    Telemetry telemetry;

    private static final double TICKS_PER_REV = 28;

    // Auto-adjustment settings
    public static boolean useAutoRPM = false; // Enable/disable auto RPM adjustment
    public static double BASE_RPM = 700;      // Tune these values
    public static double RPM_PER_INCH = 8.5;  // Tune these values
    public static int MIN_RPM = 600;
    public static int MAX_RPM = 1500;

    public DistanceTrackingShooter(OpMode opMode) {
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
        if (Driver1.x) {
            reverseShooter();
        } else if (Driver1.left_bumper) {
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
        telemetry.addData("Auto RPM", useAutoRPM ? "ENABLED" : "DISABLED");
        telemetry.addData("Power", power);
        telemetry.update();
    }

    /*
     Set target RPM based on distance from Limelight
     Call this from your main OpMode when you have a valid distance
     */
    public void setTargetRPMFromDistance(double distance) {
        if (useAutoRPM) {
            targetRPM = calculateRPMForDistance(distance);
        }
    }

    /*
     Calculate RPM based on distance - Linear formula
     */
    private double calculateRPMForDistance(double distance) {
        double calculatedRPM = BASE_RPM + (distance * RPM_PER_INCH);

        // Clamp to min/max for safety
        if (calculatedRPM < MIN_RPM) return MIN_RPM;
        if (calculatedRPM > MAX_RPM) return MAX_RPM;

        return calculatedRPM;
    }

    /*
       Lookup table method
     */
    private double calculateRPMForDistanceLookup(double distance) {
        if (distance < 24) return 700;
        else if (distance < 48) return 850;
        else if (distance < 72) return 1000;
        else if (distance < 96) return 1150;
        else if (distance < 120) return 1300;
        else return 1450;
    }

    /*
      Manually set target RPM (for manual control or testing)
     */
    public void setTargetRPM(double rpm) {
        targetRPM = rpm;
    }

    /*
     Enable or disable auto RPM adjustment
     */
    public void setAutoRPMEnabled(boolean enabled) {
        useAutoRPM = enabled;
    }

    public void reverseShooter() {
        ticksPerSecond = shooter.getVelocity();
        double currentRPM = (ticksPerSecond / TICKS_PER_REV) * -60.0;

        velController.setPID(kP, kI, kD);

        double pid = velController.calculate(currentRPM, ntargetRPM);
        double ff = kF * ntargetRPM;
        double power = pid - ff;
        shooter.setPower(power);
        shooter2.setPower(power);

        telemetry.addData("Current RPM", currentRPM);
        telemetry.addData("Reverse Target RPM", ntargetRPM);
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

    /*
      Get current target RPM (useful for telemetry)
     */
    public double getTargetRPM() {
        return targetRPM;
    }
}