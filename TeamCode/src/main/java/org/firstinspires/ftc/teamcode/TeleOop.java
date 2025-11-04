package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TeleOop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DT dt= new DT(this);
        Thing thing = new Thing(this);

        waitForStart();
        while (opModeIsActive()) {
            dt.teleop();
            thing.teleOp();
        }
    }
}