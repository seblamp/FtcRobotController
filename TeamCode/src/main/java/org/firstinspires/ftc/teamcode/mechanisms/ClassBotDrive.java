package org.firstinspires.ftc.teamcode.mechanisms;

import static java.lang.Math.PI;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ClassBotDrive {    //Classe avec méthodes pour le Drivetrain

    private DcMotor frontLeftMotor, frontRightMotor; // Création des moteurs
    private static final double ticksPerRev = 636.0;   // encoder pour une rotation
    private static final double wheelDiameter = 9.0; //diamètre de la roue du ClassBot
    private IMU imu;  //Création du gyroscope
    private double targetHeading;   //cible pour le virage
    private double turnPower; // vitesse pendant le virage
    private static final double turnTolerance = 2.0;// tolerance en degrés pour terminer le virage
    private boolean turning = false;

    public void init(HardwareMap hwMap) {  //Déclaration du matériel (Utilisation de front au cas où on ajouterait 2 moteurs (back))
        frontLeftMotor = hwMap.get(DcMotor.class, "leftmotor"); //Nom identique à la configuration du driverhub
        frontRightMotor = hwMap.get(DcMotor.class, "rightmotor"); //Nom identique à la configuration du driverhub

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);  // Dans le mode using encoder le robot essai constamment de
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // réajuster son power

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE); // un moteur doit être inversé par rapport à l'autre
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //mode brake pour des mouvements plus précis
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class, "imu"); //Nom identique à la configuration du driverhub
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,   //Le logo du Control Hub est vers l'arrière dans le ClassBot
                RevHubOrientationOnRobot.UsbFacingDirection.UP);

        imu.initialize(new IMU.Parameters(RevOrientation)); //Le port USB-C du Control Hub est vers le haut dans le ClassBot
        imu.resetYaw();
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
        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);
    }

    //méthode pour remettre les encodeurs à zéro au début du OpMode
    public void resetDriveEncoders() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //méthode pour transformer les cm en ticks
    public int cmToTicks(double distanceCm) {
        double wheelCircumference = PI * wheelDiameter;
        double rotations = distanceCm / wheelCircumference;

        return (int) Math.round(rotations * ticksPerRev);
    }

    // Méthodes de télémétrie (encodeur, distance en cm, heading en degrés, power et modes du moteur)
    public int leftMotorPosition() {  //methode pour avoir la télémétrie du moteur gauche
        return frontLeftMotor.getCurrentPosition();
    }
    public int rightMotorPosition() {  //methode pour avoir la télémétrie du moteur droit
        return frontRightMotor.getCurrentPosition();
    }
    public double distanceLeftMotor() {
        return  (frontLeftMotor.getCurrentPosition()/ticksPerRev) * PI * wheelDiameter; // calcul de la distance en cm, roue gauche
    }
    public double distanceRightMotor() {
        return  (frontRightMotor.getCurrentPosition()/ticksPerRev) * PI * wheelDiameter; // calcul de la distance en cm, roue droite
    }
    public double classBotHeading(AngleUnit angleUnit) {   //méthode pour avoir le heading du robot de -180 à 180 ou -pie à pie
        return imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }
    public double getTargetHeading() {
        return targetHeading;
    }
    public boolean getTurning() {
        return turning;
    }
    public double getLeftPower() {
        return frontLeftMotor.getPower();
    }
    public double getRightPower() {
        return frontRightMotor.getPower();
    }
    public DcMotor.RunMode getLeftMode() {
        return frontLeftMotor.getMode();
    }
    public DcMotor.RunMode getRightMode() {
        return frontRightMotor.getMode();
    }

    //Méthodes pour le mode autonome
    public void driveAuto(int leftTarget, int rightTarget, double speedAuto) {  // Méthode pour bouger en Autonome selon une valeur d'encodeur

        int targetLeft = frontLeftMotor.getCurrentPosition() + leftTarget;  //La cible est la position actuelle + le mouvement voulu
        int targetRight = frontRightMotor.getCurrentPosition() + rightTarget;

        frontLeftMotor.setTargetPosition(targetLeft);   //on met les moteurs à la cible
        frontRightMotor.setTargetPosition(targetRight);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Moteur en run to position
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftMotor.setPower(speedAuto); // le power des moteurs à speed
        frontRightMotor.setPower(speedAuto);
    }
    public boolean isDriveBusy() {       // Vérifier si les moteurs sont occupés, que la manœuvre n'est pas terminées
        return frontLeftMotor.isBusy() || frontRightMotor.isBusy();
    }

    public void driveDistance(double distanceCm, double speed) {
        int ticks = cmToTicks(distanceCm);
        driveAuto(ticks, ticks, speed);
    }

    public void turnDegrees(String direction, double degrees, double power) { // Méthode pour tourner avec le IMU en autonome
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //On remet les moteurs dans le bon mode

        double currentHeading = classBotHeading(AngleUnit.DEGREES);// On va lire l'angle actuel

        turnPower = Math.abs(power);

        if (direction.equals("RIGHT")) {
            targetHeading = currentHeading - degrees;// si on veut virer à droite, on fait le heading - l'angle désiré
        }
        else if (direction.equals("LEFT")) {
            targetHeading = currentHeading + degrees;// si on veut virer à gauche, on fait le heading + l'angle désiré
        }
        targetHeading = normalizeAngle(targetHeading);// correction pour gérer le passage de -180 à 180 degrés
        turning = true;// permet de sortir of the state
    }

    private double normalizeAngle(double angle) {  // méthode pour éviter le problème du -180 qui se téléporte à 180 degrés
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    // méthode pour vérifier si la manœuvre est terminée et donner le bon power aux moteurs

    public boolean isTurnBusy() {
        if (!turning) {
            return false;
        }
        double currentHeading =
                classBotHeading(AngleUnit.DEGREES);
        double error =
                normalizeAngle(targetHeading - currentHeading);
        // Vérification si on est à la cible
        if (Math.abs(error) <= turnTolerance) {
            frontLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            turning = false;
            return false;
        }
        // sinon, on continue à tourner dans le bon sens
        if (error > 0) {
            // Tourner vers la gauche
            frontLeftMotor.setPower(-turnPower); // moteur gauche recule, moteur droit avance
            frontRightMotor.setPower(turnPower);
        }
        else {
            // Tourner vers la droite
            frontLeftMotor.setPower(turnPower);// moteur gauche avance, moteur droit recule
            frontRightMotor.setPower(-turnPower);
        }
        return true;
    }




}

