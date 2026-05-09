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
@Autonomous(name = "RedAuto", group = "Autonomous")
public class RedAuto extends OpMode {


    private Follower follower;
    private Timer pathTimer, opModeTimer;
    Shooter shooter;
    Thing intake;
    TransferStopper stopper;
    private Paths paths;

    public static class Paths {
        public PathChain Preload;
        public PathChain Intake4;
        public PathChain Score3;
        public PathChain Intake2A;
        public PathChain Intake2B;
        public PathChain Empty;
        public PathChain Score2;
        public PathChain Intake1A;
        public PathChain Intake1B;
        public PathChain Score1;
        public PathChain GateIntake;
        public PathChain Score4;
        public PathChain Park;


        public Paths(Follower follower) {
            Preload = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(109.112, 135.448),
                                    new Pose(103.000, 102.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(46))
                    .build();

            Intake1A = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(103.000, 102.000),
                                    new Pose(80, 22),
                                    new Pose(103.000, 20.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(46), Math.toRadians(0))
                    .build();

            Intake1B = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(103.000, 20.000),

                                    new Pose(131.70, 20.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Score1 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(131.700, 20.000),
                                    new Pose(81, 22),
                                    new Pose(103.500, 102.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(46))

                    .build();

            Intake2A = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(103.500, 102.000),
                                    new Pose(72.822, 46.439),
                                    new Pose(102.000, 54.400)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(46), Math.toRadians(0))
                    .build();

            Intake2B = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(102.000, 54.400),

                                    new Pose(131.500, 52.400)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();
            Empty = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(131.500, 52.400),
                                    new Pose(90.000, 66.000),
                                    new Pose(131.500, 66.000)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(0))

                    .build();

            Score2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(134.500, 66.000),
                                    new Pose(80, 70),
                                    new Pose(103.000, 102.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(46))

                    .build();

            Intake4 = follower.pathBuilder().addPath(
                            new BezierCurve(

                                    new Pose(103.000, 102.000),
                                    new Pose(96.000, 78.000),
                                    new Pose(96.000, 83.000),
                                    new Pose(131.000, 86.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(46), Math.toRadians(0))

                    .build();

            Score4 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(131.000, 86.000),
                                    new Pose(103.000, 102.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(46))

                    .build();


            Park = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(103.000, 102.000),
                                    new Pose(103.000, 70.000)
                            )
                    ).setConstantHeadingInterpolation(Math.toRadians(46))

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
        INTAKE4, SCORE1,
        INTAKE1A, INTAKE1B,
        SCORE2,
        INTAKE2A, INTAKE2B,
        EMPTY, PARK,
         SCORE4,
        DONE
    }
    PathState pathState;
    public void statePathUpdate() {
        switch (pathState) {
            case PRELOAD:
                if (pathTimer.getElapsedTimeSeconds() >= .55) {
                    shooter.runShooter();
                } else if
                    (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2.8) {
                        shooter.stopShooter();
                    }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 1.7) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 3.2) {
                    shooter.reverseShooter();
                    intake.IntakeOff();
                    follower.followPath(paths.Intake1A, true);
                    setPathState(PathState.INTAKE1A);
                }
                break;
            case INTAKE1A:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                    follower.followPath(paths.Intake1B, true);
                    setPathState(PathState.INTAKE1B);
                }
                break;
            case INTAKE1B:
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    follower.followPath(paths.Score1, true);
                    setPathState(PathState.SCORE1);
                }
                break;
            case SCORE1:
                if (follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= .76){
                    shooter.runShooter();
                }

                if (!follower.isBusy()) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 5.4) {
                    shooter.reverseShooter();
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
                if (pathTimer.getElapsedTimeSeconds() >= .6) {
                    shooter.runShooter();
                }
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 4.7) {
                    shooter.reverseShooter();
                    intake.IntakeOff();
                    follower.followPath(paths.Intake4, true);
                    setPathState(PathState.INTAKE4);
                }
                break;
            case INTAKE4:
                if (pathTimer.getElapsedTimeSeconds() >= .1) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy()) {
                    follower.followPath(paths.Score4, true);
                    setPathState(PathState.SCORE4);
                }
                break;
            case SCORE4:
                if (pathTimer.getElapsedTimeSeconds() >=.1){
                    shooter.runShooter();
                }
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2.8) {
                    follower.followPath(paths.Park, true);
                    setPathState(PathState.PARK);
                }
                break;
            case PARK:
                if (!follower.isBusy()) {
                    setPathState(PathState.DONE);
                }
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    public void start() {
        follower.setPose(new Pose(110, 135, Math.toRadians(90)));
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