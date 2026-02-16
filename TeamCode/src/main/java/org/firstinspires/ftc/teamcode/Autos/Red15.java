package org.firstinspires.ftc.teamcode.Autos;

import static org.firstinspires.ftc.teamcode.TurretRed.Alliance.RED;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.AThing;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Shooter;
import org.firstinspires.ftc.teamcode.Thing;

@Config
@Autonomous(name = "Red15", group = "Autonomous" )
public final class Red15 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(-63, 40, Math.toRadians(180));
        Pose2d score = new Pose2d(-30, 28, Math.toRadians(90));
        Pose2d intake1 = new Pose2d(5, 43, Math.toRadians(90));
        Pose2d intake2 = new Pose2d(4, 60, Math.toRadians(90));
        Pose2d intake3 = new Pose2d(35, 50, Math.toRadians(90));
        Pose2d intake4 = new Pose2d(-14, 48, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Shooter shooter = new Shooter(this);
        Thing intake = new Thing(this);
        AThing turret = new AThing(this);

        while (!opModeIsActive() && !isStopRequested()) {
            //add stuff you need for init
        }
        waitForStart();

        if (opModeIsActive()) {
            shooter.runShooter();
            turret.Turret.setTargetPosition(135);
            turret.Turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            turret.setTurretPower(1);

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .setTangent(320)
                            .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(335))
                            .build());
            sleep(50);
            intake.IntakeOn();
            sleep(1500);
            shooter.stopShooter();

            Actions.runBlocking(
                    drive.actionBuilder(score)
                            .setTangent(320)
                            .splineToLinearHeading(new Pose2d(4, 31, Math.toRadians(90)), Math.toRadians(30))
                            .splineToLinearHeading(new Pose2d(5, 43, Math.toRadians(90)), Math.toRadians(90))
                            .build());
            intake.IntakeReverse();
            shooter.reverseShooter();
            sleep(40);
            shooter.stopShooter();
            intake.IntakeOff();
            sleep(50);
            shooter.runShooter();

            Actions.runBlocking(
                    drive.actionBuilder(intake1)
                            .setTangent(225)
                            .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(160))
                            .build());
            sleep(50);
            intake.IntakeOn();
            sleep(1500);
            shooter.stopShooter();
            intake.IntakeOff();

            Actions.runBlocking(
                    drive.actionBuilder(score)
                            .setTangent(270)
                            .splineToLinearHeading(new Pose2d(4, 60, Math.toRadians(90)), Math.toRadians(110))
                            .splineToLinearHeading(new Pose2d(10, 59, Math.toRadians(115)), Math.toRadians(270))
                            .build());
            intake.IntakeOn();
            shooter.reverseShooter();
            sleep(2000);
            intake.IntakeReverse();
            sleep(50);
            shooter.runShooter();
            intake.IntakeOff();

            Actions.runBlocking(
                    drive.actionBuilder(intake2)
            .setTangent(270)
                    .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(270))
                            .build());
            intake.IntakeOn();
            sleep(1500);
            intake.IntakeReverse();
            shooter.reverseShooter();
            sleep(50);
            shooter.runShooter();
            intake.IntakeOn();


            Actions.runBlocking(
                    drive.actionBuilder(score)
                            .setTangent(0)
                            .splineToLinearHeading(new Pose2d(30, 30, Math.toRadians(90)), Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(35, 50, Math.toRadians(90)), Math.toRadians(90))
                            .build());
            shooter.runShooter();

            Actions.runBlocking(
                    drive.actionBuilder(intake3)
                            .setTangent(180)
                            .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(180))
                            .build());
            sleep(50);
            intake.IntakeOn();
            sleep(1500);
            shooter.stopShooter();

            Actions.runBlocking(
                    drive.actionBuilder(score)
                            .setTangent(270)
                            .splineToLinearHeading(new Pose2d(-14, 48, Math.toRadians(90)), Math.toRadians(90))
                            .build());
            intake.IntakeReverse();
            shooter.reverseShooter();
            sleep(40);
            shooter.stopShooter();
            intake.IntakeOff();
            sleep(50);
            shooter.runShooter();

            Actions.runBlocking(
                    drive.actionBuilder(intake4)
                            .setTangent(180)
                            .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(225))
                            .build());
            sleep(50);
            intake.IntakeOn();
            sleep(1500);
            shooter.stopShooter();
            intake.IntakeOff();
        }
    }
}
