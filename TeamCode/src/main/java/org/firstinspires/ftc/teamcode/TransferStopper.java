package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TransferStopper {
    Telemetry telemetry;
    private final Gamepad Driver1;

    public static double open = 5;
    public static double close = 0;

    private Servo stop;

    public TransferStopper(OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        Driver1 = opMode.gamepad1;
        stop = hardwareMap.get(Servo.class, "transferStopper");
        stop.setDirection(Servo.Direction.FORWARD);
    }
    public void teleop() {
        if (Driver1.right_bumper) {
            stop.setPosition(open);
        }
        if (Driver1.left_trigger > 0.1) {
            stop.setPosition(close);
        }
    }
}