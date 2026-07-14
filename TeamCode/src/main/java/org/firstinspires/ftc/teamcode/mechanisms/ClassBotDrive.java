package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClassBotDrive {    //Classe avec méthodes pour le Drivetrain

    private DcMotor frontLeftMotor, frontRightMotor;
    private int leftPos,rightPos;

    public void init(HardwareMap hwMap) {  //Déclaration du matériel (Utilisation de front au cas où on ajouterait 2 moteurs (back))
        frontLeftMotor = hwMap.get(DcMotor.class, "leftmotor");
        frontRightMotor = hwMap.get(DcMotor.class, "rightmotor");

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  // Dans le mode using encoder le robot essai constamment de
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // réajuster son power

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE); // un moteur doit être inversé par rapport à l'autre
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //mode brake pour des mouvements plus précis
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive (double forward, double turn) {   //Méthode qui permet de contrôler une base tank
        double leftPower = forward + turn;  // formules pour avancer et tourner
        double rightPower = forward - turn;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));  // ajustement pour ne pas dépasser 1
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower/= largest;
        }
        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);
    }

    public void resetDriveEncoders() {    //méthode pour remettre les encodeurs à zéro au début du OpMode
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public int leftMotorPosition() {  //methode pour avoir le télémétrie du moteur gauche
        return  frontLeftMotor.getCurrentPosition();
    }
    public int rightMotorPosition() {  //methode pour avoir le télémétrie du moteur droit
        return  frontRightMotor.getCurrentPosition();
    }

    public void driveAuto(int leftTarget, int rightTarget, double speedAuto) {

        int targetLeft = frontLeftMotor.getCurrentPosition() + leftTarget;
        int targetRight = frontRightMotor.getCurrentPosition() + rightTarget;

        frontLeftMotor.setTargetPosition(targetLeft);
        frontRightMotor.setTargetPosition(targetRight);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftMotor.setPower(speedAuto);
        frontRightMotor.setPower(speedAuto);
    }
    public boolean isDriveBusy() {
        return frontLeftMotor.isBusy() || frontRightMotor.isBusy();
    }

    }

