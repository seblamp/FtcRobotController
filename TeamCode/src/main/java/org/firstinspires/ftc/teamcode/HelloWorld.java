package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@Autonomous
public class HelloWorld extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello","Sebastien");
    }

    @Override
    public void loop() {

    }

    // Single Line Comment
    /*
    1. Change Hello World for Hello Sebastien
    2. Run the program in the Autonomous section of the DS
     */
}
