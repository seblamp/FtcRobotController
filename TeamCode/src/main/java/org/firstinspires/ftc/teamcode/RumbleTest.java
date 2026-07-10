package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class RumbleTest extends OpMode {
    boolean wasA, isA;

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        isA = gamepad1.a;
        if (isA && !wasA) {
            gamepad1.rumbleBlips(3);
        }
        wasA = isA;
    }
}
