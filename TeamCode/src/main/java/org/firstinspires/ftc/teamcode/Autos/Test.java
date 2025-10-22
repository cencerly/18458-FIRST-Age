package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.DriveCommands;
import org.firstinspires.ftc.teamcode.OutTake;

import java.util.ArrayList;
import java.util.List;

@Autonomous
public class Test extends LinearOpMode {
    public DriveCommands drive;
    public OutTake outtake;

    enum Path {
        PARK
    }

    public static Vector2d PARK = new Vector2d(45, -15);

    public static Pose2d StartPose = new Pose2d(60, -15, Math.toRadians(180));

    Path path = Path.PARK;

    @Override
    public void runOpMode() throws InterruptedException {

        final FtcDashboard dash = FtcDashboard.getInstance();
        List<Action> runningActions = new ArrayList<>();

        while (!opModeIsActive() && !isStopRequested()) {

            waitForStart();

            if (opModeIsActive()) {
                drive.StrafeToConstantHeading(PARK, StartPose);
            }
            List<Action> newActions = new ArrayList<>();
            for (Action action : runningActions) {
                TelemetryPacket packet = new TelemetryPacket();
                action.preview(packet.fieldOverlay());
                if (!action.run(packet)) {
                    continue;
                }
                newActions.add(action);
                dash.sendTelemetryPacket(packet);
            }
            runningActions = newActions;
        }
    }
}
