package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
public class RobotLocationPractice<angleChange> {

    double angle;
    double x;
    double y;

    // this is a constructor method
    public RobotLocationPractice (double angle) {
        this.angle = angle;
    }

    public double getHeading(){

        // This method make the robot heading between -180 and 180
        // Usefull for calculating angles, especially when crossing the 0,360 boundary

        double angle = this.angle; //copy the angle of the imu

        while (angle > 180) {  // subtract if angle too positive
            angle -=360;
        }
        while (angle <=-180) { // add if angle too negative
            angle +=360;
        }

        return angle;  //return the new value
    }

    public void turnRobot(double angleChange) {
        angle +=angleChange;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return this.angle;
    }

    public void changeX(double changeAmountX) {
        x +=changeAmountX;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void changeY(double changeAmountY) {
        y +=changeAmountY;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }


}
