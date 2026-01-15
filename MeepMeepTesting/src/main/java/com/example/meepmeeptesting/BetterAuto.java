package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BetterAuto {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)

                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(13.41339, 14.33071)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(65, 20, Math.toRadians(180)))

                .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(35, 28, Math.toRadians(90)), Math.toRadians(0))
                .strafeTo(new Vector2d(35, 48))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(12, 28, Math.toRadians(90)), Math.toRadians(0))
                .strafeTo(new Vector2d(12, 48))
                .strafeTo(new Vector2d(3, 60))
                .setTangent(Math.toRadians(300))
                .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(180))
                        .setTangent(Math.toRadians(45))
                .splineToLinearHeading(new Pose2d(-11, 29, Math.toRadians(90)), Math.toRadians(0))
                .strafeTo(new Vector2d(-11, 48))
                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(-24, 23, Math.toRadians(135)), Math.toRadians(225))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
        //

    }
}