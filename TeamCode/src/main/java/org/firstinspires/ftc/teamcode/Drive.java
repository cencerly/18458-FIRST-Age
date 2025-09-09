package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

@Config
public class Drive {
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public HardwareMap hardwaremap;
    public Gamepad gamepad1;
    public IMU imu;

    double y = -gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;
    double rx = gamepad1.right_stick_x;

    double frontRightPower = (y-x-rx);
    double backRightPower = (y-x+rx);
    double frontLeftPower = (y+x+rx);
    double backLeftPower  = (y+x-rx);

    public Drive(HardwareMap hardwareMap, Gamepad gamepad2) {
        this.hardwaremap = hardwareMap;
        this.gamepad1 = gamepad2;
        this.imu = (IMU) hardwaremap.get("imu");
        this.frontLeft = (DcMotor) hardwaremap.get("frontLeft");
        this.backLeft = (DcMotor) hardwaremap.get("backLeft");
        this.frontRight = (DcMotor) hardwaremap.get("frontRight");
        this.backRight = (DcMotor) hardwaremap.get("backRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
    }
}
