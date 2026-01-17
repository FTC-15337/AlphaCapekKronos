package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimelightConfig {

    private Limelight3A limelight;
    private Turret  turret = new Turret();
    double pValue = 0.015;
    private final double tolerance = 0.5;

    public void redInit(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        turret.init(hwMap);

        limelight.pipelineSwitch(0);
        limelight.start();
    }
    public void blueInit(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        turret.init(hwMap);

        limelight.pipelineSwitch(8);
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

    public void alignR() {
        if (!hasTarget()) {
            turret.setPower(0.25);
            return;
        }

        double tx = limelight.getLatestResult().getTx();

        double power = tx * pValue;

        power = Math.max(-0.4, Math.min(0.4, power)); // clamp

        if(Math.abs(tx) <= tolerance){
            turret.Stop();
        }

        turret.setPower(power);
    }

    public void alignL() {
        if (!hasTarget()) {
            turret.setPower(-0.25);
            return;
        }

        double tx = limelight.getLatestResult().getTx();

        double power = tx * pValue;

        if(Math.abs(tx) <= tolerance){
            turret.Stop();
        }

        power = Math.max(-0.4, Math.min(0.4, power)); // clamp

        turret.setPower(power);
    }
}
