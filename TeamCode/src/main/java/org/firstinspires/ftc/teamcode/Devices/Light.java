 package org.firstinspires.ftc.teamcode.Devices;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;


public class Light {
    Shooter shooter;
    Telemetry telemetry;

    private final Servo light;


    public Light(OpMode opMode, Shooter shooter) {
        this.shooter = shooter;
        telemetry = opMode.telemetry;
        light = opMode.hardwareMap.get(Servo.class, "Light");
    }

    public void init() {
        light.setPosition(.279);
    }


     public void teleop() {
        if (shooter.currentRPM >= 2500) {
            light.setPosition(.5);
        } else {
            light.setPosition(.279);
        }
        }
    }
