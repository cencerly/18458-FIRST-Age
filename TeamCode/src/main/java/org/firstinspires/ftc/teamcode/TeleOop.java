package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@TeleOp
public class TeleOop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DT dt = new DT(this);
        Shooter shooter = new Shooter(this);
        Thing thing = new Thing(this);

        // Initialize RoadRunner drive and Turret
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        // SET YOUR ALLIANCE HERE: Turret.Alliance.RED or Turret.Alliance.BLUE
        Turret turret = new Turret(hardwareMap, drive, Turret.Alliance.RED);

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
            boolean currentA = gamepad1.a;
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

            // Turret telemetry
            Pose2d pose = drive.localizer.getPose();
            telemetry.addLine("=== TURRET ===");
            telemetry.addData("Tracking", turretEnabled ? "ON" : "OFF");
            telemetry.addData("Alliance", turret.getAlliance());
            telemetry.addData("Turret Angle", "%.1f°", turret.getTurretAngleDeg());
            telemetry.addData("Distance", "%.1f in", turret.getDistanceToTarget());
            telemetry.addData("Aimed", turret.isAimedAtTarget(3.0) ? "✓" : "X");
            telemetry.addLine();
            telemetry.addData("Robot X", "%.1f", pose.position.x);
            telemetry.addData("Robot Y", "%.1f", pose.position.y);
            telemetry.addData("Heading", "%.1f°", Math.toDegrees(pose.heading.toDouble()));
            telemetry.addLine();
            telemetry.addData("[A]", "Toggle Turret Tracking");
            telemetry.update();
        }
    }
}