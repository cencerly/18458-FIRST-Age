package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Thing;
import org.firstinspires.ftc.teamcode.DT;



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