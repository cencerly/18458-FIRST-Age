package org.firstinspires.ftc.teamcode.PedroPath;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import dev.nextftc.core.commands.Command;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

public abstract class PedroTele extends OpMode {

    DcMotorEx frontLeft, frontRight, backLeft, backRight;

    public MotorEx[] motors;

    public Command driverControlled;

    @Override
    public void init() {
        frontLeft = hardwareMap.get(DcMotorEx.class, ("frontLeft"));
        frontRight = hardwareMap.get(DcMotorEx.class, ("frontRight"));
        backLeft = hardwareMap.get(DcMotorEx.class, ("backLeft"));
        backRight = hardwareMap.get(DcMotorEx.class, ("backRight"));
        }
    }