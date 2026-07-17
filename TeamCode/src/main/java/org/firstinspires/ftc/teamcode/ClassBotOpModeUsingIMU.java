package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ClassBotArm;
import org.firstinspires.ftc.teamcode.mechanisms.ClassBotClaw;
import org.firstinspires.ftc.teamcode.mechanisms.ClassBotDrive;


@TeleOp(name = "ClassBot Using IMU")
public class ClassBotOpModeUsingIMU extends OpMode {

    ClassBotDrive drivetrain = new ClassBotDrive();  //on refait des instances de nos 3 classes pour contrôler le robot
    ClassBotArm arm = new ClassBotArm();
    ClassBotClaw claw = new ClassBotClaw();

    double forward,turn;   //variables pour le drivetrain

    @Override
    public void init() { // Init sur le Driver Hub
        //On initialise le matériel
        drivetrain.init(hardwareMap);
        arm.init(hardwareMap);
        claw.init(hardwareMap);
        //On reset les encodeurs de tous les moteurs
        arm.resetArmEncoder();
        drivetrain.resetDriveEncoders();


        claw.open(); //On ouvre la pince

    }

    @Override
    public void loop() { //Après avoir pesé sur Play
        /*
        Avance/recule : joystick de gauche ou gachette droite et gauche
        Tourne : joystick de droite
        Ralentir robot : bumper gauche ou droite
        Monter bras : dpad-up
        descendre bras : dpad-down
        position bras down : y
        position bras middle : dpad-left
        poosition bras up : dpad-right
        Ouvrir pince : a
        Fermer pince : b
        Pince au milieu x
         */

        //Contrôles de la base
        //forward = -gamepad1.left_stick_y;  // enlever le commentaire pour activer
        forward = (gamepad1.right_trigger - gamepad1.left_trigger);  // enlever le commentaire pour activer
        turn = gamepad1.right_stick_x;

        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            forward *= 0.4;
            turn *= 0.4;
        }
        drivetrain.drive(forward,turn);


        //Contrôles du bras
        if (gamepad1.dpad_up) {
            arm.setArmSpeed(0.5);
        }
        else if (gamepad1.dpad_down) {
            arm.setArmSpeed(-0.5);
        }
        else if (!arm.isArmBusy()) {
            arm.setArmSpeed(0);
        }

        if (gamepad1.y) {
            arm.setArmDown(0.6);
        }
        else if (gamepad1.dpad_left) {
            arm.setArmMiddle(0.6);
        }
        else if (gamepad1.dpad_right) {
            arm.setArmUp(0.6);
        }

        //Contrôles de la pince
        if (gamepad1.a) {
            claw.open();
        }
        else if (gamepad1.x) {
            claw.neutral();
        }
        else if (gamepad1.b) {
            claw.close();
        }

        //Télémétrie des moteurs et de la limit switch
        telemetry.addData("Limit Switch", arm.isTouchSensorPressed());
        telemetry.addData("Arm Position", arm.armPosition());
        telemetry.addData("Left Motor", drivetrain.leftMotorPosition());
        telemetry.addData("Right Motor", drivetrain.rightMotorPosition());
        telemetry.addData("Distance Left (cm)", "%.2f", drivetrain.distanceLeftMotor());
        telemetry.addData("Distance Right (cm)", "%.2f", drivetrain.distanceRightMotor());
        telemetry.addData("Heading", drivetrain.classBotHeading(AngleUnit.DEGREES));

    }
}



