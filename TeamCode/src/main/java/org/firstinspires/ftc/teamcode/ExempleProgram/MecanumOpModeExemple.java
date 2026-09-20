package org.firstinspires.ftc.teamcode.ExempleProgram;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
        if (gamepad1.start && !previousStartButton) {   //On appui sur start et on n'appuyait pas dessus au loop précédent
            drive.resetYaw();   // remet le Yaw à 0
        }
        previousStartButton = gamepad1.start;  //enregistre l'état du bouton start

        telemetry.addData(       //Télémétrie pour savoir si on est en field ou robot oriented
                "Mode",
                fieldOriented ? "FIELD ORIENTED" : "ROBOT ORIENTED"
        );
        telemetry.update();
    }

}

