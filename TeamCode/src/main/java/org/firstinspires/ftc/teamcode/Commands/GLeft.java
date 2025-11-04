package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.Thing;

public class GLeft {
    Thing shooter;
    Thing intake;
    HardwareMap hardwareMap;

     public GLeft (Thing intake, Thing shooter, HardwareMap hardwareMap, Pose2d start) {
         this.intake = intake;
         this.shooter = shooter;
         this.hardwareMap = hardwareMap;

         MecanumDrive drive = new MecanumDrive(hardwareMap, start);
     }

     public void run(Pose2d pose2d) {

     }

}
