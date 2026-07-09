package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestBenchDistance;
@Disabled
@TeleOp
public class DistaceTest extends OpMode {
    TestBenchDistance bench = new TestBenchDistance();


    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        double distance = bench.getDistance();
        telemetry.addData("Distance", bench.getDistance());
        if(distance < 10) {
            telemetry.addLine("too close");
        }
        else {
            telemetry.addLine("you're good");
        }

    }

    /*
    1.Print "too close" if your objet is less than 10 cm away
     */
}
