package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TransferStopper {
    Telemetry telemetry;

    public int open = -1;
    public int close = 0;

    private Servo stop;

    public TransferStopper(OpMode opMode){
        HardwareMap hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        stop = hardwareMap.get(Servo.class, "Stopper");

        stop.setDirection(Servo.Direction.REVERSE);

    }



}