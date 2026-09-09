package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClassBotBasePilotable {
    private DcMotor leftMotor, rightMotor;
    private double speedMultiplier = 1.0 ;


    public void init(HardwareMap hwMap) {
        leftMotor=hwMap.get(DcMotor.class, "leftmotor");
        rightMotor=hwMap.get(DcMotor.class, "rightmotor");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double avance, double tourne) {
        double leftPower = avance + tourne;
        double rightPower = avance - tourne;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower/= largest;
        }
        leftMotor.setPower((leftPower*speedMultiplier));
        rightMotor.setPower((rightPower*speedMultiplier));
    }
    public void setSpeedMultiplier (double multiplier)
    {speedMultiplier = multiplier;
    }

    public void resetDriveEncoders(){
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int leftMotorPosition () {
        return leftMotor.getCurrentPosition();
    }
    public int rightMotorPosition () {
        return rightMotor.getCurrentPosition();
    }
}
