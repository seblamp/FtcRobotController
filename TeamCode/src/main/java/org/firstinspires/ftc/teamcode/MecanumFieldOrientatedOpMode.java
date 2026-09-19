package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ArcadeDrive;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;

@TeleOp
public class MecanumFieldOrientatedOpMode extends OpMode {

    MecanumDrive robot = new MecanumDrive();
    double forward, strafe, rotate;

    boolean fieldOriented = true;
    boolean previousBackButton = false;
    boolean previousStartButton = false;

    @Override
    public void init() {
        robot.init(hardwareMap);

    }

    @Override
    public void loop() {

        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (gamepad1.back && !previousBackButton) {
            fieldOriented = !fieldOriented;
        }

        previousBackButton = gamepad1.back;

        if (fieldOriented) {
            robot.driveFieldRelative(forward, strafe, rotate);
        } else {
            robot.drive(forward, strafe, rotate);
        }

        if (gamepad1.start && !previousStartButton) {
            robot.resetYaw();
        }

        previousStartButton = gamepad1.start;

        telemetry.addData(
                "Mode",
                fieldOriented ? "FIELD ORIENTED" : "ROBOT ORIENTED"
        );
        telemetry.update();
    }

}
