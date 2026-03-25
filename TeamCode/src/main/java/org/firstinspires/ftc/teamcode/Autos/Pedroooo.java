package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Constants;

@Config
@Autonomous(name = "Pedro", group = "Autonomous")
public abstract class Pedroooo extends OpMode{
    private Follower follower;
    private Timer pathTimer, opModeTimer;
    private TestForPedro.Paths paths;



    public static class Paths {
        public PathChain Pos1;
        public PathChain Pos2;

    public Paths(Follower follower) {
        Pos1 = follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(110.112, 110.448),

                        new Pose(110.000, 130.000)
                )
        ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))

                .build();
    }}


    public enum PathState {
        POSS1,
        POS2
    }
    PathState pathState;
    public void statePathUpdate() {
        switch (pathState) {
            case POSS1:
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() < 0.1) {
                    setPathState(PathState.POSS1);
                }
                break;
        }
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        paths = new TestForPedro.Paths(follower);

        follower.setPose(new Pose(110, 110, Math.toRadians(90)));
    }
    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    public void start() {
        opModeTimer.resetTimer();
        follower.followPath(paths.Preload, true);
        setPathState(PathState.POSS1);

    }
    @Override
    public void loop() {
        follower.update();
        statePathUpdate();
    }
}
