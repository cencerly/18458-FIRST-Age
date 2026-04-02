package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.BezierLine;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.PedroPath.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Thing;

@Config
@Autonomous(name = "Test", group = "Autonomous")
public class RedAuto extends OpMode {

    Limelight3A ll;
    private Follower follower;
    private Timer pathTimer, opModeTimer;
    Shooter shooter;
    Thing intake;
    private Paths paths;

    public static class Paths {
        public PathChain Preload;
        public PathChain Intake1;
        public PathChain Score1;
        public PathChain Intake2;
        public PathChain Empty;
        public PathChain Score2;
        public PathChain Intake3;
        public PathChain Score3;
        public PathChain Park;


        public Paths(Follower follower) {
            Preload = follower.pathBuilder().addPath(
                    new BezierLine(
                            new Pose(109.112, 135.448),
                            new Pose(103.000, 102.000)
        )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(45))

                .build();

        Intake1 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(103.000, 102.000),
                                    new Pose(96.000, 78.000),
                                    new Pose(96.000, 83.000),
                                    new Pose(133.000, 81.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                    .build();

            Score1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(133.000, 81.000),

                                    new Pose(103.000, 102.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(46))

                    .build();

        Intake2 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(103.000, 102.000),
                                new Pose(80.000, 47.000),
                                new Pose(105.000, 47.000),
                                new Pose(144.500, 47.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))

                .build();
            Empty = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(144.000, 47.000),
                                    new Pose(85.000, 66.000),
                                    new Pose(140.000, 68.000)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(0))

                    .build();

        Score2 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(140.500, 68.000),
                                new Pose(103.000, 102.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(48))

                .build();

        Intake3 = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(103.000, 102.000),
                                new Pose(70.775, 74.586),
                                new Pose(70.496, 7.652),
                                new Pose(133.000, 22.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))

                .build();

        Score3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(133.000, 22.000),
                                new Pose(103.000, 102.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(48))

                .build();

        Park = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(103.000, 102.000),
                        new Pose(103.000, 70.000)
                )
        ).setConstantHeadingInterpolation(Math.toRadians(45))

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
        ll = hardwareMap.get(Limelight3A.class, "limelight");

        follower.setPose(new Pose(110, 135, Math.toRadians(90)));
    }
    public enum PathState {
        PRELOAD,
        INTAKE1, SCORE1,
        INTAKE2, SCORE2,
        INTAKE3, SCORE3,
        EMPTY, PARK,
        DONE
    }
    PathState pathState;
    public void statePathUpdate() {
        switch (pathState) {
            case PRELOAD:
                if (pathTimer.getElapsedTimeSeconds() >= .1) {
                    shooter.runShooter();
                } else
                    if (pathTimer.getElapsedTimeSeconds() >= 2.4) {
                        shooter.stopShooter();
                    }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 1.5) {
                    intake.IntakeOn();
                }
                    if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2.5) {
                        intake.IntakeOff();
                        shooter.reverseShooter();
                    }
                        if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 4) {
                        follower.followPath(paths.Intake1, true);
                        setPathState(PathState.INTAKE1);
                }
                    break;
            case INTAKE1:
                if (pathTimer.getElapsedTimeSeconds() >= 1 && pathTimer.getElapsedTimeSeconds() <= 2.8) {
                    intake.IntakeSlow();
                }
                if (pathTimer.getElapsedTimeSeconds() >= 2.8) {
                    follower.followPath(paths.Score1, true);
                    setPathState(PathState.SCORE1);
                }
                break;
            case SCORE1:
                if (pathTimer.getElapsedTimeSeconds() >= .1 && pathTimer.getElapsedTimeSeconds() < .135) {
                    intake.IntakeReverse();
                } else if (pathTimer.getElapsedTimeSeconds() >= .135){
                    intake.IntakeOff();
                }
                if (pathTimer.getElapsedTimeSeconds() >= .25) {
                    shooter.runShooter();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 1.8) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 3.2) {
                    shooter.reverseShooter();
                    follower.followPath(paths.Intake2, true);
                    setPathState(PathState.INTAKE2);
                }
                break;
            case INTAKE2:
                    if (follower.isBusy()) {
                        intake.IntakeOn();
                    }
                if (!follower.isBusy()) {
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
                if (pathTimer.getElapsedTimeSeconds() >= .1 && pathTimer.getElapsedTimeSeconds() < .18) {
                    intake.IntakeReverse();
                } else if (pathTimer.getElapsedTimeSeconds() >= .225 && pathTimer.getElapsedTimeSeconds() < .5) {
                    intake.IntakeOff();
                }
                if (pathTimer.getElapsedTimeSeconds() >= .4 && pathTimer.getElapsedTimeSeconds() < 4.7) {
                    shooter.runShooter();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2.2) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >=2.8) {
                    shooter.reverseShooter();
                    intake.IntakeOff();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 3.1) {
                    follower.followPath(paths.Intake3, true);
                    setPathState(PathState.INTAKE3);
                }
                break;
            case INTAKE3:
                if (follower.isBusy()) {
                    intake.IntakeOn();
                }
                    if (!follower.isBusy()) {
                        follower.followPath(paths.Score3, true);
                        setPathState(PathState.SCORE3);
                    }
                break;
            case SCORE3:
                if (pathTimer.getElapsedTimeSeconds() >= .1 && pathTimer.getElapsedTimeSeconds() < .2) {
                    intake.IntakeReverse();
                } else if (pathTimer.getElapsedTimeSeconds() >= .2) {
                    intake.IntakeOff();
                }
                if (pathTimer.getElapsedTimeSeconds() >= .35) {
                    shooter.runShooter();
                }
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 3.5) {
                    shooter.stopShooter();
                    intake.IntakeOff();
                    follower.followPath(paths.Park, true);
                    setPathState(PathState.PARK);
                }
                break;
            case PARK:
                if (!follower.isBusy()) {
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
        telemetry.addData("Path cycle: ", pathState);

    }
}