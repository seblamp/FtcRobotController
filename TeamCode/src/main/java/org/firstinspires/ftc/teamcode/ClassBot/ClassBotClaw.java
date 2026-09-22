package org.firstinspires.ftc.teamcode.ClassBot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClassBotClaw { // classe qui contrôle le servo

    private Servo servoPos;

    public void init(HardwareMap hwMap) {  // déclaration du matériel
        servoPos = hwMap.get(Servo.class, "claw");

    }

    public void open() {   //méthodes pour les différentes positions du servo
        servoPos.setPosition(1.0);
    }

    public void neutral() {
        servoPos.setPosition(0.5);
    }

    public void close() {
        servoPos.setPosition(0.0);
    }

}
