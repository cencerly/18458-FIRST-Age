package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp   //comment back in later
public class ServoTest extends OpMode {
    Servo servo1;

    @Override
    public void init() {
        servo1 = hardwareMap.get(Servo.class, "test");
        servo1.setDirection(Servo.Direction.REVERSE);
        //servo1.setDirection(Servo.Direction.FORWARD);
        servo1.setPosition(0);
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_down){
            servo1.setPosition(servo1.getPosition()+ 0.001);
        }
        else if(gamepad1.dpad_up){
            servo1.setPosition(servo1.getPosition()- 0.001);
        }
        telemetry.addData("Servo",servo1.getPosition());
        telemetry.update();
    }
}