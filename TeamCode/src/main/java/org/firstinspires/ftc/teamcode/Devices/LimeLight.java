package org.firstinspires.ftc.teamcode.Devices;

import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Subsystems.Thing;

@TeleOp
public class LimeLight extends OpMode {
    Limelight3A limelight;
    Thing thing;
    IMU imu;
    Timer timer;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        thing = new Thing(this);
        timer = new Timer();

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    public void start () {
        limelight.start();
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            timer.resetTimer();
        } else if (llResult == null) {
            timer.getElapsedTime();
        }
        if (timer.getElapsedTimeSeconds() <= .35) {
            thing.IntakeOn();
        } else {
            thing.IntakeOff();
        }
    }
}
