package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArcadeDrive {
    private DcMotor frontLeftMotor, frontRightMotor;
    //private DcMotor backLeftMotor, backRightMotor;

    public void init (HardwareMap hwMap) {
        frontLeftMotor = hwMap.get(DcMotor.class, "leftmotor");
        frontRightMotor = hwMap.get(DcMotor.class, "rightmotor");
        //backLeftMotor =  hwMap.get(DcMotor.class, "leftMotor1");
       // backRightMotor = hwMap.get(DcMotor.class, "rightMotor1");

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //setter method
    public void drive (double throttle, double spin) {
        double leftPower = throttle + spin;  // formules pour avancer et tourner
        double rightPower = throttle - spin;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));  // ajustement pour ne pas dépaaser 1
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower/= largest;
        }
        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);
        //backLeftMotor.setPower(leftPower);
        //frontRightMotor.setPower(rightPower);

    }


}
