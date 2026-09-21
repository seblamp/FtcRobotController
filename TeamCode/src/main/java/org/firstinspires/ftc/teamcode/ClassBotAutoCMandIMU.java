package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ClassBotArm;
import org.firstinspires.ftc.teamcode.mechanisms.ClassBotClaw;
import org.firstinspires.ftc.teamcode.mechanisms.ClassBotDrive;
import org.firstinspires.ftc.teamcode.mechanisms.ClassBotDriveMoreEfficient;

@Autonomous(name ="TestAutoClassBotWithIMUandCM")
public class ClassBotAutoCMandIMU extends OpMode {
    //ClassBotDrive drivetrain = new ClassBotDrive();
    ClassBotDriveMoreEfficient drivetrain = new ClassBotDriveMoreEfficient();
    ClassBotArm arm = new ClassBotArm();
    ClassBotClaw claw = new ClassBotClaw();

    enum State {
        FORWARD,
        TURN,
        RAISE_ARM_CLOSE_CLAW,
        FINISHED
    }

    State state = State.FORWARD;

    boolean stateStarted = false;


    @Override
    public void init() {
        drivetrain.init(hardwareMap);
        arm.init(hardwareMap);
        claw.init(hardwareMap);
        arm.resetArmEncoder();
        drivetrain.resetDriveEncoders();
        claw.open();
        state = State.FORWARD;
        stateStarted = false;

    }

    @Override
    public void loop() {

        telemetry.addData("Current State", state);
        switch (state) {
            case FORWARD:
                if (!stateStarted) {
                    drivetrain.driveStraightDistance(70, 0.2);
                    stateStarted = true;
                }
                if (!drivetrain.isStraightDriveBusy()) {
                    state = State.TURN;
                    stateStarted = false;
                }
                break;

            case TURN:
                if (!stateStarted) {
                    //drivetrain.turnDegrees("RIGHT", 90, 0.2); //tourner selon la classe ClassBotDrive
                    drivetrain.turnDegrees(ClassBotDriveMoreEfficient.TurnDirection.RIGHT, 90, 0.2); //tourner selon la classe ClassBotDriveMoreEffiecient
                    stateStarted = true;
                }

                if (!drivetrain.isTurnBusy()) {
                    state = State.RAISE_ARM_CLOSE_CLAW;
                    stateStarted = false;
                }
                break;

            case RAISE_ARM_CLOSE_CLAW:
                if (!stateStarted) {
                    arm.setArmUp(0.6);
                    claw.close();
                    stateStarted = true;
                }
                if (!arm.isArmBusy()) {
                    state = State.FINISHED;
                    stateStarted = false;
                }
                break;

            case FINISHED:
                telemetry.addLine("Auto State machine idle");
                break;
        }
        //telemetry.addData("Limit Switch", arm.isTouchSensorPressed());
        //telemetry.addData("Arm Position", arm.armPosition());
        telemetry.addData("Left Motor", drivetrain.leftMotorPosition());
        telemetry.addData("Right Motor", drivetrain.rightMotorPosition());
        telemetry.addData("Distance Left (cm)", "%.2f", drivetrain.distanceLeftMotor());
        telemetry.addData("Distance Right (cm)", "%.2f", drivetrain.distanceRightMotor());
        telemetry.addData("Heading", drivetrain.classBotHeading(AngleUnit.DEGREES));
        telemetry.addData("Turning", drivetrain.isTurnBusy());
        telemetry.addData("Heading", drivetrain.classBotHeading(AngleUnit.DEGREES));
        telemetry.addData("Target", drivetrain.getTargetHeading());
        telemetry.addData("Turning", drivetrain.getTurning());
        telemetry.addData("Left power", drivetrain.getLeftPower());
        telemetry.addData("Right power", drivetrain.getRightPower());
        telemetry.addData("Left mode", drivetrain.getLeftMode());
        telemetry.addData("Right mode", drivetrain.getRightMode());

    }
}

