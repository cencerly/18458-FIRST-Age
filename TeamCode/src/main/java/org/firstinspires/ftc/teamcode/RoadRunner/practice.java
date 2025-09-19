package org.firstinspires.ftc.teamcode.RoadRunner;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
    public class practice {
        public HardwareMap hardwareMap;
        public DcMotor leftMotor, rightMotor;
        public Gamepad gamepad2;

        int HIGH = 100;
        int MID = 50;
        int LOW = 0;


        public practice(HardwareMap hardwareMap, Gamepad gamepad2) {
            this.hardwareMap = hardwareMap;
            this.gamepad2 = gamepad2;
            this.leftMotor = (DcMotor) hardwareMap.get("leftMotor");
            this.rightMotor = (DcMotor) hardwareMap.get("rightMotor");
        }
}
