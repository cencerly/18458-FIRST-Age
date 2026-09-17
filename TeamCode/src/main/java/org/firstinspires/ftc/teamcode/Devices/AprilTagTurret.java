package org.firstinspires.ftc.teamcode.Devices;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class AprilTagTurret extends OpMode {
    Limelight3A limelight;
    private AprilTag tag = new AprilTag();

    double[] stepSizes = {0.1, 0.01, 0.001, 0.0001, 0.00001};
    int stepIndex = 2;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        tag.init(hardwareMap);
        limelight.pipelineSwitch(1);
    }

    public void start() {
        limelight.start();
        tag.resetTimer();
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();
        llResult.getTx();

        tag.update();

        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if(gamepad1.dpadLeftWasPressed()) {
            tag.setkP(tag.getkP() - stepSizes[stepIndex]);
        }
        if(gamepad1.dpadRightWasPressed()) {
            tag.setkP(tag.getkP() + stepSizes[stepIndex]);
        }

        if(gamepad1.dpadUpWasPressed()) {
            tag.setkD(tag.getkD() + stepSizes[stepIndex]);
        }
        if(gamepad1.dpadDownWasPressed()) {
            tag.setkD(tag.getkD() - stepSizes[stepIndex]);
        }

        telemetry.addData("Tuning P", "%.5f (D-Pad L/R", tag.getkP());
        telemetry.addData("Tuning D", "%.5f (D-Pad U/D", tag.getkD());
        telemetry.addData("Step Size", "%.5f (B Button", stepSizes[stepIndex]);
    }
}
