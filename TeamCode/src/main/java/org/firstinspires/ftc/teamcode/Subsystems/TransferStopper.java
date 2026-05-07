package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TransferStopper {
    Telemetry telemetry;
    private final Gamepad Driver1;

    public static double down= .9;
    public static double up = 1;

    public Servo left;
    public Servo right;

    public TransferStopper(OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        Driver1 = opMode.gamepad1;
        left = hardwareMap.get(Servo.class, "left");
        right = hardwareMap.get(Servo.class, "right");
        left.setDirection(Servo.Direction.REVERSE);
    }

    public void init() {
        right.setPosition(up);
        left.setPosition((up));
    }

    public void UP() {
        right.setPosition(up);
        left.setPosition(up);
    }

    public void DOWN() {
        right.setPosition(down);
        left.setPosition(down);
    }


    boolean stopper = false;


    public void teleop() {
        if (Driver1.right_trigger > .3) {
            stopper = !stopper;
            if (!stopper) DOWN();
            }
        else if (stopper) {
            UP();
        }
    }
}