package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@TeleOp(name = "Turret TeleOp")
public class SimpleTurretTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize drive system
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        // Initialize turret - SET YOUR ALLIANCE HERE
        TurretRed turret = new TurretRed(hardwareMap, drive, TurretRed.Alliance.BLUE);

        // Turret tracking mode
        boolean turretEnabled = true; // Start with tracking ON
        boolean lastA = false;

        // Alliance selection
        boolean lastB = false;

        waitForStart();

        while (opModeIsActive()) {
            // Update robot localization
            drive.updatePoseEstimate();

            // Drive control: left stick for translation, right stick X for rotation
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x
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

            // Toggle alliance with B button
            boolean currentB = gamepad1.b;
            if (currentB && !lastB) {
                if (turret.getAlliance() == TurretRed.Alliance.BLUE) {
                    turret.setAlliance(TurretRed.Alliance.RED);
                } else {
                    turret.setAlliance(TurretRed.Alliance.BLUE);
                }
            }
            lastB = currentB;

            // Continuous turret tracking when enabled
            if (turretEnabled) {
                turret.update();
            }

            // Telemetry
            Pose2d pose = drive.localizer.getPose();
            Vector2d targetGoal = turret.getTargetGoal();

            telemetry.addLine("=== TURRET STATUS ===");
            telemetry.addData("Tracking", turretEnabled ? "ENABLED" : "DISABLED");
            telemetry.addData("Alliance", turret.getAlliance());
            telemetry.addData("Turret Angle", "%.1f°", turret.getTurretAngleDeg());
            telemetry.addData("Distance to Goal", "%.1f inches", turret.getDistanceToTarget());
            telemetry.addData("Aimed", turret.isAimedAtTarget(3.0) ? "YES ✓" : "NO");
            telemetry.addLine();

            telemetry.addLine("=== ROADRUNNER LOCALIZATION ===");
            telemetry.addData("Robot X", "%.2f", pose.position.x);
            telemetry.addData("Robot Y", "%.2f", pose.position.y);
            telemetry.addData("Robot Heading", "%.1f°", Math.toDegrees(pose.heading.toDouble()));
            telemetry.addLine();

            telemetry.addLine("=== TARGET GOAL ===");
            telemetry.addData("Goal X", "%.1f", targetGoal.x);
            telemetry.addData("Goal Y", "%.1f", targetGoal.y);
            telemetry.addData("Delta X", "%.1f", targetGoal.x - pose.position.x);
            telemetry.addData("Delta Y", "%.1f", targetGoal.y - pose.position.y);
            telemetry.addLine();

            telemetry.addLine("=== CONTROLS ===");
            telemetry.addData("[A]", "Toggle Tracking");
            telemetry.addData("[B]", "Switch Alliance");
            telemetry.update();
        }
    }
}