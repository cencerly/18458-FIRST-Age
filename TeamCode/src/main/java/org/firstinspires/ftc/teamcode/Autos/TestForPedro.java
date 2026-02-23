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
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Shooter;
import org.firstinspires.ftc.teamcode.Thing;

@Config
@Autonomous(name = "Test", group = "Autonomous")
public class TestForPedro extends OpMode {

    private Follower follower;
    private Timer pathTimer, opModeTimer;
    Shooter shooter;
    Thing intake;
    private Paths paths;

    public static class Paths {
        public PathChain Preload;
        public PathChain Intake1;
        public PathChain Score1;
        public PathChain Empty;
        public PathChain Intake2;
        public PathChain Score2;
        public PathChain Intake3;
        public PathChain Score3;
        public PathChain Intake4;
        public PathChain Score4;


        public Paths(Follower follower) {
        Preload = follower.pathBuilder().addPath(
        new BezierLine(
                new Pose(109.112, 135.448),

                new Pose(103.000, 102.000)
        )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))

                .build();

        Intake1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(103.000, 102.000),
                                new Pose(86.000, 50.000),
                                new Pose(106.000, 60.000),
                                new Pose(130.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Score1 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(130.000, 60.000),
                                new Pose(89.000, 53.000),
                                new Pose(103.000, 102.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Empty = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(103.000, 102.000),
                                new Pose(85.000, 66.000),
                                new Pose(128.000, 68.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Intake2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(128.000, 68.000),
                                new Pose(125.000, 53.000),
                                new Pose(138.000, 60.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(45))

                .build();

        Score2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(138.000, 60.000),
                                new Pose(86.000, 69.000),
                                new Pose(103.000, 102.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))

                .build();

        Intake3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(103.000, 102.000),
                                new Pose(88.000, 22.000),
                                new Pose(106.000, 36.000),
                                new Pose(129.000, 36.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Score3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(129.000, 36.000),
                                new Pose(87.000, 90.000),
                                new Pose(103.000, 100.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Intake4 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(103.000, 100.000),
                                new Pose(100.000, 78.000),
                                new Pose(102.000, 83.000),
                                new Pose(129.000, 83.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

                .build();

        Score4 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(129.000, 83.000),

                                new Pose(103.000, 102.000)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(0))

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

        follower.setPose(new Pose(110, 135, Math.toRadians(90)));
    }
    public enum PathState {
        PRELOAD,
        INTAKE1, SCORE1,
        INTAKE2, SCORE2,
        INTAKE3, SCORE3,
        INTAKE4, SCORE4,
        DONE
    }
    PathState pathState;
    public void statePathUpdate() {
        switch (pathState) {
            case PRELOAD:
                shooter.runShooter();

                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() < 0.1) {
                    intake.IntakeOn();
                }
                    if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 1.3) {
                        shooter.stopShooter();
                        follower.followPath(paths.Intake1, true);
                        setPathState(PathState.INTAKE1);
                }
                    break;
            case INTAKE1:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                    follower.followPath(paths.Score1, true);
                    setPathState(PathState.SCORE1);
                }
                break;
            case SCORE1:
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    follower.followPath(paths.Intake2, true);
                    setPathState(PathState.INTAKE2);
                }
                break;
            case INTAKE2:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                    follower.followPath(paths.Score2, true);
                    setPathState(PathState.SCORE2);
                }
                break;
            case SCORE2:
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    follower.followPath(paths.Intake3, true);
                    setPathState(PathState.INTAKE3);
                }
                break;
            case INTAKE3:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                    follower.followPath(paths.Score3, true);
                    setPathState(PathState.SCORE3);
                }
                break;
            case SCORE3:
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    follower.followPath(paths.Intake4, true);
                    setPathState(PathState.INTAKE4);
                }
                break;
            case INTAKE4:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                    follower.followPath(paths.Score4, true);
                    setPathState(PathState.SCORE4);
                }
                break;
            case SCORE4:
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    setPathState(PathState.DONE);
                }
                break;
            case DONE:
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

    }
}