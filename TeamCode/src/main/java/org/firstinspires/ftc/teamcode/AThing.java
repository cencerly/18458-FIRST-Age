package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AThing {

    public static final double FULL_POWER = 1.0;
    public static final double NO_POWER = 0.0;
    public static final double INTAKEPOWER = 1.0;

    public final DcMotor Intake;
    public final DcMotor Turret;

    public AThing(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;

        Intake = hardwareMap.get(DcMotor.class, "spinner");
        Intake.setDirection(DcMotorSimple.Direction.FORWARD);
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Turret = hardwareMap.get(DcMotor.class, "turret");
        Turret.setDirection(DcMotorSimple.Direction.REVERSE);
        Turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // ---------- Intake ----------
    public void intakeOn() {
        Intake.setPower(INTAKEPOWER);
    }

    public void intakeReverse() {
        Intake.setPower(-INTAKEPOWER);
    }

    public void intakeOff() {
        Intake.setPower(NO_POWER);
    }

    // ---------- Turret ----------
    public void setTurretPower(double power) {
        Turret.setPower(power);
    }

    public void stopTurret() {
        Turret.setPower(NO_POWER);
    }
}
//