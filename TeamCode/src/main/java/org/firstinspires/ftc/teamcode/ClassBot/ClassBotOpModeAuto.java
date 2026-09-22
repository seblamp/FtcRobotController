package org.firstinspires.ftc.teamcode.ClassBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name ="TestAutoClassBot")
public class ClassBotOpModeAuto extends OpMode {
    ClassBotDrive drivetrain = new ClassBotDrive();
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
                    drivetrain.driveAuto(1000, 1000, 0.5);
                    arm.setArmUp(0.6);
                    stateStarted = true;
                }

                if (!drivetrain.isDriveBusy() && !arm.isArmBusy()) {
                    state = State.TURN;
                    stateStarted = false;
                }
                break;

            case TURN:
                if (!stateStarted) {
                    drivetrain.driveAuto(-500, 500, 0.5);
                    stateStarted = true;
                }
                if (!drivetrain.isDriveBusy()) {
                    state = State.RAISE_ARM_CLOSE_CLAW;
                    stateStarted = false;
                }
                break;

            case RAISE_ARM_CLOSE_CLAW:
                if (!stateStarted) {
                    arm.setArmDown(0.6);
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
        telemetry.addData("Limit Switch", arm.isTouchSensorPressed());
        telemetry.addData("Arm Position", arm.armPosition());
        telemetry.addData("Left Motor", drivetrain.leftMotorPosition());
        telemetry.addData("Right Motor", drivetrain.rightMotorPosition());
    }
}
