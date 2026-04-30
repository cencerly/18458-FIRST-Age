package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TransferStopper {
    Telemetry telemetry;
    private final Gamepad Driver1;

    public static double down= .5;
    public static double up = 1;

    private Servo left;
    private Servo right;

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



    boolean stopper = false;
    boolean lastA = false;

    public void teleop() {
        if (Driver1.b) {

        }
    }
}