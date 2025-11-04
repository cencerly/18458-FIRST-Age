package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Thing {
    public static final double FULL_POWER = 1.0;
    public static final double NO_POWER = 0.0;

    public final DcMotor Intake;
    public final DcMotor Shooter;
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

        Shooter = hardwareMap.get(DcMotor.class, "shooter");
        Shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        Shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
//        servoRight = hardwareMap.get(Servo.class, "servoRight");
//        servoLeft.setDirection(Servo.Direction.FORWARD);
//        servoRight.setDirection(Servo.Direction.REVERSE);
//        // Default position
//        servoLeft.setPosition(VERTICAL);
//        servoRight.setPosition(VERTICAL);

    }

    public void teleOp() {
        // --- Shooter Control ---
        if (Driver1.left_bumper) {
            Shooter.setPower(FULL_POWER);
        } else if (Driver1.right_bumper) {
            Shooter.setPower(-FULL_POWER);
        } else {
            Shooter.setPower(NO_POWER);
        }

        // --- Intake Control ---
        if (Driver1.x) {
            Intake.setPower(0.5);
        } else if (Driver1.y) {
            Intake.setPower(-0.5);
        } else {
            Intake.setPower(NO_POWER);
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
        telemetry.addData("Shooter Power", Shooter.getPower());
        telemetry.addData("Intake Power", Intake.getPower());
//        telemetry.addData("Servo Left Pos", Hood.getPower());
//        telemetry.addData("Servo Right Pos", Hood.getPower());
        telemetry.update();
    }
    public void Intake() {
        Intake.setPower(1);
    }
    public void Shooter() {
        Shooter.setPower(1);
    }
}
