package org.firstinspires.ftc.teamcode.Devices;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class AprilTag {
    DcMotorEx turret;
    Limelight3A limelight;
    double kP = 0.001;
    double kD = 0.001;
    double goalX = 0;
    double lastError = 0;
    double angleTolerance = 0;
    private final double MAX_POWER = 0.0;
    private double power = 0;
    private final ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hardwareMap) {
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
    }

    public void setkP(double newkP) {
        kP = newkP;
    }

    public double getkP() {
        return kP;
    }

    public void setkD(double newkD) {
        kP = newkD;
    }

    public double getkD() {
        return kD;
    }

    public void resetTimer() {
        timer.reset();
    }
    public void update() {
        LLResult llResult = limelight.getLatestResult();
        double deltaTime = timer.seconds();
        timer.reset();

        if (llResult == null) {
             turret.setPower(0);
             lastError = 0;
        }

        assert llResult != null;
        double error = goalX - llResult.getTx();
        double pTerm = error * kP;

        double dTerm = 0;
        if(deltaTime > 0) {
            dTerm = ((error - lastError) / deltaTime) * kD;
        }

        if(Math.abs(error) < angleTolerance) {
            power = 0;
        } else {
            power = Range.clip(pTerm + dTerm, -MAX_POWER, MAX_POWER);
        }

        turret.setPower(power);
        lastError = error;

    }
}
