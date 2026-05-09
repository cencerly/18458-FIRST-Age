package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.PedroPath.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Thing;
import org.firstinspires.ftc.teamcode.Subsystems.TransferStopper;

@Config
@Autonomous(name = "BlueAuto", group = "Autonomous")
public class CloseBlueAuto extends OpMode {


    private Follower follower;
    private Timer pathTimer, opModeTimer;
    Shooter shooter;
    Thing intake;
    TransferStopper stopper;
    private Paths paths;

    public static class Paths {
        public PathChain Preload;
        public PathChain Intake1A;
        public PathChain Intake1B;
        public PathChain Score1;
        public PathChain Intake2A;
        public PathChain Intake2B;
        public PathChain Empty;
        public PathChain Score2;
        public PathChain Intake3;
        public PathChain Score3;

        public PathChain Park;

        public Paths(Follower follower) {
            Preload = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(32.000, 135.000),
                                    new Pose(48.000, 96.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(130))
                    .build();

            Intake1A = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(48.000, 96.000),
                                    new Pose(58.000, 71.000),
                                    new Pose(41.000, 72.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                    .build();

            Intake1B = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(41.000, 72.000),
                                    new Pose(14.000, 72.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            Score1 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(14.000, 72.000),
                                    new Pose(58.000, 72.000),
                                    new Pose(48.000, 96.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                    .build();

            Intake2A = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(48.000, 96.000),
                                    new Pose(67.000, 46.000),
                                    new Pose(41.000, 47.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                    .build();

            Intake2B = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(41.000, 47.000),
                                    new Pose(14.000, 47.000)
                            )
                    )
                    .setConstantHeadingInterpolation(Math.toRadians(180))
                    .build();

            Empty = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(14.000, 47.000),
                                    new Pose(24.617, 56.422),
                                    new Pose(15.361, 61.948)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            Score2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(15.361, 61.948),
                                    new Pose(48.000, 96.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                    .build();

            Intake3 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(48.000, 96.000),
                                    new Pose(77.041, 25.956),
                                    new Pose(41.000, 24.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                    .build();

            Score3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(41.000, 24.000),
                                    new Pose(14.000, 24.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                    .build();

            Park = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(14.000, 24.000),
                                    new Pose(70.000, 30.000),
                                    new Pose(48.000, 96.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(130))
                    .build();
        }
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        shooter = new Shooter(this);
        intake = new Thing(this);
        paths = new Paths(follower);
        stopper = new TransferStopper(this);

        follower.setPose(new Pose(110, 135, Math.toRadians(90)));
    }
    public enum PathState {
        PRELOAD,
        SCORE1,
        Intake1A, INTAKE1B,
        SCORE2,
        INTAKE2A, INTAKE2B,
        SCORE3,
        EMPTY, PARK,
        INTAKE3,
    }
    PathState pathState;
    public void statePathUpdate() {
        switch (pathState) {
            case PRELOAD:
                if (pathTimer.getElapsedTimeSeconds() >= .1) {
                    shooter.runShooter();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 1.7) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2.8) {
                    shooter.reverseShooter();
                    intake.IntakeOff();
                    follower.followPath(paths.Intake1A, true);
                    setPathState(PathState.Intake1A);
                }
                break;
            case Intake1A:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                    follower.followPath(paths.Intake1B, true);
                    setPathState(PathState.INTAKE1B);
                }
                break;
            case INTAKE1B:
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    shooter.runShooter();
                    follower.followPath(paths.Score1, true);
                    setPathState(PathState.SCORE1);
                }
                break;
            case SCORE1:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 4.3) {
                    intake.IntakeOff();
                    follower.followPath(paths.Intake2A, true);
                    setPathState(PathState.INTAKE2A);
                }
                break;
            case INTAKE2A:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Intake2B, true);
                    setPathState(PathState.INTAKE2B);
                }
                break;
            case INTAKE2B:
                if (pathTimer.getElapsedTimeSeconds() >.1) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    follower.followPath(paths.Empty, true);
                    setPathState(PathState.EMPTY);
                }
                break;
            case EMPTY:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Score2, true);
                    setPathState(PathState.SCORE2);
                }
                break;
            case SCORE2:
                if (pathTimer.getElapsedTimeSeconds() >=2.4 && pathTimer.getElapsedTimeSeconds() <= 3.9) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 4) {
                    intake.IntakeOff();
                    follower.followPath(paths.Intake3, true);
                    setPathState(PathState.INTAKE3);
                }
                break;
            case INTAKE3:
                if (pathTimer.getElapsedTimeSeconds() >= 3.5) {
                    follower.followPath(paths.Score3, true);
                    setPathState(PathState.SCORE3);
                }
                break;
            case SCORE3:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2.8) {
                    follower.followPath(paths.Park, true);
                    setPathState(PathState.PARK);
                }
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    public void start() {
        opModeTimer.resetTimer();
        follower.followPath(paths.Preload, true);
        setPathState(PathState.PRELOAD);

    }

    @Override
    public void loop() {

        follower.update();
        statePathUpdate();

        telemetry.addData("Path State", pathState.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
        telemetry.addData("Path cycle: ", pathState);

    }
}