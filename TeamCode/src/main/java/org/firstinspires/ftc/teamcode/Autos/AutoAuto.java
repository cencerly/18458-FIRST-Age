package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Commands.DriveCommands;
import org.firstinspires.ftc.teamcode.Commands.GLeft;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

@Autonomous
public class AutoAuto extends LinearOpMode {
    public DriveCommands drive;
    public GLeft gLeft;

    enum Path{

    }

    @Override
    public void runOpMode() throws InterruptedException {
        final FtcDashboard dash = FtcDashboard.getInstance();
        List<Action> runningActions = new ArrayList<>();

        while (!opModeIsActive() && !isStopRequested()) {

            drive = new DriveCommands(this);

            waitForStart();

            if (opModeIsActive()) {

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
