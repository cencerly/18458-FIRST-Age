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
@Autonomous(name = "BlueAuto", group = "Autonomous")
public final class BlueAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-63, -40, Math.toRadians(180));
        Pose2d Score = new Pose2d(-28, -26, Math.toRadians(225));
        Pose2d Pos1 = new Pose2d(36, -24, Math.toRadians(270));
        Pose2d Pos2 = new Pose2d(36, -50, Math.toRadians(270));
        Pose2d Pos3 = new Pose2d(10, -24, Math.toRadians(270));
        Pose2d Pos4 = new Pose2d(10, -50, Math.toRadians(270));
        Pose2d Pos5 = new Pose2d(-11, -24, Math.toRadians(270));
        Pose2d Pos6 = new Pose2d(-11, -50, Math.toRadians(270));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
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
                    drive.actionBuilder(beginPose)
                            .setTangent(Math.toRadians(5))
                            .splineToLinearHeading(new Pose2d(-28, -26, Math.toRadians(225)), Math.toRadians(45))
                            .build());
            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();

            // Pair 3: Pos5 -> Pos6
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(360))
                            .splineToLinearHeading(new Pose2d(-11, -24, Math.toRadians(270)), Math.toRadians(360))
                            .build());
            intake.IntakeOn();
            Actions.runBlocking(
                    drive.actionBuilder(Pos5)
                            .strafeTo(new Vector2d(-11, -50))
                            .build());
            intake.IntakeReverse();
            shooter.reverseshooter();
            sleep(40);
            shooter.stopShooter();
            intake.IntakeOff();
            sleep(300);
            shooter.runShooter();
            Actions.runBlocking(
                    drive.actionBuilder(Pos6)
                            .setTangent(Math.toRadians(135))
                            .splineToLinearHeading(new Pose2d(-28, -26, Math.toRadians(225)), Math.toRadians(135))
                            .build());
            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();

            // Pair 2: Pos3 -> Pos4
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(360))
                            .splineToLinearHeading(new Pose2d(10, -24, Math.toRadians(270)), Math.toRadians(360))
                            .build());
            intake.IntakeOn();
            Actions.runBlocking(
                    drive.actionBuilder(Pos3)
                            .strafeTo(new Vector2d(10, -50))
                            .build());
            intake.IntakeReverse();
            shooter.reverseshooter();
            sleep(70);
            shooter.stopShooter();
            intake.IntakeOff();
            Actions.runBlocking(
                    drive.actionBuilder(Pos4)
                            .strafeTo(new Vector2d(4, -50))
                            .strafeTo(new Vector2d(4, -60))
                            .build());
            shooter.runShooter();
            Actions.runBlocking(
                    drive.actionBuilder(Pos4)
                            .setTangent(Math.toRadians(90))
                            .splineToLinearHeading(new Pose2d(-28, -26, Math.toRadians(225)), Math.toRadians(180))
                            .build());
            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();

            // Pair 1: Pos1 -> Pos2
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .setTangent(Math.toRadians(360))
                            .splineToLinearHeading(new Pose2d(36, -24, Math.toRadians(270)), Math.toRadians(360))
                            .build());
            intake.IntakeOn();
            Actions.runBlocking(
                    drive.actionBuilder(Pos1)
                            .strafeTo(new Vector2d(36, -50))
                            .build());
            intake.IntakeReverse();
            shooter.reverseshooter();
            sleep(70);
            intake.IntakeOff();
            shooter.stopShooter();
            sleep(300);
            shooter.runShooter();
            Actions.runBlocking(
                    drive.actionBuilder(Pos2)
                            .setTangent(Math.toRadians(90))
                            .splineToLinearHeading(new Pose2d(-28, -26, Math.toRadians(225)), Math.toRadians(180))
                            .build());
            sleep(300);
            intake.IntakeOn();
            sleep(2000);
            shooter.stopShooter();
            intake.IntakeOff();
        }
    }
}