package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous
public class AprilTagLimeLightTest extends OpMode {

    private Limelight3A limelight;
    private IMU imu;
    private AThing thing;

    // === PD TUNING CONSTANTS ===
    private static final double kP = 0.025;
    private static final double kD = 0.003;

    private static final double MAX_POWER = 0.3;
    private static final double TX_DEADBAND = 0.25; // degrees

    private double previousError = 0;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        thing = new AThing(this);

        limelight.pipelineSwitch(8); // AprilTag pipeline

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(orientation));

        // Turret setup
        thing.Turret.setZeroPowerBehavior(
                com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE
        );
        thing.Turret.setMode(
                com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER
        );
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        // Update robot heading for Limelight
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(angles.getYaw());

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {

            double tx = result.getTx(); // degrees error

            //  DEADBAND
            if (Math.abs(tx) < TX_DEADBAND) {
                thing.Turret.setPower(0);
                previousError = 0;
            } else {
                // NEGATIVE fixes direction
                double error = -tx;
                double derivative = error - previousError;

                double power = (kP * error) + (kD * derivative);
                previousError = error;

                // Clamp power
                power = Math.max(-MAX_POWER, Math.min(MAX_POWER, power));

                thing.Turret.setPower(power);
            }

            telemetry.addData("tx (deg)", tx);
            telemetry.addData("Turret Power", thing.Turret.getPower());

        } else {
            // No target → stop turret
            thing.Turret.setPower(0);
            previousError = 0;
        }

        telemetry.update();
    }
}

