package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Shooter;
import org.firstinspires.ftc.teamcode.Thing;
import org.firstinspires.ftc.teamcode.TurretRed;

@Config
@Autonomous(name = "ShootTest", group = "Autonomous")
public final class ShootTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-28, 26, Math.toRadians(0)));

        TurretRed turret = new TurretRed(hardwareMap, drive, TurretRed.Alliance.RED);

        boolean turretEnabled = true;

        Shooter shooter = new Shooter(this);
        Thing intake = new Thing(this);

        while (!opModeIsActive() && !isStopRequested()) {
            // Only display telemetry, no turret movement

            telemetry.addData("Status", "Initialized - Ready to track goal");
            telemetry.addData("Turret Enabled", turretEnabled);
            telemetry.update();
        }

        waitForStart();

        if (opModeIsActive()) {
            while (!turret.isAimedAtTarget(5.0) && opModeIsActive()) {
                drive.updatePoseEstimate();
                turret.update();
                telemetry.addData("Status", "Aiming at goal...");
                telemetry.update();
            }

            shooter.runShooter();
            sleep(2000);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();

            telemetry.addData("Status", "Shot complete");
            telemetry.update();
        }
    }
}
