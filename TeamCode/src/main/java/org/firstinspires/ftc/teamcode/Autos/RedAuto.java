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
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(45))

                    .build();

            Intake1A = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(103.000, 102.000),
                                    new Pose(85, 22),
                                    new Pose(100.000, 20.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                    .build();

            Intake1B = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(100.000, 20.000),

                                    new Pose(141.000, 20.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();

            Score1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(137.000, 20.000),
                                    new Pose(103.000, 102.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(48))

                    .build();

            Intake2A = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(103.000, 102.000),
                                    new Pose(72.822, 46.439),
                                    new Pose(102.000, 53.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                    .build();

            Intake2B = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(102.000, 53.000),

                                    new Pose(144.000, 53.000)
                            )
                    ).setTangentHeadingInterpolation()

                    .build();
            Empty = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(144.000, 53.000),
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

                GateIntake = follower.pathBuilder().addPath(
                                new BezierCurve(
                                        new Pose(103.000, 102.000),
                                        new Pose(98.990, 59.312),
                                        new Pose(136.000, 60.000)
                                )
                        ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(45))

                        .build();

                Score3 = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(136.000, 60.000),
                                new Pose(103, 102)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(45))
                        .build();


            Intake4 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(103.000, 102.000),
                                    new Pose(96.000, 78.000),
                                    new Pose(96.000, 83.000),
                                    new Pose(133.000, 82.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                    .build();

            Score4 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(133.000, 82.000),

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
        stopper = new TransferStopper(this);

        follower.setPose(new Pose(110, 135, Math.toRadians(90)));
    }
    public enum PathState {
        PRELOAD,
        INTAKE4, SCORE1,
        INTAKE1A, INTAKE1B,
        SCORE2,
        INTAKE2A, INTAKE2B,
        SCORE3,
        EMPTY, PARK,
        GATEINTAKE, SCORE4,
        DONE
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
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2.3) {
                    intake.IntakeOff();
                    follower.followPath(paths.Intake1A, true);
                    setPathState(PathState.INTAKE1A);
                }
                break;
            case INTAKE1A:
                if (!follower.isBusy()) {
                    follower.followPath(paths.Intake1B, true);
                    setPathState(PathState.INTAKE1B);
                }
                break;
            case INTAKE1B:
                if (follower.isBusy()) {
                    stopper.DOWN();
                    intake.IntakeOn();
                }
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    stopper.UP();
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
                    stopper.UP();
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
                    follower.followPath(paths.GateIntake, true);
                    setPathState(PathState.GATEINTAKE);
                }
                break;
            case GATEINTAKE:
                if (follower.isBusy() && pathTimer.getElapsedTimeSeconds() <= 0.1) {
                    stopper.DOWN();
                    intake.IntakeOn();
            }
                if (!follower.isBusy()) {
                    intake.IntakeOff();
                    stopper.UP();
                    follower.followPath(paths.Score3, true);
                    setPathState(PathState.SCORE3);
                }
                break;
            case SCORE3:
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() >= 2) {
                    intake.IntakeOff();
                }
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                    follower.followPath(paths.Intake4, true);
                    setPathState(PathState.INTAKE4);
            }
                break;
            case INTAKE4:
                if (pathTimer.getElapsedTimeSeconds() >= 3.5) {
                    follower.followPath(paths.Score4, true);
                    setPathState(PathState.SCORE4);
                }
                break;
            case SCORE4:
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