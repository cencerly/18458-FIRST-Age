package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Commands.DriveCommands;

import java.util.ArrayList;
import java.util.List;

@Autonomous
public class Parks extends LinearOpMode {

    public DriveCommands drive;
}

enum Path{
    PARK
}

public static Vector2d PARK = new Vector2d(35, 10);

public static Pose2d StartPose = new Pose2d(62, 10, Math.toRadians(-180));

@Override
public void runOpMode() throws InterruptedException {

    final FtcDashboard dash = FtcDashboard.getInstance();
    List<Action> runningAction = new ArrayList<>();

    switch (path) {
        case PARK:

    }
}