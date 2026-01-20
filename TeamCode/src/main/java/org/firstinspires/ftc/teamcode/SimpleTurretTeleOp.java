package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.Turret;

@TeleOp(name = "Simple Turret TeleOp")
public class SimpleTurretTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        Turret turret = new Turret(hardwareMap, drive);

        // Set target to basket position (adjust these coordinates for your field)
        turret.setTargetPosition(-64, 60);

        boolean turretEnabled = false;
        boolean lastA = false;

        waitForStart();

        while (opModeIsActive()) {
            // Update localization
            drive.updatePoseEstimate();

            // Drive with left stick and right stick
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            gamepad1.left_stick_x
                    ),
                    gamepad1.right_stick_x
            ));

            // Toggle turret tracking with A button
            boolean currentA = gamepad1.a;
            if (currentA && !lastA) {
                turretEnabled = !turretEnabled;
                if (!turretEnabled) {
                    turret.stop();
                }
            }
            lastA = currentA;

            // Turret tracks target only when enabled
            if (turretEnabled) {
                turret.update();
            }

            // Simple telemetry
            Pose2d pose = drive.localizer.getPose();
            telemetry.addData("Turret Tracking", turretEnabled ? "ON" : "OFF");
            telemetry.addData("X", pose.position.x);
            telemetry.addData("Y", pose.position.y);
            telemetry.addData("Heading", Math.toDegrees(pose.heading.toDouble()));
            telemetry.addData("Turret Angle", turret.getTurretAngleDeg());
            telemetry.update();
        }
    }
}