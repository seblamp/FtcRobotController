package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestBenchTouchSensor;
@Disabled
@TeleOp
public class TouchSensorPractice extends OpMode {

    TestBenchTouchSensor bench = new TestBenchTouchSensor();


    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        String touchSensorState = "not pressed!";
        if (bench.getTouchSensorState()) {
            touchSensorState = "pressed!";

        }
        telemetry.addData("Touch Sensor State", touchSensorState);
        telemetry.addData("Is Touch Sensor Released", bench.isTouchSensorReleased());
    }

    /*
    1.Create a new "getter" method in your testBench class called isTouchSensorRelease". return true
    if the touch is not being pressed.
    2.In your telemetry OpMode, have telemetry state "pressed!" instead of true and "not pressed!" instead of false
     */
}
