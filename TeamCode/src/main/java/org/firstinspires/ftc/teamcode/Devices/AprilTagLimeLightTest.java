package org.firstinspires.ftc.teamcode.Devices;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Subsystems.AThing;

@Autonomous
public class AprilTagLimeLightTest extends OpMode {

    private Limelight3A limelight;
    private IMU imu;
    private AThing thing;

    //  TUNING CONSTANTS
    private static final double kP = 0.056;
    private static final double kD = 0.073;

    private static final double MAX_POWER = 10;
    private static final double TX_DEADBAND = 5; // degrees

//

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        thing = new AThing(this);

        limelight.pipelineSwitch(8); // AprilTag pipeline

        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(orientation));
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        // Update robot heading for Limelight

    }

}
