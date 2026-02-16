package org.firstinspires.ftc.teamcode.Autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.util.Timer;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Shooter;
import org.firstinspires.ftc.teamcode.Thing;

@Autonomous(name = "Test", group = "Autonomous")
public class TestForPedro extends OpMode {

    private Follower follower;
    private Timer pathTimer, opModeTimer;
    Shooter shooter;
    Thing intake;

    public enum PathState {
        preload,
        scorePosition
    }

    PathState pathState;

    private final Pose startPose = new Pose(120, 84, Math.toRadians(90));
    private final Pose score = new Pose(103, 102, Math.toRadians(45));

    private PathChain preload;

    public void buildPath() {
        shooter.runShooter();
        preload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, score))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(45))
                .build();
        intake.IntakeOn();
    }

    public void statePathUpdate() {
        switch(pathState) {
            case preload:
                follower.followPath(preload, true);
                setPathState(PathState.scorePosition);
                break;
            case scorePosition:
                if (!follower.isBusy()) {
                    intake.IntakeOn();
                  shooter.runShooter();
                }
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathState = PathState.preload;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        shooter = new Shooter(this);
        intake = new Thing(this);

        buildPath();
        follower.setPose(startPose);
    }

    public void start() {
        opModeTimer.resetTimer();
        setPathState(pathState);
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