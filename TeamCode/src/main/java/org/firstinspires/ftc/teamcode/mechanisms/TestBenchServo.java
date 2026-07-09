package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TestBenchServo {

    private Servo servoPos; // use more descriptive names
    public CRServo servoRot;

    public void init(HardwareMap hwMap) {
        servoPos = hwMap.get(Servo.class, "servo_pos"); // use sames names as configuration on Driver Hub
        servoRot = hwMap.get(CRServo.class,"servo_cont");
        // servoPos.scaleRange(0.2,0.8);  // set range for servo. 0.5 est le milieu
        //servoPos.setDirection(Servo.Direction.REVERSE);
        servoRot.setDirection(CRServo.Direction.REVERSE);
    }

    //setter functions

    public void setServoPos(double angle){
        servoPos.setPosition(angle);
    }

    public void setServoRot(double power){
        servoRot.setPower(power);
    }

}
