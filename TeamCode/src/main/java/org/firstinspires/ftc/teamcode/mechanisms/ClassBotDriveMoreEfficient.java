package org.firstinspires.ftc.teamcode.mechanisms;

import static java.lang.Math.PI;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ClassBotDriveMoreEfficient {    // Classe avec méthodes pour le Drivetrain

    public enum TurnDirection { // Enum utilisé pour choisir la direction d'un virage
        LEFT, RIGHT}

    private DcMotor frontLeftMotor, frontRightMotor; // Création des moteurs
    private IMU imu;                                  // Création du gyroscope

    // Constantes mécaniques du ClassBot
    private static final double TICKS_PER_REV = 636.0;  // Encodeur pour une rotation
    private static final double WHEEL_DIAMETER = 9.0;   // Diamètre en cm de la roue du ClassBot

    // Variables pour les virages avec le IMU
    private double turnTargetHeading;                    // Cible pour le virage
    private double turnPower;                            // Vitesse pendant le virage
    private static final double TURN_TOLERANCE = 2.0;    // Tolérance en degrés pour terminer le virage
    private boolean turning = false;                     // Variable pour vérifier si le virage est terminé

    // Variables pour la correction pour rouler droit
    private boolean drivingStraight = false;
    private double drivePower;
    private double driveHeading;
    private static final double DRIVE_KP = 0.02;

    // Déclaration du matériel
    public void init(HardwareMap hwMap) {   // Utilisation de "front" au cas où on ajouterait deux moteurs "back"

        frontLeftMotor = hwMap.get(DcMotor.class, "leftmotor"); // Nom identique à la configuration du Driver Hub
        frontRightMotor = hwMap.get(DcMotor.class, "rightmotor"); // Nom identique à la configuration du Driver Hub

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// Dans le mode RUN_USING_ENCODER,
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// le robot essaie constamment de réajuster son power

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);// Un moteur doit être inversé par rapport à l'autre
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);// Mode BRAKE pour des mouvements plus précis
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class, "imu");// Nom identique à la configuration du Driver Hub

        RevHubOrientationOnRobot revOrientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.
                LogoFacingDirection.BACKWARD, // Le logo du Control Hub est vers l'arrière dans le ClassBot
                RevHubOrientationOnRobot.UsbFacingDirection.UP); //Le port USB-C du Control Hub est vers le  dans le ClassBot

        imu.initialize(new IMU.Parameters(revOrientation));
        imu.resetYaw();
    }


    // --------------------------------------------------
    // Méthodes pour le téléop
    // --------------------------------------------------

    public void drive(double forward, double turn) { // Méthode qui permet de contrôler une base tank en téléop
        double leftPower = forward + turn; // Formules pour avancer et tourner
        double rightPower = forward - turn;

        double largest = Math.max(Math.abs(leftPower), Math.abs(rightPower)); // Ajustement pour ne pas dépasser 1

        if (largest > 1.0) {leftPower /= largest;rightPower /= largest;}

        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);
    }

    // --------------------------------------------------
    // Méthodes pour les encodeurs et les conversions
    // --------------------------------------------------

    // au début du OpMode
    public void resetDriveEncoders() { // Méthode pour remettre les encodeurs à zéro
        stop();
        drivingStraight = false;
        turning = false;
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public int cmToTicks(double distanceCm) {  // Méthode pour transformer les centimètres en ticks
        double wheelCircumference = PI * WHEEL_DIAMETER;
        double rotations = distanceCm / wheelCircumference;
        return (int) Math.round(rotations * TICKS_PER_REV);
    }
    public double ticksToCm(int ticks) { // Méthode pour transformer les ticks en centimètres
        double rotations = ticks / TICKS_PER_REV;
        return rotations * PI * WHEEL_DIAMETER;
    }

    // --------------------------------------------------
    // Méthodes de télémétrie, Encodeurs, distance en cm, heading, power et modes des moteurs
    // --------------------------------------------------

    public int leftMotorPosition() { // Méthode pour avoir la télémétrie du moteur gauche
        return frontLeftMotor.getCurrentPosition();
    }
    public int rightMotorPosition() { // Méthode pour avoir la télémétrie du moteur droit
        return frontRightMotor.getCurrentPosition();
    }
    public double distanceLeftMotor() { // Calcul de la distance en cm, roue gauche
        return ticksToCm(frontLeftMotor.getCurrentPosition());
    }
    public double distanceRightMotor() { // Calcul de la distance en cm, roue droite
        return ticksToCm(frontRightMotor.getCurrentPosition());
    }

    public double classBotHeading(AngleUnit angleUnit) { // Méthode pour avoir le heading du robot de -180 à 180 degrés ou de -PI à PI radians
        return imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }
    public double getTargetHeading() {
        if (turning) {
            return turnTargetHeading;
        }
        return driveHeading;
    }
    public boolean isTurning() {
        return turning;
    }
    public boolean isDrivingStraight() {
        return drivingStraight;
    }

    // Ancienne méthode conservée pour éviter
    // de devoir modifier immédiatement la télémétrie
    public boolean getTurning() {
        return isTurning();
    }

    // Méthodes de diagnostic et de télémétrie
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

    // --------------------------------------------------
    // Méthodes pour le mode autonome
    // --------------------------------------------------

    public void driveAuto(int leftTarget, int rightTarget, double speedAuto) { // Méthode pour bouger en autonome selon une valeur d'encodeur
        int targetLeft = frontLeftMotor.getCurrentPosition() + leftTarget; // La cible est la position actuelle
        int targetRight = frontRightMotor.getCurrentPosition() + rightTarget;// plus le mouvement voulu

        frontLeftMotor.setTargetPosition(targetLeft); // On met les moteurs à la cible
        frontRightMotor.setTargetPosition(targetRight);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Moteurs en RUN_TO_POSITION
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double power = Math.abs(speedAuto);

        frontLeftMotor.setPower(power); // Le power doit être positif en RUN_TO_POSITION.
        frontRightMotor.setPower(power); // La direction est déterminée par la position cible.

    }
    public boolean isDriveBusy() {  // Vérifier si les moteurs sont occupés et si la manœuvre n'est pas terminée
        return frontLeftMotor.isBusy() || frontRightMotor.isBusy();
    }
    public void driveDistance(double distanceCm, double speed) {
        int ticks = cmToTicks(distanceCm);
        driveAuto(ticks, ticks, speed);
    }

    // --------------------------------------------------
    // Méthodes pour que le robot se corrige en ligne droite
    // --------------------------------------------------
    public void driveStraightDistance(double distanceCm, double speed) {
        int ticks = cmToTicks(distanceCm);
        driveHeading = classBotHeading(AngleUnit.DEGREES);  // On prend en note le heading au début du déplacement
        drivePower = Math.abs(speed);
        drivingStraight = true;  // Cette action est maintenant active
        turning = false;
        driveAuto(ticks, ticks, drivePower);
    }
    public boolean isStraightDriveBusy() {
        if (!drivingStraight) {
            return false;
        }
        if (!isDriveBusy()) {
            stop();
            drivingStraight = false;
            return false;
        }
        double currentHeading = classBotHeading(AngleUnit.DEGREES);
        double error = normalizeAngle(driveHeading - currentHeading);
        double correction = error * DRIVE_KP;
        double leftPower = drivePower - correction;
        double rightPower = drivePower + correction;

        leftPower = Math.max(0.0, Math.min(1.0, leftPower)); //En RUN_TO_POSITION, les puissances doivent demeurer positives entre 0 et 1
        rightPower = Math.max(0.0, Math.min(1.0, rightPower));

        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);

        return true;
    }

    // --------------------------------------------------
    // Méthodes pour les virages avec le IMU
    // --------------------------------------------------
    public void turnDegrees(TurnDirection direction, double degrees, double power) { // Méthode pour tourner avec le IMU en autonome
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // On remet les moteurs dans le bon mode
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double currentHeading = classBotHeading(AngleUnit.DEGREES); // On va lire l'angle actuel

        turnPower = Math.abs(power);

        if (direction == TurnDirection.RIGHT) { // Si on veut virer à droite,
            turnTargetHeading = currentHeading - degrees;  // on fait le heading moins l'angle désiré
        }
        else {
            turnTargetHeading = currentHeading + degrees; // Si on veut virer à gauche, on fait le heading plus l'angle désiré
        }
        turnTargetHeading = normalizeAngle(turnTargetHeading); // Correction pour gérer le passage de -180 à 180 degrés
        turning = true;
        drivingStraight = false; // Permet de sortir du state
    }

    /*
     *Ancienne version conservée temporairement. Elle permet de continuer à utiliser :
     turnDegrees("RIGHT", 90, 0.2);
     La nouvelle version recommandée est :
     turnDegrees(TurnDirection.RIGHT, 90, 0.2);
     */
    public void turnDegrees(String direction, double degrees, double power) {
        if (direction == null) {
            throw new IllegalArgumentException("La direction du virage ne peut pas être null.");
        }
        try {
            TurnDirection turnDirection = TurnDirection.valueOf(direction.trim().toUpperCase());
            turnDegrees(turnDirection, degrees, power);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Direction invalide : " + direction + ". Utiliser LEFT ou RIGHT.", exception);
        }
    }
    private double normalizeAngle(double angle) { // Méthode pour éviter le problème du -180 qui se téléporte à 180 degrés
        while (angle > 180.0) {
            angle -= 360.0;
        }
        while (angle < -180.0) {
            angle += 360.0;
        }
        return angle;
    }
    public boolean isTurnBusy() { // Méthode pour vérifier si la manœuvre est terminée et donner le bon power aux moteurs
        if (!turning) {
            return false;
        }
        double currentHeading = classBotHeading(AngleUnit.DEGREES);
        double error = normalizeAngle(turnTargetHeading - currentHeading);

        // Vérification si on est à la cible
        if (Math.abs(error) <= TURN_TOLERANCE) {
            stop();
            turning = false;
            return false;
        }
        // Sinon, on continue à tourner dans le bon sens
        if (error > 0.0) { // Tourner vers la gauche
            frontLeftMotor.setPower(-turnPower); // Moteur gauche recule,
            frontRightMotor.setPower(turnPower); // moteur droit avance
        }
        else { // Tourner vers la droite
            frontLeftMotor.setPower(turnPower); // Moteur gauche avance,
            frontRightMotor.setPower(-turnPower); // moteur droit recule
        }
        return true;
    }
    public void stop() {
        frontLeftMotor.setPower(0.0);
        frontRightMotor.setPower(0.0);
    }
}