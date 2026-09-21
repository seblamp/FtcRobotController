package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class MecanumDriveExemple {


    /* ------------------------------------------------------------------------------------------
    -----------------------------------VARIABLES -----------------------------------------------*/

    private DcMotor frontLeft, backLeft, frontRight, backRight; // Déclaration des moteurs
    private IMU imu;    //Déclaration du Gyro du ControlHub
    private double targetHeading;    //Variable pour la correction en ligne droite
    private boolean holdingHeading = false;  //Variable pour vérifier si on était déjà en train de rouler en ligne droite avec un headging
    private static final double DRIVE_KP = 0.02; // Variable qui gère l'ampleur de la correction en ligne droite

    /* ------------------------------------------------------------------------------------------
    -----------------------------INITIALISATION DU MATÉRIEL ------------------------------------*/

    //Méthode qui initialise les moteurs et le IMU
    public void init (HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotor.class, "frontLeft"); //Les noms en vert doivent être identiques à la configuration sur le DriverHub
        backLeft = hwMap.get(DcMotor.class, "backLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backRight = hwMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);  //Obverver la base lorsqu'elle avance et inverser les moteurs au besoin
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);   //Les moteurs utilisent sont PID interne pour toujours avoir le bon power
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);    //Les moteurs sont en mode brake quand il n'y a pas de signal de la manette
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class,"imu"); // imu devrait être le mom par défault sur la configuration du DriverHub
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,     //Changer UP et FOWARD pour la réalité du robot
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ); //Le ControlHub devrait toujours être dans un angle droit par rapport à la structure du robot

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    /* -----------------------------------------------------------------------------------------
    ---------------------------DÉPLACEMENT DE LA BASE -----------------------------------------*/

    // Méthode pour la base en RobotOriented,
    public void drive  (double forward, double strafe, double rotate){   //déclaration des variables
        double frontLeftPower = forward + strafe + rotate;  //Formules qui gèrent le power aux roues.
        double backLeftPower = forward - strafe + rotate;    // 1. Ajuster avance/recule en inversant les moteurs au besoin
        double frontRightPower = forward - strafe - rotate;  // 2. Tester le strafe et modifier le signe +/- devant strafe dans les formules au besoin
        double backRightPower = forward + strafe - rotate;   // 3. Tester le rotate et modifier le signe +/- devant rotate dans les formules au besoin

        double maxPower = 1.0;
        double maxSpeed = 1.0;  //Ajustement de la vitesse maximale au besoin

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower)); // On trouve le plus grand power demandé par les 4 moteurs.
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));  // Le minimum est 1.0 et on divise uniquement si on a un input plus grand que 1.0
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeft.setPower(maxSpeed*(frontLeftPower/maxPower));  //Calcul du power de chaque moteur qui ne peut pas dépasser 1.0
        backLeft.setPower(maxSpeed*(backLeftPower/maxPower));
        frontRight.setPower(maxSpeed*(frontRightPower/maxPower));
        backRight.setPower(maxSpeed*(backRightPower/maxPower));
    }

    //Méthode pour piloter en Field Oriented
    public void  driveFieldRelative(double forward, double strafe, double rotate) {
        double theta = Math.atan2(forward, strafe);  //On transforme les valeurs en coordonnées polaires avec de la trigonométrie
        double r = Math.hypot(strafe,forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);  // retransformation en coordonnées cartésiennes
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward, newStrafe, rotate);  // On appelle la méthode drive avec les nouvelles variables

    }

    /*---------------------------------------------------------------------------------------
    ------------------------------------MÉTHODES DU IMU --------------------------------------*/

    public double getHeading() {         //Méthode pour connaître le heading (le Yaw) actuel du robot
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public void resetYaw() {
        imu.resetYaw();}  //Méthode pour remettre l'orientation du robot à zéro


    /*----------------------------------------------------------------------------------------------
    -----------------------------TÉLÉOP : CORRECTION LIGNE DROITE ------------------------------*/

    public void startHoldingHeading() {    //Méthode qui démarre la correction en ligne droite
        targetHeading = getHeading();       //Le heading initial devient le target heading
        holdingHeading = true;              //La condition "es-tu en train de rouler droit ?" devient vrai
    }
    public void stopHoldingHeading() {    //Méthode qui arrête la correction de la ligne droite
        holdingHeading = false;         //La condition "es-tu en train de rouler droit ?" redevient fausse
    }
    public boolean isHoldingHeading() {     //Méthode pour que le robot vérifie si la correction en ligne droite est active
        return holdingHeading;              //Retourne cette variable true or false
    }
    public double headingError() {         //Méthode qui calcule l'erreur entre le target et la réalité
        return AngleUnit.normalizeDegrees(targetHeading - getHeading());   //Ajuste la valeur de l'Angle au cas où on passe de 180 à -180 degrés
    }
    public double headingCorrection() {   //Méthode qui transforme l'erreur de heading en différence de power que les moteurs vont avoir
        return headingError() * DRIVE_KP;  // 3 degrés d'erreur * 0.02 = 0.06 de power de plus ou moins pour le moteur
    }

    public double BotHeading(AngleUnit angleUnit) {   //méthode pour avoir le heading du robot de -180 à 180 ou -pie à pie
        return imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

}
