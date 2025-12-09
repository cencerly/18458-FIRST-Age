package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;


@Config
@Autonomous(name = "Auto", group = "Autonomous")
public final class Auto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(65, 20, Math.toRadians(180));
        Pose2d Score = new Pose2d(-24, 23, Math.toRadians(135));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);


        while (!opModeIsActive() && !isStopRequested()) {
  //add stuff you need for init
        }

        waitForStart();

        if (opModeIsActive()) {
            Actions.runBlocking(
                    //score1
                    drive.actionBuilder(beginPose)
                            .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(180))
                            .build());
            sleep(1000);
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(35, 28, Math.toRadians(90)), Math.toRadians(0))
                            .strafeTo(new Vector2d(35, 48))
                            .setTangent(Math.toRadians(270))
                            .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(180))
                            .build());
            sleep(1000);
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(12, 28, Math.toRadians(90)), Math.toRadians(0))
                            .strafeTo(new Vector2d(12, 48))
                            .strafeTo(new Vector2d(3, 60))
                            .setTangent(Math.toRadians(300))
                            .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(180))
                            .build());
            sleep(1000);
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(45))
                            .splineToLinearHeading(new Pose2d(-11, 29, Math.toRadians(90)), Math.toRadians(0))
                            .strafeTo(new Vector2d(-11, 48))
                            .setTangent(Math.toRadians(225))
                            .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(225))
                            .build());
        }sleep(3000);
    }
}