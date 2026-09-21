package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp  //TeleOp, Autonomous ou Disabled

public class MecanumOpModeExemple extends OpMode {

    MecanumDriveExemple drive = new MecanumDriveExemple();  //Création de l'instance Mecanum drive à partir de notre classe MecanumDriveExemple

    double forward, strafe, rotate;  //Variables pour les contrôles
    boolean fieldOriented = true;       //Variables pour passer de Robot à Field Oriented
    boolean previousBackButton = false;
    boolean previousStartButton = false;


    @Override
    public void init() {
        drive.init(hardwareMap);  //Initialisation du matériel
    }

    @Override
    public void loop() {
        //forward = gamepad1.left_stick_y;  //Attribution des contrôles
        forward = (gamepad1.right_trigger - gamepad1.left_trigger); // enlever le commentaire et mettre devant l'autre forward pour contrôler avec les triggers ou le joystick
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad1.left_bumper || gamepad1.right_bumper) {  //Boutons pour ralentir le robot
            forward *= 0.4;
            strafe *= 0.7;
            rotate *=0.4;
        }

        boolean drivingStraight = ((Math.abs(forward) > 0.05 && Math.abs(strafe) < 0.05) ||  //Si le robot avance /recule ou si le robot strafe, la correction ligne droite active
                        (Math.abs(strafe) > 0.05 && Math.abs(forward) < 0.05))
                        && Math.abs(rotate) < 0.05;

        if (drivingStraight && !drive.isHoldingHeading()) {   //Si le robot va droit et qu'il n'était pas déja en train d'appliquer une correction ligne droite
            drive.startHoldingHeading();                     //Active la correction ligne droite
        }
        else if (!drivingStraight && drive.isHoldingHeading()) {       //Si le robot n'est pas en train de rouler droit et qu'il y avait une correction ligne droite active
            drive.stopHoldingHeading();                 // Arrête la correction ligne droite

        }if (drive.isHoldingHeading()) {            //Si le robot roule droite, la variable rotate est maintenant calculée par l'erreur de heading
            rotate = -drive.headingCorrection();   //enlever le signe négatif si le robot fait le contraire de ce qu'on veut
        }

        // Passage de Robot à Field Oriented
        if (gamepad1.back && !previousBackButton) {    //On appui sur back et on n'appuyait pas dessus au loop précédent
            fieldOriented = !fieldOriented;   // On switch le mode du robot (robot à field ou field à robot)
        }
        previousBackButton = gamepad1.back; //enregistre l'état du bouton back

        if (fieldOriented) {
            drive.driveFieldRelative(forward, strafe, rotate);  // Méthode à utiliser si Field Oriented
        } else {
            drive.drive(forward, strafe, rotate);       //Méthode à utiliser si robot Oriented
        }

        // Bouton pour remettre l'angle du gyro à 0
        /*if (gamepad1.start && !previousStartButton) {   //On appui sur start et on n'appuyait pas dessus au loop précédent
            drive.resetYaw();   // remet le Yaw à 0
        }
        previousStartButton = gamepad1.start;*/  //enregistre l'état du bouton start

        // Bouton pour remettre pour réinitialiser le IMU et remettre l'angle du gyro à 0
        if (gamepad1.start && !previousStartButton) {   //On appui sur start et on n'appuyait pas dessus au loop précédent
            drive.resetIMU();   // reset IMU, Yaw à 0
        }
        previousStartButton = gamepad1.start;  //enregistre l'état du bouton start

        telemetry.addData("Mode", fieldOriented ? "FIELD ORIENTED" : "ROBOT ORIENTED");//Télémétrie pour savoir si on est en field ou robot oriented
        telemetry.addData("Heading", drive.BotHeading(AngleUnit.DEGREES));
        telemetry.update();
        telemetry.addData("Heading Error", drive.headingError());
        telemetry.addData("Correction", drive.headingCorrection());

    }

}

