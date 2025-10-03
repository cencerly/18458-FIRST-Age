package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

    @Config
    public class TeleopDT extends LinearOpMode {

        public DcMotor frontLeft, backLeft, frontRight, backRight;
        public OutTake outTake;

        @Override
        public void runOpMode() throws InterruptedException {
            this.outTake = new OutTake(hardwareMap, gamepad2);

            this.frontLeft = hardwareMap.dcMotor.get("frontLeft");
            this.backLeft = hardwareMap.dcMotor.get("backLeft");
            this.frontRight = hardwareMap.dcMotor.get("frontRight");
            this.backRight = hardwareMap.dcMotor.get("backRight");

            frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);

            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            IMU imu = hardwareMap.get(IMU.class, "imu");
            // Adjust the orientation parameters to match your robot
            IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
            imu.initialize(parameters);

            telemetry.addLine("IMU and Motors Initialized. Ready to start.");
            telemetry.update();

            waitForStart();

            if (isStopRequested()) return;

            while (opModeIsActive()) {
                outTake.tele();
                double y = -gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double rx = gamepad1.right_stick_x;
                boolean slowmode = gamepad1.right_bumper;
                double slowMode;

                if (gamepad1.options) {
                    imu.resetYaw();
                }
                if (slowmode) {
                    slowMode = 0.3;
                } else slowMode = 1;

                double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

                double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
                double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

                rotX = rotX * 1.1;

                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;

                frontLeft.setPower(frontLeftPower * slowMode);
                backLeft.setPower(backLeftPower * slowMode);
                frontRight.setPower(frontRightPower * slowMode);
                backRight.setPower(backRightPower * slowMode);
            }

        }
    }