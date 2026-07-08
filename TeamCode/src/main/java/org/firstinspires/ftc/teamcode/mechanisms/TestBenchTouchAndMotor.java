package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TestBenchTouchAndMotor {

    private DigitalChannel touchSensor; //habituellement on met un nom plus descriptif ex : touchIntake
    private DcMotor motor; // Donner un nom précis idéalement ex : front_left
    private double ticksPerRev; //ticks per revolution

    public void init(HardwareMap hwMap) {

        // touch sensor
        touchSensor = hwMap.get(DigitalChannel.class, "touch_sensor"); //le nom en vert "touch_sensor" doit être identique à la configuration sur le Driver Hub
        touchSensor.setMode(DigitalChannel.Mode.INPUT);

        //DC Motor
        motor = hwMap.get(DcMotor.class, "motor"); //en vert "motor" doit être le nom exact de la configuration sur le Driver Hub
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // ex : va aider si 2 moteurs sur un gearbox doivent tourner ensenble à la même vitesse
        ticksPerRev = 28.0;
        //motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Brake ou float
        //motor.setDirection(DcMotorSimple.Direction.FORWARD); //si on a besoin d'inverser le moteur
    }

    // touch sensor getter
    public boolean isTouchSensorPressed() {
        return !touchSensor.getState(); //le point d'exclamation permet de retourner l'inverse de l'état du sensor selon si on a besoin de true or false
    }
    public boolean isTouchSensorReleased() {
        return touchSensor.getState();
    }

    // DC motor getter
    public void setMotorSpeed (double speed) {
        // accept values from -1.0 to 1.0
        motor.setPower(speed);
    }
    public double getMotorRevs() {
        return  motor.getCurrentPosition()/ticksPerRev; // normalizing ticks to revolution
    }
    public void setMotorZeroBehaviour(DcMotor.ZeroPowerBehavior zeroBehaviour) {
        motor.setZeroPowerBehavior(zeroBehaviour);
    }

    public DcMotor.ZeroPowerBehavior getMotorZeroBehaviour() {
        return motor.getZeroPowerBehavior();

    }

}



