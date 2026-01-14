package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimelightConfig {

    private Limelight3A limelight;
    private Turret  turret = new Turret();

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


    // Get a safe Limelight result
    private LLResult getSafeResult() {
        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) return null;
        return result;
    }

    // Check if a fiducial (AprilTag) is visible
    public boolean hasTarget() {
        LLResult result = getSafeResult();
        return result != null &&
                result.getFiducialResults() != null &&
                !result.getFiducialResults().isEmpty();
    }

    // Get horizontal offset to target
    public double getTx() {
        LLResult result = getSafeResult();
        if (result == null) return 0;
        return result.getTx();
    }

    // Align turret to the AprilTag using PD control

    public void alignR() {
        if (!hasTarget()) {
            turret.setPower(0.25);
            return;
        }

        double tx = limelight.getLatestResult().getTx();

        double kP = 0.015;
        double power = tx * kP;

        power = Math.max(-0.4, Math.min(0.4, power)); // clamp

        if(Math.abs(tx) <= 0.5){
            turret.setPower(0.0);
        }

        turret.setPower(power);
    }

    public void alignL() {
        if (!hasTarget()) {
            turret.setPower(-0.25);
            return;
        }

        double tx = limelight.getLatestResult().getTx();

        double kP = 0.015;
        double power = tx * kP;

        if(Math.abs(tx) <= 0.5){
            turret.setPower(0.0);
        }

        power = Math.max(-0.4, Math.min(0.4, power)); // clamp

        turret.setPower(power);
    }


}
