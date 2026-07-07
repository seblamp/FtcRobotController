package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class UseRobotLocationOpMode extends OpMode {

    RobotLocationPractice robotLocationPractice = new RobotLocationPractice(0);


    @Override
    public void init() {
        robotLocationPractice.setAngle(0);
        robotLocationPractice.setX(0);
        robotLocationPractice.setY(0);
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
            robotLocationPractice.turnRobot(0.1);
        }

        if (gamepad1.b) {
            robotLocationPractice.turnRobot(-0.1);
        }

        if (gamepad1.dpad_left) {
            robotLocationPractice.changeX(0.1);
        }

        else if (gamepad1.dpad_right) {
            robotLocationPractice.changeX(-0.1);
        }

        if (gamepad1.dpad_up) {
            robotLocationPractice.changeY(0.1);
        }
        else if (gamepad1.dpad_down) {
            robotLocationPractice.changeY(-0.1);
        }

        telemetry.addData("Heading", robotLocationPractice.getHeading());
        telemetry.addData("Angle", robotLocationPractice.getAngle());
        telemetry.addData("X", robotLocationPractice.getX());
        telemetry.addData("Y", robotLocationPractice.getY());
    }
}
/*
1. add a double getAngle to your RobotLocationPractice and display this in your OpMode
2.Inside your RobotLocationPractice class,
- create a double x
- double getX()
- void changeX (double changeAmount)
-setX (double x)
3. Inside your OpMode,
    - When left dpad is pressed add 0.1 to x
    - When right dpad is pressed subtract 0.1 to x
    - Telemetry display your x value
 4. Add support for Y using dpad up and dpad down (0.1 and -0.1)
 */