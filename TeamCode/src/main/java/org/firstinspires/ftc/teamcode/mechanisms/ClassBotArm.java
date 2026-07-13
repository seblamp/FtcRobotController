package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClassBotArm {  //Classe qui contient toutes les méthodes concernant les bras

    private DigitalChannel touchSensor; //Déclaration du matériel
    private DcMotor arm;//Déclaration du matériel

    private final int armDown = 0;  //Variables pour les positions du bras
    private final int armMiddle = 75;
    private final int armUp = 160;

    public void init(HardwareMap hwMap) {
        touchSensor = hwMap.get(DigitalChannel.class, "touch");   // Déclaration et setup du touch sensor comme input
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        arm = hwMap.get(DcMotor.class, "arm");  //Déclaration de DC Motor
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Brake ou float
        arm.setDirection(DcMotor.Direction.FORWARD); //si on a besoin d'inverser le moteur
    }

    public boolean isTouchSensorPressed() {  // Méthode qui call TRUE si on pèse sur le Touch sensor
        return !touchSensor.getState();
    }

    // DC motor getter
    public void setArmSpeed (double speed) {   //Méthode qui contrôle le bras en mode manuel
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
             if (isTouchSensorPressed()) {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //encodeur à 0 si on pèse sur le touch
                arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if (isTouchSensorPressed() && speed < 0) {   //si on pèse sur le touch, le bras refuse d'aller vers le bas
                arm.setPower(0);
            } else {
                arm.setPower(speed);  // sinon le bras fonction manuellement
            }
        }
    public void setArmPosition(int position, double speed) { //méthode qui donne les paramètres du bras pour avoir des positions
        arm.setTargetPosition(position);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(speed);
    }
    public void setArmDown(double speed) {  //méthode pour le bras en bas
        setArmPosition(armDown, speed);
    }
    public void setArmMiddle(double speed) {  //méthode pour le bras au milieu
        setArmPosition(armMiddle, speed);
    }
    public void setArmUp(double speed) { //méthode pour le bras en haut
        setArmPosition(armUp, speed);
    }
    public boolean isArmBusy() {  //méthode qui permet au bras d'aller à sa position et ne pas avoir de conflit
        return arm.isBusy();  // avec les contrôles manuels
    }
    public void resetArmEncoder() {  //encodeur à 0 sà l'init du OpMode
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public int armPosition() {  //methode pour avoir le télémétrie de l'encodeur du bras
        return  arm.getCurrentPosition();
    }
}








