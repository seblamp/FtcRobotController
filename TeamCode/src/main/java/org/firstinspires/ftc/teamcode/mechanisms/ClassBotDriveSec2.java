package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClassBotDriveSec2 {    //Classe avec méthodes pour le Drivetrain

    private DcMotor frontLeftMotor, frontRightMotor; // Création des moteurs
    private double speedMultiplier = 1.0;  //variable pour ralentir le robot


    public void init(HardwareMap hwMap) {  //Déclaration du matériel (Utilisation de front au cas où on ajouterait 2 moteurs (back))
        frontLeftMotor = hwMap.get(DcMotor.class, "leftmotor"); //Nom identique à la configuration du driverhub
        frontRightMotor = hwMap.get(DcMotor.class, "rightmotor"); //Nom identique à la configuration du driverhub

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  // Dans le mode using encoder le robot essai constamment de
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // réajuster son power

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE); // un moteur doit être inversé par rapport à l'autre
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //mode brake pour des mouvements plus précis
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //Méthodes pour le téléop
    public void drive(double forward, double turn) {   //Méthode qui permet de contrôler une base tank en téléop
        double leftPower = forward + turn;  // formules pour avancer et tourner
        double rightPower = forward - turn;
        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower));  // ajustement pour ne pas dépasser 1
        if (largest > 1.0) {
            leftPower /= largest;
            rightPower /= largest;
        }
        frontLeftMotor.setPower(leftPower * speedMultiplier);  //la multiplication permet de ralentir le robot quand un appui sur un bouton
        frontRightMotor.setPower(rightPower * speedMultiplier);  //la multiplication permet de ralentir le robot quand un appui sur un bouton
    }

    //méthode pour avoir un bouton pour ralentir le robot pour des contrôles plus précis
    public void setSpeedMultiplier(double multiplier) {
        speedMultiplier = multiplier;
    }

    //méthode pour remettre les encodeurs à zéro au début du OpMode
    public void resetDriveEncoders() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Méthodes de télémétrie (encodeur, distance en cm, heading en degrés, power et modes du moteur)
    public int leftMotorPosition() {  //methode pour avoir la télémétrie du moteur gauche
        return frontLeftMotor.getCurrentPosition();
    }

    public int rightMotorPosition() {  //methode pour avoir la télémétrie du moteur droit
        return frontRightMotor.getCurrentPosition();
    }
}














