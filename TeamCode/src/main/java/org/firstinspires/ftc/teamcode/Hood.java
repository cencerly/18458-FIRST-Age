package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Hood Control", group = "TeleOp")
public class Hood extends OpMode {

    private Servo servoLeft;
    private Servo servoRight;

    // Servo position constants
    private static final double VERTICAL = 1.0;
    private static final double HORIZONTAL = 0.5;

    public Hood(TeleOop teleOop) {
    }

    public static Servo getPower() {
        return null;
    }


    @Override
    public void init() {
        // Initialize hardware
        servoLeft = hardwareMap.get(Servo.class, "servoLeft");
        servoRight = hardwareMap.get(Servo.class, "servoRight");

        // Set servo directions
        servoLeft.setDirection(Servo.Direction.FORWARD);
        servoRight.setDirection(Servo.Direction.REVERSE);

        // Default position
        servoLeft.setPosition(VERTICAL);
        servoRight.setPosition(VERTICAL);

        telemetry.addLine("Hood initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Example of using triggers with thresholds
        if (gamepad1.left_trigger > 0.5) {
            // Move to horizontal
            servoLeft.setPosition(HORIZONTAL);
            servoRight.setPosition(HORIZONTAL);
            telemetry.addLine("Hood: Horizontal");
        }
        else if (gamepad1.right_trigger > 0.5) {
            // Move to vertical
            servoLeft.setPosition(VERTICAL);
            servoRight.setPosition(VERTICAL);
            telemetry.addLine("Hood: Vertical");
        }

        else {
            // Maintain last position or do nothing
            telemetry.addLine("Hood: Idle");
        }

        telemetry.update();
    }

    public void teleop() {
    }
}

