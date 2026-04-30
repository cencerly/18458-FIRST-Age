package org.firstinspires.ftc.teamcode.TeleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PedroPath.Constants;
import org.firstinspires.ftc.teamcode.PedroPath.Turret;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Thing;
import org.firstinspires.ftc.teamcode.Subsystems.TransferStopper;

@TeleOp
public class TeleOopBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        follower.startTeleopDrive();

        TransferStopper stopper = new TransferStopper(this);
        Shooter shooter = new Shooter(this);
        Thing thing = new Thing(this);
        Hood hood = new Hood(this);
       // Light light = new Light(this, shooter);
        Turret turret = new Turret(hardwareMap, follower, Turret.Alliance.BLUE);
        turret.init();

        boolean turretEnabled = false;
        boolean lastA = false;

        waitForStart();

        while (opModeIsActive()) {
            follower.setTeleOpDrive(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    false
            );
            follower.update();

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

            Pose pose = follower.getPose();
            double ticksPerSecond = shooter.shooter.getVelocity();
            double currentRPM = (ticksPerSecond / 28.0) * 60.0;

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
            telemetry.addData("Robot X", "%.1f", pose.getX());
            telemetry.addData("Robot Y", "%.1f", pose.getY());
            telemetry.addData("Heading", "%.1f°", Math.toDegrees(pose.getHeading()));
            telemetry.addLine();

            telemetry.addData("[dpad_up]", "Toggle Turret Tracking");
            telemetry.addData("[LB]", "Run Shooter");
            telemetry.addData("[X]", "Reverse Shooter");
            telemetry.update();
        }
    }
}