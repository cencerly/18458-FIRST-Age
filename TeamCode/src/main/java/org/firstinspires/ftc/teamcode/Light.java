/* package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class Light extends OpMode {
    Shooter shooter;
    ElapsedTime timer = new ElapsedTime();

    private Servo light = null;

    private static final double TICKS_PER_REV = 28;
    private static final double TargetRPM = 3000;
    private static final double Tolerance = 100;

    private int lastPosition = 0;

    @Override
    public void init() {
        Shooter shooter = new Shooter(this);
        light = hardwareMap.get(Servo.class, "Light");
        light.setPosition(.279);
        timer.reset();
    }

    @Override
    public void loop() {

    }

    public Light(OpMode opMode) {
        double elapsed = timer.seconds();
        double ticksDelta = currentPosition - lastPosition;

        timer.reset();
        shooter.shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lastPosition = currentPosition;
        timer.reset();
        double rpm = (ticksDelta / TICKS_PER_REV) / elapsed * 60;

        if (Math.abs(rpm - TargetRPM) <= Tolerance) {
            light.setPosition(.500);
        } else {
            light.setPosition(.239);
        }
    }

    public void teleOp() {

    }
}*/
