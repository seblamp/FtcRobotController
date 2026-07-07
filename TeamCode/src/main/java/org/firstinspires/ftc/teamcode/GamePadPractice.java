package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class GamePadPractice extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        // runs 50 times per second
        double speedForward = -gamepad1.left_stick_y /2.0;
        double speedFowardY = -gamepad1.right_stick_y/2.0;
        double diffXJoysticks = gamepad1.left_stick_x - gamepad1.right_stick_x;
        double sumTriggers = gamepad1.left_trigger + gamepad1.right_trigger;

        telemetry.addData("Left X",gamepad1.left_stick_x);
        telemetry.addData("Left Y", speedForward);
        telemetry.addData("Right X", gamepad1.right_stick_x);
        telemetry.addData("Right Y", speedFowardY);

        telemetry.addData("Difference left and right X",diffXJoysticks);
        telemetry.addData("Sum Triggers", sumTriggers);

        telemetry.addData("A button",gamepad1.a);
        telemetry.addData("B button", gamepad1.b);

    }
    /*
    1. Add telemetry for the right joystick
    2. Add telemetry for the B button
    3. Add telemetry to report the difference between x left joystick and x right joystick
    4. Add telemetry to report the sum of the two triggers

     */

}
