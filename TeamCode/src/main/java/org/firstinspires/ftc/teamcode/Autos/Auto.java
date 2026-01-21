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
@Autonomous(name = "Auto", group = "Autonomous")
public final class Auto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(65, 20, Math.toRadians(180));
        Pose2d Score = new Pose2d(-28, 26, Math.toRadians(135));
        Pose2d Pos1 = new Pose2d(36, 28, Math.toRadians(90));
        Pose2d Pos2 = new Pose2d(36, 44, Math.toRadians(90));
        Pose2d Pos3 = new Pose2d(14, 28, Math.toRadians(90));
        Pose2d Pos4 = new Pose2d(14, 44, Math.toRadians(90));
        Pose2d Pos5 = new Pose2d(-11, 29, Math.toRadians(90));
        Pose2d Pos6 = new Pose2d(-11, 45, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Shooter shooter = new Shooter(this);
        Thing intake = new Thing(this);

        while (!opModeIsActive() && !isStopRequested()) {
  //add stuff you need for init
        }
        waitForStart();

        if (opModeIsActive()) {
            shooter.runShooter();
            Actions.runBlocking(
                    //score1

                    drive.actionBuilder(beginPose)
                            .splineToLinearHeading(new Pose2d(28, 26, Math.toRadians(135)), Math.toRadians(180))
                            .build());

            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();

            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(36, 28, Math.toRadians(90)), Math.toRadians(0))
                            .build());
            intake.IntakeOn();

            Actions.runBlocking(
                    drive.actionBuilder(Pos1)
                    .strafeTo(new Vector2d(36, 44))
                            .build());

            intake.IntakeReverse();
            sleep(70);
            intake.IntakeOff();
            sleep(300);
            shooter.runShooter();


            Actions.runBlocking(
                    drive.actionBuilder(Pos2)
                    .setTangent(Math.toRadians(270))
                    .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(180))
                            .build());


            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();

            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(14, 28, Math.toRadians(90)), Math.toRadians(0))
                            .build());

            intake.IntakeOn();

            Actions.runBlocking(
                    drive.actionBuilder(Pos3)
                            .strafeTo(new Vector2d(14, 44))
                            .build());

            intake.IntakeReverse();
            sleep(70);
            intake.IntakeOff();

            Actions.runBlocking(
                    drive.actionBuilder(Pos4)
                            .strafeTo(new Vector2d(4, 50))
                            .strafeTo(new Vector2d(4, 60))
                            .build());

            shooter.runShooter();

                    Actions.runBlocking(
                            drive.actionBuilder(Pos4)
                                    .setTangent(270)
                            .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(180))
                            .build());

            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();

            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(45))
                            .splineToLinearHeading(new Pose2d(-11, 29, Math.toRadians(90)), Math.toRadians(0))
                            .build());

            intake.IntakeOn();

            Actions.runBlocking(
                    drive.actionBuilder(Pos5)
                            .strafeTo(new Vector2d(-11, 45))
                            .build());

            intake.IntakeReverse();
            sleep(70);
            intake.IntakeOff();
            sleep(300);
            shooter.runShooter();

                    Actions.runBlocking(
                            drive.actionBuilder(Pos6)
                                    .setTangent(Math.toRadians(225))
                                    .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(225))
                            .build());

            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();
        }
    }
}