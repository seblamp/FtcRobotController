package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class MecanumDriveExemple {

    /* -------------------------------------------------------
    Variables et matériel  -----------------------*/
    private DcMotor frontLeft, backLeft, frontRight, backRight; // Déclaration des moteurs
    private IMU imu;    //Déclaration du Gyro du ControlHub

    //Méthode qui initialise les moteurs et le IMU
    public void init (HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotor.class, "frontLeft"); //Les noms en vert doivent être identiques à la configuration sur le DriverHub
        backLeft = hwMap.get(DcMotor.class, "backLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backRight = hwMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);  //Obverver la base lorsqu'elle avance et inverser les moteurs au besoin
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

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
                RevHubOrientationOnRobot.LogoFacingDirection.UP,     //Changer UP et FOWARD pour la réalité du robot
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD); //Le ControlHub devrait toujours être dans un angle droit par rapport à la structure du robot

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    // Méthode pour la base en RobotOriented,
    public void drive  (double forward, double strafe, double rotate){   //déclaration des variables
        double frontLeftPower = forward + strafe + rotate;  //Formules qui gèrent le power aux roues.
        double backLeftPower = forward + strafe + rotate;      // 1. Ajuster avance/recule en inversant les moteurs au besoin
        double frontRightPower = forward + strafe + rotate;  //2. Tester le strafe et modifier le signe +/- devant strafe dans les formules au besoin
        double backRightPower = forward + strafe + rotate;   //3. Tester le rotate et modifier le signe +/- devant rotate dans les formules au besoin

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
    public void resetYaw() {
        imu.resetYaw();}  //Méthode pour remettre l'orientation du robot à zéro

}
