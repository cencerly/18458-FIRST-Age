package org.firstinspires.ftc.teamcode;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(10.1604691) // Robot mass in kg
            .forwardZeroPowerAcceleration(-25.9) // Tune this
            .lateralZeroPowerAcceleration(-67.3) // Tune this
            .translationalPIDFCoefficients(new PIDFCoefficients(
                    0.03, // P
                    0,    // I
                    0,    // D
                    0.015 // F
            ))
            .translationalPIDFSwitch(4)
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(
                    0.4,
                    0,
                    0.005,
                    0.0006
            ))
            .headingPIDFCoefficients(new PIDFCoefficients(
                    0.8,  // P
                    0,    // I
                    0,    // D
                    0.01  // F
            ))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(
                    2.5,
                    0,
                    0.1,
                    0.0005
            ))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(
                    0.1,
                    0,
                    0.00035,
                    0.6,
                    0.015
            ))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(
                    0.02,
                    0,
                    0.000005,
                    0.6,
                    0.01
            ))
            .drivePIDFSwitch(15)
            .centripetalScaling(0.0005);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .leftFrontMotorName("leftFront")   // Update these names
            .leftRearMotorName("leftBack")
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(78.26) // Tune these from the tuner
            .yVelocity(61.49);


    public static TwoWheelConstants localizerConstants = new TwoWheelConstants()
            .forwardEncoder_HardwareMapName("frontLeft")
            .strafeEncoder_HardwareMapName("backLeft")
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.LEFT
            ))
            .forwardPodY(0)     // Distance forward from center (inches)
            .strafePodX(0)      // Distance left from center (inches)
            .forwardTicksToInches(0.001)  // Will calibrate with tuner
            .strafeTicksToInches(0.001);  // Will calibrate with tuner
    // Add .forwardEncoderDirection(Encoder.REVERSE) if needed
    // Add .strafeEncoderDirection(Encoder.REVERSE) if needed

    public static PathConstraints pathConstraints = new PathConstraints(
            0.995,  // tValue constraint
            0.1,    // velocity constraint
            0.1,    // translational constraint
            0.009,  // heading constraint
            50,     // timeout (seconds)
            1.25,   // braking strength
            10,     // bezier curve search limit (leave at 10)
            1       // braking start
    );

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .mecanumDrivetrain(driveConstants)
                .twoWheelLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .build();
    }
}