package org.firstinspires.ftc.teamcode;

import android.hardware.HardwareBuffer;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
    public class Intake {

        public HardwareMap hardwareMap;
        public DcMotor dcMotor;
        public Gamepad gamepad2;

        public Intake(HardwareMap hardwareMap, Gamepad gamepad2) {
            this.hardwareMap= hardwareMap;
            this.gamepad2 = gamepad2;
            this.dcMotor = (DcMotor) hardwareMap.get("Intake");

        }



}
