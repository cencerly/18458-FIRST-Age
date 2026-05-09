package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hood{

    private final Gamepad Driver1;
    Shooter shooter;

    final private Servo servoLeft;
    final private Servo servoRight;

    Telemetry telemetry;

    // Servo position constants
    public static double VERTICAL = 0;
    public static double HORIZONTAL = .7;
    public Hood(OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;
        Driver1 = opMode.gamepad1;
        telemetry = opMode.telemetry;
        shooter = new Shooter(opMode);

        servoRight = hardwareMap.get(Servo.class, "hoodRight");
        servoLeft = hardwareMap.get(Servo.class, "hoodLeft");

        servoRight.setDirection(Servo.Direction.REVERSE);
    }
    boolean HoodUp;
    boolean leftTriggerPrevPressed;

    public void teleop() {
        if (Driver1.left_trigger > 0.5 && !leftTriggerPrevPressed) {
            HoodUp = !HoodUp;

            if (HoodUp) {
                servoLeft.setPosition(HORIZONTAL);
                servoRight.setPosition(HORIZONTAL);
            } else {
                servoLeft.setPosition(VERTICAL);
                servoRight.setPosition(VERTICAL);
            }
        }

        if (Driver1.left_bumper) {
            if (HoodUp) {
                shooter.runFarShooter();
            } else {
                shooter.runShooter();
            }
        } else {
            shooter.stopShooter();
        }
        leftTriggerPrevPressed = Driver1.left_trigger > 0.5;
        telemetry.addData("IS HOOD UP?", HoodUp);
    }
}
