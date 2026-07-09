package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.TestBenchServo;
@Disabled
@TeleOp
public class ServoExamples extends OpMode {
    TestBenchServo bench = new TestBenchServo();
    double leftTrigger, rightTrigger;

    @Override
    public void init() {
        bench.init(hardwareMap);
        leftTrigger = 0.0;
        rightTrigger = 0.0;

    }

    @Override
    public void loop() {
        leftTrigger = gamepad1.left_trigger;
        rightTrigger = gamepad1.right_trigger;

        bench.setServoPos(leftTrigger);
        bench.setServoRot(rightTrigger);

        /*
        if (gamepad1.a){
            bench.setServoPos(0.0);
        }
        else {
            bench.setServoPos(1.0);
        }
        if(gamepad1.b){
            bench.setServoRot(1.0);
        }
        else {
            bench.setServoRot(0.0);
        }
*/
    }
}

/*
1. Set your continuous rotation servo to reverse its direction
2. Set your OpMode so when you pull the left gamepad trigger, its sets the position of the pos servo
    and when you pull the right gamepad trigger, 0 is off and 1 is fully on for the CRservo
 */

