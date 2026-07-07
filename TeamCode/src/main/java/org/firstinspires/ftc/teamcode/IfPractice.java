package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class IfPractice extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        double motorSpeed = gamepad1.left_stick_y;

        if (!gamepad1.a) {
            motorSpeed *= 0.5;
        }
        telemetry.addData("Motor Speed", motorSpeed);
        {

            /*
            1. Make a turbo button. If hte A button is not pressed, mutiply the motor speed by a 0.5. Otherwise, use the standard speed
             */



      /*if (leftY < 0) {
           telemetry.addData("Left Stick", "is Negative");
       }
// les else if se font dans l'ordre. Si le premier else if englobe le 2e, le 2e n'aura jamais lieu

       else if (leftY > 0.5) {
           telemetry.addData("Left Stick","Greater than 50%");
       }
       else if (leftY >0) {
        telemetry.addData("Left Stick", "Is Positive");
       }

       else  {
           telemetry.addData("Left Stick", "is 0");
       } */




     /*double leftY = gamepad1.left_stick_y;

       if(leftY <0.1 && leftY > -0.1) {
           telemetry.addData("Left Stick", "In Dead Zone");
       }

            telemetry.addData("Left Stick Value", gamepad1.left_stick_y);
       }*/




        /*  boolean aButton = gamepad1.a; // press = True depress = Fals
      if (aButton) {       // Test si le bouton A est True or False
            telemetry.addData("A Button","Pressed !");
        }
        else {
            telemetry.addData("A Button","NOT Pressed !");
        }
        telemetry.addData("A Button State", aButton); */


        }

    }
}