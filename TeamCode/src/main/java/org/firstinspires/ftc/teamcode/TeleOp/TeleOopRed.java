package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Thing;
import org.firstinspires.ftc.teamcode.Subsystems.TransferStopper;
import org.firstinspires.ftc.teamcode.Subsystems.TurretRed;

@TeleOp
public class TeleOopRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DT dt = new DT(this);
        TransferStopper stopper = new TransferStopper(this);
        Shooter shooter = new Shooter(this);
        Thing thing = new Thing(this);
        Hood hood = new Hood(this);
        // Light light = new Light(this, shooter);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(30, 30, Math.toRadians(135)));

        TurretRed turret = new TurretRed(hardwareMap, drive, TurretRed.Alliance.RED);

        boolean turretEnabled = false;
        boolean lastA = false;

        waitForStart();

        while (opModeIsActive()) {
            drive.updatePoseEstimate();

            dt.teleop();
            stopper.teleop();
            thing.teleOp();
            shooter.teleOp();
            hood.teleop();
            // light.teleop();


            boolean currentA = gamepad1.dpad_up;
            if (currentA && !lastA) {
                turretEnabled = !turretEnabled;
                if (!turretEnabled) turret.stop();
            }
            lastA = currentA;

            if (turretEnabled) {
                turret.update();
            }

            double ticksPerSecond = shooter.shooter.getVelocity();
            double currentRPM = (ticksPerSecond / 28.0) * 60.0;
            Pose2d pose = drive.localizer.getPose();

            telemetry.addLine("=== TURRET ===");
            telemetry.addData("Tracking", turretEnabled ? "ON" : "OFF");
            telemetry.addData("Turret Angle", "%.1f°", turret.getTurretAngleDeg());
            telemetry.addData("Distance", "%.1f in", turret.getDistanceToTarget());
            telemetry.addData("Aimed", turret.isAimedAtTarget(3.0) ? "✓" : "X");
            telemetry.addLine();

            telemetry.addLine("=== SHOOTER ===");
            telemetry.addData("Current RPM", "%.0f", currentRPM);
            telemetry.addData("Target RPM", "%.0f", shooter.targetRPM);
            telemetry.addData("FarTargetRPM", "%.0f", shooter.farTargetRPM);
            telemetry.addData("Shooter Power", "%.3f", shooter.shooter.getPower());
            telemetry.addData("RAW VELOCITY", shooter.shooter.getVelocity());
            telemetry.addLine();

            telemetry.addLine("=== ROBOT POSE ===");
            telemetry.addLine();

            telemetry.addData("[dpad_up]", "Toggle Turret Tracking");
            telemetry.addData("[LB]", "Run Shooter");
            telemetry.addData("[X]", "Reverse Shooter");

            telemetry.addData("Stopper Pos:", "%.1f°",stopper.left.getPosition());
            telemetry.update();
        }
    }
}