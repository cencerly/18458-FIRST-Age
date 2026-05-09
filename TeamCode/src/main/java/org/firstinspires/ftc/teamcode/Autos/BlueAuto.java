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
    public class BlueAuto extends OpMode {

    private Follower follower;
    private Timer pathTimer, opModeTimer;
    Shooter shooter;
    Thing intake;
    TransferStopper stopper;
    private BlueAuto.Paths paths;

    public static class Paths {
        public PathChain PRELOAD;
        public PathChain INTAKE1;
        public PathChain SCORE1;
        public PathChain INTAKE2;
        public PathChain SCORE2;
        public PathChain INTAKE3;
        public PathChain SCORE3;
        public PathChain GATE;

        public Paths(Follower follower) {
            PRELOAD = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.000, 8.000),
                                    new Pose(68.000, 20.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(115))
                    .build();

            INTAKE1 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(68.000, 20.000),
                                    new Pose(57.000, 25.000),
                                    new Pose(7.000, 24.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(267))
                    .build();

            SCORE1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(7.000, 24.000),
                                    new Pose(7.000, 7.000)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            INTAKE2 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(7.000, 7.000),
                                    new Pose(45.000, 9.000),
                                    new Pose(68.000, 20.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(7), Math.toRadians(115))
                    .build();

            SCORE2 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(68.000, 20.000),
                                    new Pose(68.000, 48.000),
                                    new Pose(16.000, 47.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(180))
                    .build();

            INTAKE3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(16.000, 47.000),
                                    new Pose(68.000, 20.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))
                    .build();

            SCORE3 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(68.000, 20.000),
                                    new Pose(68.000, 76.000),
                                    new Pose(16.000, 71.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(180))
                    .build();

            GATE = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(16.000, 71.000),
                                    new Pose(68.000, 29.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))
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
        paths = new BlueAuto.Paths(follower);
        stopper = new TransferStopper(this);

        follower.setPose(new Pose(56,8 , Math.toRadians(90)));
    }

    public enum PathState {
        START,
        SCORE,
        PARK
    }

    BlueAuto.PathState pathState;

    @Override
    public void loop() {

        follower.update();
        //statePathUpdate();

        telemetry.addData("Path State", pathState.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("Path time", pathTimer.getElapsedTimeSeconds());
        telemetry.addData("Path cycle: ", pathState);

    }
}


