package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RedTwo {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)

                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(13.41339, 14.33071)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-63, 40, Math.toRadians(180)))

                        .setTangent(320)
                .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(335))

                .setTangent(320)
                .splineToLinearHeading(new Pose2d(4, 31, Math.toRadians(90)), Math.toRadians(30))
                .splineToLinearHeading(new Pose2d(7, 48, Math.toRadians(90)), Math.toRadians(90))

                        .setTangent(225)
                .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(160))

                .setTangent(0)
                .splineToLinearHeading(new Pose2d(4, 53, Math.toRadians(90)), Math.toRadians(110))
                        .splineToLinearHeading(new Pose2d(10, 59, Math.toRadians(115)), Math.toRadians(90))
                        .strafeTo(new Vector2d (12, 60))

                .setTangent(225)
                .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(180))

                .setTangent(0)
                .splineToLinearHeading(new Pose2d(30, 30, Math.toRadians(90)), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(34, 50, Math.toRadians(90)), Math.toRadians(90))


                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(180))

                .setTangent(270)
                .splineToLinearHeading(new Pose2d(-14, 48, Math.toRadians(90)), Math.toRadians(90))


                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-30, 28, Math.toRadians(90)), Math.toRadians(225))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();

    }
}