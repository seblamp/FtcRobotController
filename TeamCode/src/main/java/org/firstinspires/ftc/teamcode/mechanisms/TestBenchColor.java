package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestBenchColor {
    NormalizedColorSensor colorSensor;

    public enum DetectedColor {
        RED,
        BLUE,
        YELLOW,
        GREEN,
        UNKNOWN
    }

    public void init(HardwareMap hwmMap) {
        colorSensor = hwmMap.get(NormalizedColorSensor.class, "sensor_color_distance"); //même nom que configuration du driver hub
        colorSensor.setGain(9);
    }

    //getter method
    public DetectedColor getDetectedColor(Telemetry telemetry) {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();  // return 4 values, red, green, blue, alpha(brightness)

        float normRed, normGreen, normBlue;
        normRed = colors.red / colors.alpha;    //la valeur de la couleur tient compte de la luminosité (alpha)
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        telemetry.addData("red", normRed);
        telemetry.addData("green", normGreen);
        telemetry.addData("blue", normBlue);

        //To do : add if statements for specific colors added
        //pour les valeurs j'ai fait un document excel

        if (normRed / normGreen > 1.6 && normRed / normBlue > 3.0) {
            return DetectedColor.RED;
        }
        else if (normBlue / normRed > 5.0 && normBlue / normGreen > 2.0) {
            return DetectedColor.BLUE;
        }
        else if (normGreen / normRed > 2.8 && normGreen / normBlue > 1.2) {
            return DetectedColor.GREEN;
        }
        else if (normGreen / normRed > 1.5 && normGreen / normBlue > 4.0) {
            return DetectedColor.YELLOW;
        }
        else {
            return DetectedColor.UNKNOWN;
        }

    }
}