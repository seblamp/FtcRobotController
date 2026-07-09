package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanisms.TestBenchMotor;
import org.firstinspires.ftc.teamcode.mechanisms.TestBenchTouchAndMotor;

@Disabled
@TeleOp
public class DcMotorPractice extends OpMode {

   TestBenchTouchAndMotor bench = new TestBenchTouchAndMotor();
    @Override
    public void init() {
        bench.init(hardwareMap);
    }
    @Override
    public void loop() {
        double motorSpeed = gamepad1.left_stick_y;
        bench.setMotorSpeed(motorSpeed);
        if (gamepad1.a) {
            bench.setMotorZeroBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else {
            bench.setMotorZeroBehaviour(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        /*
          if (bench.isTouchSensorPressed()) {
            bench.setMotorSpeed(0.6);
        }
        else {
            bench.setMotorSpeed(0.0);
        }
         */

        telemetry.addData("Motor Revs",bench.getMotorRevs());
        telemetry.addData("Motor Zero Behaviour", bench.getMotorZeroBehaviour());
    }

    /*
    1.Add a method on testBench that allows to change the brake behavior from your OpMode. A pressed, set brake, B pressed set Float
     */
}
