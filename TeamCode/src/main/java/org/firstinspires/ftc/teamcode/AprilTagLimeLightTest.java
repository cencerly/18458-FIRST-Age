package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;
import static org.firstinspires.ftc.teamcode.Shooter.kP;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

    @Autonomous
    public class AprilTagLimeLightTest extends OpMode {
    private Limelight3A limelight;
    private IMU imu;
    HardwareMap hardwareMap;

    static final double TICKS_PER_DEGREE = 10.0;
    static final double TX_DEADBAND = 0.5;
    int turretTarget = 0;

        public Thing thing;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        this.thing = new Thing(opMode);
        limelight.pipelineSwitch(8); //24(Blue)
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                com.qualcomm.hardware.rev.RevHubOrientationOnRobot.UsbFacingDirection.UP);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    @Override
    public void start() {
        limelight.start();

        //If there is delay on the limelight starting up then we can just run this in the init statement,
    }


    @Override
    public void loop() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());
        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {

            double tx = llResult.getTx();

            if (Math.abs(tx) > TX_DEADBAND) {
                turretTarget += (int) (tx * TICKS_PER_DEGREE);
            }

            thing.Turret.setTargetPosition(turretTarget);
            thing.Turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addData("Turret Pos", thing.Turret.getCurrentPosition());
            telemetry.addData("Target Pos", turretTarget);
            telemetry.addData("Tx", tx);
        }
    }}

