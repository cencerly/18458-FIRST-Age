package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class LimeLightDistanceTracking extends OpMode {
    private Limelight3A limelight;
    private IMU imu;
    private DistanceTrackingShooter shooter;

    // Target AprilTag IDs for each alliance
    private static final int RED_ALLIANCE_TAG = 24;  // Update with your actual tag IDs
    private static final int BLUE_ALLIANCE_TAG = 20; // Update with your actual tag IDs

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));

        // Initialize shooter
        shooter = new DistanceTrackingShooter(this);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Info", "Press A to toggle Auto RPM");
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        // Toggle auto RPM with A button
        if (gamepad1.a) {
            shooter.setAutoRPMEnabled(!shooter.useAutoRPM);
            sleep(200); // Debounce
        }

        // Update IMU orientation
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());

        // Get Limelight result
        LLResult llResult = limelight.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose_MT2();

            // Calculate distance
            double distance = calculateDistance(botPose);

            // Update shooter target RPM based on distance
            shooter.setTargetRPMFromDistance(distance);

            // Get detected tag ID
            int detectedTagId = -1;
            if (llResult.getFiducialResults() != null && !llResult.getFiducialResults().isEmpty()) {
                detectedTagId = (int) llResult.getFiducialResults().get(0).getFiducialId();
            }

            // Telemetry
            telemetry.addData("=== LIMELIGHT ===", "");
            telemetry.addData("Tx", llResult.getTx());
            telemetry.addData("Ty", llResult.getTy());
            telemetry.addData("Tag ID", detectedTagId);
            telemetry.addData("Distance (inches)", "%.2f", distance);

            if (detectedTagId == RED_ALLIANCE_TAG) {
                telemetry.addData("Target", "RED ALLIANCE GOAL");
            } else if (detectedTagId == BLUE_ALLIANCE_TAG) {
                telemetry.addData("Target", "BLUE ALLIANCE GOAL");
            }

            telemetry.addData("=== SHOOTER ===", "");
            telemetry.addData("Auto RPM", shooter.useAutoRPM ? "ENABLED" : "DISABLED");
            telemetry.addData("Target RPM", "%.0f", shooter.getTargetRPM());

        } else {
            telemetry.addData("Status", "No valid target");
            telemetry.addData("Auto RPM", shooter.useAutoRPM ? "ENABLED" : "DISABLED");
        }

        // Run shooter teleop ( X and left_bumper)
        shooter.teleOp();

        telemetry.update();
    }

    private double calculateDistance(Pose3D botPose) {
        double x = botPose.getPosition().x;
        double y = botPose.getPosition().y;
        double distance = Math.sqrt(x * x + y * y);

        // If Limelight returns meters, convert to inches
         distance = distance * 39.37;

        return distance;
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}