package org.firstinspires.ftc.teamcode;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .forwardZeroPowerAcceleration(-60)
            .lateralZeroPowerAcceleration(-60)
            .translationalPIDFCoefficients(new PIDFCoefficients(9, 0, .5, 0))
            .headingPIDFCoefficients(new PIDFCoefficients(6, 0, .5, 0))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.05,0.0,0,0.6,0.0))//change
            .centripetalScaling(0.0001)//change
            .mass(5);//change

    public static MecanumConstants driveConstants = new MecanumConstants()
            .xVelocity(60)
            .yVelocity(60)
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftRearMotorName("leftBack")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .twoWheelLocalizer(localizerConstants)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
    public static TwoWheelConstants localizerConstants = new TwoWheelConstants()
            .forwardTicksToInches(0.000523485)
            .strafeTicksToInches(0.000363458288431157)
            .forwardEncoderDirection(Encoder.REVERSE)
            .forwardEncoder_HardwareMapName("leftFront")
            .strafeEncoder_HardwareMapName("rightBack")
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                            RevHubOrientationOnRobot.UsbFacingDirection.UP
                    )
            );


}