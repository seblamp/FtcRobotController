package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TestBenchMotor {
    private DcMotor motor; // Donner un nom précis idéalement ex : front_left
    private double ticksPerRev; //ticks per revolution

    public void init(HardwareMap hwMap) {

        motor = hwMap.get(DcMotor.class, "motor"); //en vert "motor" doit être le nom exact de la configuration sur le Driver Hub
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // ex : va aider si 2 moteurs sur un gearbox doivent tourner ensenble à la même vitesse
        ticksPerRev = 28.0;
    }

    public void setMotorSpeed (double speed) {
        // accept values from -1.0 to 1.0
        motor.setPower(speed);
    }
    public double getMotorRevs() {
        return  motor.getCurrentPosition()/ticksPerRev; // normalizing ticks to revolution
    }

}
