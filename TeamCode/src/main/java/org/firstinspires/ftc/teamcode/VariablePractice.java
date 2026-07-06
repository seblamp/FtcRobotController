package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VariablePractice extends OpMode {
    @Override
    public void init() {
        int teamNumber = 18013;
        double motorSpeed = 0.75;
        boolean clawClosed = true;
        String teamName = "Cuivre & Or";
        int motorAngle = 90;

        telemetry.addData("Team",teamNumber);
        telemetry.addData("Motor Speed",motorSpeed);
        telemetry.addData("Is Claw Closed ?",clawClosed);
        telemetry.addData("Team name",teamName);
        telemetry.addData("Motor angle", motorAngle);
    }

    @Override
    public void loop() {

    }

    /*
    1. Change the string variable "name" to your team name
    2. Create an int called "motorAngle" and store an angle between 0 and 180 degrees. Display in your init method
     */
}
