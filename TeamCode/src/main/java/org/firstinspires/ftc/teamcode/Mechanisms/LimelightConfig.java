package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimelightConfig {

    private Limelight3A limelight;

    Turret turret = new Turret();


    private final double Kp = 0.03;
    private final double minPower = 0.5;
    private final double tolerance = 0.25;
    private final double searchPowerR = 1.0;

    private final double searchPowerL = -1.0;

    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        turret.init(hwMap);

        limelight.pipelineSwitch(0);
        limelight.start();
    }

    private LLResult getSafeResult() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;
        return result;
    }

    public boolean hasTarget() {
        LLResult result = getSafeResult();
        return result != null &&
                result.getFiducialResults() != null &&
                !result.getFiducialResults().isEmpty();
    }


    public double getTx() {
        LLResult result = getSafeResult();
        if (result == null) return 0;
        return result.getTx();
    }


    public void alignTurretRight(boolean buttonHeld) {

        // Button not held → turret OFF
        if (!buttonHeld) {
            turret.setPower(0);
            return;
        }

        if (!hasTarget()) {
            turret.setPower(searchPowerR);
            return;
        }

        double tx = getTx();

        if (Math.abs(tx) <= tolerance) {
            turret.setPower(0);
            return;
        }

        double power = tx * Kp;

        if (Math.abs(power) < minPower) {
            power = Math.copySign(minPower, power);
        }
        power = Math.max(-1.0, Math.min(1.0, power));

        turret.setPower(power);
    }

    public void alignTurretLeft(boolean buttonHeld) {

        // Button not held → turret OFF
        if (!buttonHeld) {
            turret.setPower(0);
            return;
        }

        if (!hasTarget()) {
            turret.setPower(searchPowerL);
            return;
        }

        double tx = getTx();

        if (Math.abs(tx) <= tolerance) {
            turret.setPower(0);
            return;
        }

        double power = tx * Kp;

        if (Math.abs(power) < minPower) {
            power = Math.copySign(minPower, power);
        }
        power = Math.max(-1.0, Math.min(1.0, power));

        turret.setPower(power);
    }
}


