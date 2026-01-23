package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingAuto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        Pose2d beginPose = new Pose2d(63, -40, Math.toRadians(180));
        Pose2d Score = new Pose2d(28, -26, Math.toRadians(135));
        Pose2d Pos1 = new Pose2d(-36, -28, Math.toRadians(90));
        Pose2d Pos2 = new Pose2d(-36, -44, Math.toRadians(90));
        Pose2d Pos3 = new Pose2d(-14, -28, Math.toRadians(90));
        Pose2d Pos4 = new Pose2d(-14, -44, Math.toRadians(90));
        Pose2d Pos5 = new Pose2d(11, -29, Math.toRadians(90));
        Pose2d Pos6 = new Pose2d(11, -45, Math.toRadians(90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setStartPose(beginPose)
                .build();

        myBot.runAction(
                myBot.getDrive().actionBuilder(beginPose)
                        // Score 1 - from start position
                        .setTangent(Math.toRadians(350))
                        .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(315))

                        // Pair 3: Pos5 -> Pos6
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-11, 29, Math.toRadians(90)), Math.toRadians(0))
                        .strafeTo(new Vector2d(-11, 45))

                        // Back to Score
                        .setTangent(Math.toRadians(225))
                        .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(225))

                        // Pair 2: Pos3 -> Pos4
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(14, 28, Math.toRadians(90)), Math.toRadians(0))
                        .strafeTo(new Vector2d(14, 44))
                        .strafeTo(new Vector2d(4, 50))
                        .strafeTo(new Vector2d(4, 60))

                        // Back to Score
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(180))

                        // Pair 1: Pos1 -> Pos2
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(36, 28, Math.toRadians(90)), Math.toRadians(0))
                        .strafeTo(new Vector2d(36, 44))

                        // Final Score
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(180))

                        .build()
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}