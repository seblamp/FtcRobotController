package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TestBenchTouchSensor {
    private DigitalChannel touchSensor; //habituellement on met un nom plus descriptif ex : touchIntake

    public void init(HardwareMap hwMap) {
        touchSensor = hwMap.get(DigitalChannel.class, "touch_sensor"); //le nom en vert "touch_sensor" doit être identique à la configuration sur le Driver Hub
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean getTouchSensorState() {
        return !touchSensor.getState(); //le point d'exclamation permet de retourner l'inverse de l'état du sensor selon si on a besoin de true or false
    }
    public boolean isTouchSensorReleased() {
        return touchSensor.getState();
    }

}
