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

@Autonomous(name = "BlueFarRight", group = "Autonomous")
public class BlueRightFar extends LinearOpMode {
    Thing shooter;
    Thing intake;
    HardwareMap hardwareMap;

     public BlueRightFar(Thing intake, Thing shooter, HardwareMap hardwareMap) {
         this.intake = intake;
         this.shooter = shooter;
         this.hardwareMap = hardwareMap;
     }

    public void runOpMode() throws InterruptedException {
         Pose2d startPose = new Pose2d(60, 15, Math.toRadians(180));
         Pose2d prePickup = new Pose2d(36, 32, Math.toRadians(90));
         Pose2d pickup = new Pose2d(36, 62, Math.toRadians(90));
         Pose2d preShoot = new Pose2d(20, 20, Math.toRadians(90));
         Pose2d shoot = new Pose2d(-40, 20, Math.toRadians(120));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        telemetry.addLine("Ready to start autonomous");
        telemetry.update();

        final FtcDashboard dash = FtcDashboard.getInstance();
        List<Action> runningActions = new ArrayList<>();

        while (!opModeIsActive() && !isStopRequested()) {

            waitForStart();
            if (isStopRequested()) return;

            if (opModeIsActive()) {
                Actions.runBlocking(
                        drive.actionBuilder(startPose)
                                .strafeTo(prePickup.position)
                                .build());
                intake.IntakeOn();
                sleep(500);
                Actions.runBlocking(
                        drive.actionBuilder(prePickup)
                                .strafeTo(pickup.position)
                                .build());
                sleep(500);
                intake.IntakeOff();
                Actions.runBlocking(
                        drive.actionBuilder(pickup)
                                .strafeTo(preShoot.position)
                                .build());
                Actions.runBlocking(
                        drive.actionBuilder(preShoot)
                                .strafeTo(shoot.position)
                                .build());
                shooter.ShooterOn();


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
}//