package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class PracticeSlides {
    public HardwareMap hardwareMap;
    public DcMotor leftSlide, rightSlide;
    public Gamepad gamepad1;

    int HIGH = 100;
    int MID = 50;
    int LOW = 25;
    int RESET = 0;

    public PracticeSlides(HardwareMap hardwareMap, Gamepad gamepad1) {
        this.hardwareMap = hardwareMap;
        this.gamepad1 = gamepad1;
        this.leftSlide = (DcMotor) hardwareMap.get("leftslide");
        this.rightSlide = (DcMotor) hardwareMap.get("rightslide");

        leftSlide.setDirection(DcMotor.Direction.REVERSE);
        rightSlide.setDirection(DcMotor.Direction.REVERSE);

        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void slidesHigh(int HIGH){
        leftSlide.setTargetPosition(HIGH);
        rightSlide.setTargetPosition(HIGH);
    }
    public void slidesMid(int MID){
        leftSlide.setTargetPosition(MID);
        rightSlide.setTargetPosition(MID);
    }
    public void slidesLow(int LOW){
        leftSlide.setTargetPosition(LOW);
        rightSlide.setTargetPosition(LOW);
    }
    public void slidesReset(int RESET){
        leftSlide.setTargetPosition(RESET);
        rightSlide.setTargetPosition(RESET);
    }

    public void tele() {
        if (gamepad1.dpad_up) {
            slidesHigh(HIGH);
        } else if (gamepad1.dpad_down) {
            slidesLow(LOW);
        } else if (gamepad1.dpad_left) {
            slidesMid(MID);
            } else if (gamepad1.dpad_right) {
            slidesReset(RESET);
        }
    }
}
// jkvjhv