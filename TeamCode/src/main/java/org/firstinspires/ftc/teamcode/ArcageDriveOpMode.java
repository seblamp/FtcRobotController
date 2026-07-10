package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ArcadeDrive;

@TeleOp
public class ArcageDriveOpMode extends OpMode {

    ArcadeDrive robot = new ArcadeDrive();
    double throttle, spin;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        throttle = -gamepad1.left_stick_y;
        spin = gamepad1.right_stick_x;

        robot.drive(throttle,spin);


    }
}
