package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@TeleOp
public class TeleOopRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DT dt = new DT(this);
        Shooter shooter = new Shooter(this);
        Thing thing = new Thing(this);

        // Initialize RoadRunner drive and Turret
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-28, 26, Math.toRadians(225)));

        // SET YOUR ALLIANCE HERE: Turret.Alliance.RED or Turret.Alliance.BLUE
        TurretRed turret = new TurretRed(hardwareMap, drive, TurretRed.Alliance.RED);

        boolean turretEnabled = false;
        boolean lastA = false;

        waitForStart();

        while (opModeIsActive()) {
            // Update RoadRunner localization
            drive.updatePoseEstimate();

            // Existing subsystems
            dt.teleop();
            thing.teleOp();
            shooter.teleOp();

            // Toggle turret tracking with A button
            boolean currentA = gamepad1.right_stick_button;
            if (currentA && !lastA) {
                turretEnabled = !turretEnabled;
                if (!turretEnabled) {
                    turret.stop();
                }
            }
            lastA = currentA;

            // Update turret when enabled
            if (turretEnabled) {
                turret.update();
            }

            // Get shooter data
            double ticksPerSecond = shooter.shooter.getVelocity();
            double currentRPM = (ticksPerSecond / 28.0) * 60.0;

            // Turret telemetry
            Pose2d pose = drive.localizer.getPose();
            telemetry.addLine("=== TURRET ===");
            telemetry.addData("Tracking", turretEnabled ? "ON" : "OFF");
            telemetry.addData("Alliance", turret.getAlliance());
            telemetry.addData("Turret Angle", "%.1f°", turret.getTurretAngleDeg());
            telemetry.addData("Distance", "%.1f in", turret.getDistanceToTarget());
            telemetry.addData("Aimed", turret.isAimedAtTarget(3.0) ? "✓" : "X");
            telemetry.addLine();

            // Shooter telemetry
            telemetry.addLine("=== SHOOTER ===");
            telemetry.addData("Current RPM", "%.0f", currentRPM);
            telemetry.addData("Target RPM", "%.0f", shooter.targetRPM);
            telemetry.addData("Shooter Power", "%.3f", shooter.shooter.getPower());
            telemetry.addLine();

            // Robot pose telemetry
            telemetry.addLine("=== ROBOT POSE ===");
            telemetry.addData("Robot X", "%.1f", pose.position.x);
            telemetry.addData("Robot Y", "%.1f", pose.position.y);
            telemetry.addData("Heading", "%.1f°", Math.toDegrees(pose.heading.toDouble()));
            telemetry.addLine();

            telemetry.addData("[A]", "Toggle Turret Tracking");
            telemetry.addData("[LB]", "Run Shooter");
            telemetry.addData("[X]", "Reverse Shooter");

            telemetry.update();
        }
    }
}