package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Thing {
    public static final double FULL_POWER = 1.0;
    public static final double NO_POWER = 0.0;
    public static final double INTAKEPOWER = 1;
    public static final double NINTAKEPOWER =-1;
    public static final double OPEN = 20.0;
    public static final double CLOSE = 0.0;

    public final DcMotor Intake;
    public final DcMotor Turret;
    public final Servo TransferStopper;
    private final Gamepad Driver1;
   // private final Gamepad Driver2;
    private final Telemetry telemetry;

//    private final Servo servoLeft;
//    private final Servo servoRight;

    // Servo position constants
//    private static final double VERTICAL = 1.0;
//    private static final double HORIZONTAL = 0.5;

    public Thing(OpMode opMode) {
        Driver1 = opMode.gamepad1;
       // Driver2 = opMode.gamepad2;
        telemetry = opMode.telemetry;
        HardwareMap hardwareMap = opMode.hardwareMap;

        Intake = hardwareMap.get(DcMotor.class, "spinner");
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Turret = hardwareMap.get(DcMotor.class, "turret");
        Turret.setDirection(DcMotorSimple.Direction.REVERSE);
        Turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        TransferStopper = hardwareMap.get(Servo.class, "transferStopper");

//        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
//        servoRight = hardwareMap.get(Servo.class, "servoRight");
//        servoLeft.setDirection(Servo.Direction.FORWARD);
//        servoRight.setDirection(Servo.Direction.REVERSE);
//        // Default position
//        servoLeft.setPosition(VERTICAL);
//        servoRight.setPosition(VERTICAL);

    }

    public void teleOp() {
        // --- Turret Control ---
        if (Driver1.dpad_left) {
            Turret.setPower(FULL_POWER);
        } else if (Driver1.dpad_right) {
            Turret.setPower(-FULL_POWER);
        } else {
            Turret.setPower(NO_POWER);
        }

        // --- Intake Control ---
        if (Driver1.x) {
            Intake.setPower(INTAKEPOWER);
        } else if (Driver1.y) {
            Intake.setPower(NINTAKEPOWER);
        } else {
            Intake.setPower(NO_POWER);
        }

        if (Driver1.right_stick_button) {
            TransferStopper.setPosition(OPEN);
        }
        else if (Driver1.left_stick_button) {
            TransferStopper.setPosition(CLOSE);
        }


        // --- Hood Control ---
//        if (Driver1.left_trigger > 0.5) {
//            servoLeft.setPosition(VERTICAL);
//            servoRight.setPosition(VERTICAL);
//        } else if (Driver1.right_trigger > 0.5) {
//            servoLeft.setPosition(HORIZONTAL);
//            servoRight.setPosition(HORIZONTAL);
//        }


        // Optional telemetry feedback
        telemetry.addData("Shooter Power", Turret.getPower());
        telemetry.addData("Intake Power", Intake.getPower());
        telemetry.addData("Stopper", TransferStopper.getPosition());
//        telemetry.addData("Servo Left Pos", Hood.getPower());
//        telemetry.addData("Servo Right Pos", Hood.getPower());
        telemetry.update();
    }
    public void IntakeOn() {
        Intake.setPower(-INTAKEPOWER);
    }
    public void IntakeReverse() {
        Intake.setPower(INTAKEPOWER);
    }

    public void IntakeOff() {
        Intake.setPower(NO_POWER);
    }
    public void ShooterOn() {
        Turret.setPower(FULL_POWER);
    }
    public void ShooterOff() {
        Turret.setPower(NO_POWER);
    }
    public void StopperOn(){
        TransferStopper.setPosition(OPEN);
    }
    public void StopperOff() {
        TransferStopper.setPosition(CLOSE);
    }
}
