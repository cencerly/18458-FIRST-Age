package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Commands.DriveCommands;
import org.firstinspires.ftc.teamcode.Commands.BlueRightFar;

import java.util.ArrayList;
import java.util.List;

@Autonomous (name = "AutoAuto", group = "Autonomous" )
public class AutoAuto extends LinearOpMode {
    public BlueRightFar blueRightFar;

    public enum Path{

    }

    @Override
    public void runOpMode() throws InterruptedException {
        final FtcDashboard dash = FtcDashboard.getInstance();
        List<Action> runningActions = new ArrayList<>();

        while (!opModeIsActive() && !isStopRequested()) {

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
