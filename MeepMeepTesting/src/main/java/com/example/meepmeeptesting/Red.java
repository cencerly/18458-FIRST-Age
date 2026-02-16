package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Red {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .setDimensions(13.41339, 14.33071)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-63, 40, Math.toRadians(180)))

                // Score 1
                .setTangent(Math.toRadians(335))
                .splineToLinearHeading(new Pose2d(-30, 29, Math.toRadians(135)), Math.toRadians(315))

                // Pair 3: Pos5 -> Pos6
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-14, 24, Math.toRadians(90)), Math.toRadians(0))
                .strafeTo(new Vector2d(-14, 49))

                .setTangent(Math.toRadians(225))
                .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(130)), Math.toRadians(225))

                // Pair 2: Pos3 -> Pos4
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(8, 20, Math.toRadians(90)), Math.toRadians(0))
                .strafeTo(new Vector2d(8, 53))

                .strafeTo(new Vector2d(4, 48))
                .strafeTo(new Vector2d(4, 55))

                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(130)), Math.toRadians(180))

                // Pair 1: Pos1 -> Pos2
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(34, 24, Math.toRadians(90)), Math.toRadians(0))
                .strafeTo(new Vector2d(34, 50))

                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-28, 26, Math.toRadians(135)), Math.toRadians(180))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}