package org.firstinspires.ftc.teamcode.ClassBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClassBotArmSec2 {  //Classe qui contient toutes les méthodes concernant les bras

    private DigitalChannel touchSensor; //Déclaration du matériel
    private DcMotor arm;//Déclaration du matériel


    public void init(HardwareMap hwMap) {
        touchSensor = hwMap.get(DigitalChannel.class, "touch");   // Déclaration et setup du touch sensor comme input
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        arm = hwMap.get(DcMotor.class, "arm");  //Déclaration du DC Motor, même nom que la configuration sur le DriverHub
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Brake ou float
        arm.setDirection(DcMotor.Direction.FORWARD); //si on a besoin d'inverser le moteur
    }

    public boolean isTouchSensorPressed() {  // Méthode qui call TRUE si on pèse sur le Touch sensor
        return !touchSensor.getState();
    }

    // Méthode pour contrôller manuellement le bras
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

    public void resetArmEncoder() {  //encodeur à 0 à l'init du OpMode
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public int armPosition() {  //methode pour avoir le télémétrie de l'encodeur du bras
        return  arm.getCurrentPosition();
    }
}









