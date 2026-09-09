package org.firstinspires.ftc.teamcode.Devices;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.TeleOp.DT;

@TeleOp
public class LimeLightDrive extends OpMode {
    DcMotor leftFront, leftBack, rightFront, rightBack;
    Limelight3A limelight;
    DT dt;
    IMU imu;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        dt = new DT(this);
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

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

       double tx = llResult.getTx();
       double kp = .07;

       double turnSpeed = tx * -kp;

        if (llResult.isValid() && llResult.getTx() >= 15) {
            leftFront.setPower(-turnSpeed);
            leftBack.setPower(-turnSpeed);
            rightBack.setPower(turnSpeed);
            rightFront.setPower(turnSpeed);
        } else if (llResult.isValid() && llResult.getTx() <= -15) {
            leftFront.setPower(turnSpeed);
            leftBack.setPower(turnSpeed);
            rightBack.setPower(-turnSpeed);
            rightFront.setPower(-turnSpeed);
        } else if (Math.abs(tx) <= 15 || (Math.abs(tx) >= -15)) turnSpeed = 0; {
            leftFront.setPower(turnSpeed);
            leftBack.setPower(turnSpeed);
            rightBack.setPower(turnSpeed);
            rightFront.setPower(turnSpeed);
        }
    }
}
