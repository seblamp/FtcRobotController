package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.TestBenchIMU;
@Disabled
@TeleOp
public class ImuPractice extends OpMode {

    TestBenchIMU bench = new TestBenchIMU();
     double heading;

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        heading = bench.getHeading(AngleUnit.RADIANS);
        telemetry.addData("Heading", bench.getHeading(AngleUnit.RADIANS));

        if (heading > 0.5){
            bench.setMotor(-0.5);
        } else if (heading < -0.5) {
            bench.setMotor(0.5);
        }
        else {
            bench.setMotor(0.0);
        }


    }
}

/*
1. Return heading in radians
2.When you heading is less than 0.5 and greater than -0.5, stop a motor. If its less than -0.5 make your motor positive
and if its greater than 0.5 make the motor negative
 */