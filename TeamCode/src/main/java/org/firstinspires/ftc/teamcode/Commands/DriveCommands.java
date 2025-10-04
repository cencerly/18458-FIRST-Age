package org.firstinspires.ftc.teamcode.Commands;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class DriveCommands {
    public MecanumDrive drive;
    public HardwareMap hardwareMap;

    public static Pose2d StartPose = new Pose2d(60, -15, Math.toRadians(180));

    public DriveCommands(OpMode opMode) {
        this.hardwareMap = opMode.hardwareMap;
        drive = new MecanumDrive(hardwareMap, StartPose);
    }

    public void StrafeToLinearHeading(Vector2d point, double heading, Pose2d pose) {
        Actions.runBlocking(
                drive.actionBuilder(pose)
                        .strafeToLinearHeading(point, heading)
                        .build());
    }

    public void StrafeToConstantHeading(Vector2d point, Pose2d pose) {
        Actions.runBlocking(
                drive.actionBuilder(pose)
                        .strafeToConstantHeading(point)
                        .build());

    }

    public void SplineToConstantHeading(Vector2d point, double tangent, Pose2d pose) {
        Actions.runBlocking(
                drive.actionBuilder(pose)
                        .splineToConstantHeading(point, Math.toRadians(tangent))
                        .build());
    }
    public void StrafeFast(Vector2d point, Pose2d pose, double minAccel, double maxAccel, double wheelVel) {

        Actions.runBlocking(
                drive.actionBuilder(pose)
                        .strafeToConstantHeading(point,
                                new TranslationalVelConstraint(wheelVel),
                                new ProfileAccelConstraint(minAccel, maxAccel))
                        .build()
        );
    }
    public void StrafeFastWAction(Vector2d point, Pose2d pose, double minAccel, double maxAccel, double wheelVel, Action movement) {
        Actions.runBlocking(
                drive.actionBuilder(pose)
                        .afterTime(0.25, movement)
                        .strafeToConstantHeading(point,
                                new TranslationalVelConstraint(wheelVel),
                                new ProfileAccelConstraint(minAccel, maxAccel))
                        .build()
        );
    }

    public void StrafeWAction(Vector2d point, Pose2d pose, Action movement, double time) {
        Actions.runBlocking(
                drive.actionBuilder(pose)
                        .afterTime(time, movement)
                        .strafeToConstantHeading(point)
                        .build()
        );
    }
}
