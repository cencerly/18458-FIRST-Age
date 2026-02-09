package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleTele", group = "TeleOp")
public class TeleTele extends OpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0, 0, 0);

    @Override
    public void init() {
        // Initialize follower
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        telemetry.addLine("Robot Initialized!");
        telemetry.update();
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        // Drive controls - field-centric
        follower.setTeleOpDrive(
                -gamepad1.left_stick_y,   // Forward/backward
                -gamepad1.left_stick_x,   // Strafe left/right
                -gamepad1.right_stick_x,  // Rotate
                true                       // Field-centric (true) or robot-centric (false)
        );

        // Update follower
        follower.update();

        // Telemetry
        telemetry.addData("X Position", follower.getPose().getX());
        telemetry.addData("Y Position", follower.getPose().getY());
        telemetry.addData("Heading (deg)", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();
    }

    @Override
    public void stop() {
        // Optional: add cleanup code here if needed
    }
}