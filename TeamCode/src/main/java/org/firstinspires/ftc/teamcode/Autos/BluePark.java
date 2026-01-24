package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Thing;
import org.firstinspires.ftc.teamcode.Shooter;
@Config
@Autonomous(name = "BluePark", group = "Autonomous")
public final class BluePark extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d begin = new Pose2d(60, 10, Math.toRadians(180));
        Pose2d park = new Pose2d(40, 10, Math.toRadians(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, begin);
        Shooter shooter = new Shooter(this);
        Thing intake = new Thing(this);

        while (!opModeIsActive() && !isStopRequested()) {
            //add stuff you need for init
        }
        waitForStart();

        if (opModeIsActive()) {
            shooter.runShooter();

            // Score 1 - from start position
            Actions.runBlocking(
                    drive.actionBuilder(begin)
                            .strafeTo(new Vector2d(40, 10))
                            .build());
        }}}