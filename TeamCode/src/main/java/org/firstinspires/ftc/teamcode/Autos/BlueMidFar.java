package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Thing;

import java.util.ArrayList;
import java.util.List;

@Autonomous (name = "BlueMidFar", group = "Autonomous")
public class BlueMidFar extends LinearOpMode {
    Thing shooter;
    Thing intake;
    HardwareMap hardwareMap;

    public BlueMidFar (Thing shooter, Thing intake, HardwareMap hardwareMap) {
        this.shooter = shooter;
        this.intake = intake;
        this.hardwareMap = hardwareMap;
    }

    public void runOpMode() throws InterruptedException{
        Pose2d startPose = new Pose2d(60, 15, Math.toRadians(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        telemetry.addLine("Ready to start autonomous");
        telemetry.update();

        final FtcDashboard dash = FtcDashboard.getInstance();
        List<Action> runningActions = new ArrayList<>();

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            Actions.runBlocking(
                    drive.actionBuilder(startPose)
                          //  .strafeTo(prePickup.position)
                            .build());

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
