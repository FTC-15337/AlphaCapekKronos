package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Sorter1 {
    private NormalizedColorSensor colorSensor;


    private DistanceSensor distance;

    public enum DetectedColor {
        PURPLE(1),
        GREEN(2),
        UNKNOWN(3);

        private final int code;

        DetectedColor(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public void init(HardwareMap hwMap) {
        colorSensor = hwMap.get(NormalizedColorSensor.class, "c1");
        distance = hwMap.get(DistanceSensor.class , "d1");
    }


    public DetectedColor getDetectedColor(Telemetry telemetry) {

        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float r = colors.red;
        float g = colors.green;
        float b = colors.blue;

        float total = r + g + b;

        float redRatio = r / total;
        float greenRatio = g / total;
        float blueRatio = b / total;

        //telemetry.addData("Red Ratio", redRatio);
        //telemetry.addData("Green Ratio", greenRatio);
        //telemetry.addData("Blue Ratio", blueRatio);
        //telemetry.addData("Distance", GetDistance());
        if (greenRatio >= 0.375 && greenRatio <= 0.45) {
            return DetectedColor.UNKNOWN;
        }else{

            if (redRatio > 0.22 && greenRatio < 0.4) {
                return DetectedColor.PURPLE;
            } else {
                return DetectedColor.GREEN;
            }
        }
    }

    public double GetDistance(){
        return distance.getDistance(DistanceUnit.CM);
    }

}
