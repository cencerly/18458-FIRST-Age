package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutoPark_Simple", group = "Auto")
public class Park extends LinearOpMode {

    public static DcMotor leftFront, leftBack, rightFront, rightBack;

    static final double TICKS_PER_REV = 537.6; // GoBilda 435 RPM motors
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double DRIVE_GEAR_REDUCTION = 1.0;

    static final double TICKS_PER_INCH = (TICKS_PER_REV * DRIVE_GEAR_REDUCTION) /
            (Math.PI * WHEEL_DIAMETER_INCHES);

    @Override
    public void runOpMode() throws InterruptedException {

        // --- Hardware Map ---
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack   = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack  = hardwareMap.get(DcMotor.class, "rightRearBack");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        // Reset encoders
        for (DcMotor m : new DcMotor[]{leftFront, rightFront, leftBack, rightBack}) {
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        waitForStart();

        if (opModeIsActive()) {
            // Drive forward 24 inches to park
            driveForward(24, 0.4);
        }
    }

    public void driveForward(double inches, double power) {
        int move = (int)(inches * TICKS_PER_INCH);

        leftFront.setTargetPosition(leftFront.getCurrentPosition() + move);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() + move);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() + move);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() + move);

        for (DcMotor m : new DcMotor[]{leftFront, rightFront, leftBack, rightBack}) {
            m.setPower(power);
        }

        while (opModeIsActive() &&
                (leftFront.isBusy() && rightFront.isBusy() &&
                        leftBack.isBusy() && rightBack.isBusy())) {
            idle();
        }

        for (DcMotor m : new DcMotor[]{leftFront, rightFront, leftBack, rightBack}) {
            m.setPower(0);
        }
    }
}
