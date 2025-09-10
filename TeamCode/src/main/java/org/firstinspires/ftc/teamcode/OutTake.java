package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class OutTake {
    public HardwareMap hardwareMap;
    public DcMotor dcMotor;
    public Gamepad gamepad2;

    int SHOOT = 1;

    public OutTake(HardwareMap hardwareMap, Gamepad gamepad2) {
        this.hardwareMap = hardwareMap;
        this.gamepad2 = gamepad2;
        this.dcMotor = (DcMotor) hardwareMap.get("outtake");

        dcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void shoot(int SHOOT) {
        dcMotor.setPower(SHOOT);
    }

    public void tele() {
        if (gamepad2.a) {
            shoot(SHOOT);
        }
            else {
                dcMotor.setPower(0);
        }
    }
}
//choose button for carlos and set direction if not working