package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimelightConfig {

    private Limelight3A limelight;
    private Turret turret;

    // PD control constants
    private final double Kp = 0.05;   // proportional gain
    private final double Kd = 0.02;   // derivative gain

    private final double tolerance = 1.0; // degrees within which turret stops
    private final double searchPower = 0.2; // power used when searching

    private double lastTx = 0; // previous error for derivative

    public LimelightConfig() {
        turret = new Turret();
    }

    // Initialize hardware
    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
        turret.init(hwMap);

        limelight.pipelineSwitch(0);
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
    public void alignTurret(boolean buttonHeld) {

        if (!buttonHeld) {
            turret.setPower(0);
            lastTx = 0;
            return;
        }

        if (!hasTarget()) {
            // No target: rotate slowly to search
            turret.setPower(searchPower);
            lastTx = 0;
            return;
        }

        double tx = getTx();

        // Stop if within tolerance
        if (Math.abs(tx) <= tolerance) {
            turret.setPower(0);
            lastTx = 0;
            return;
        }

        // PD control: proportional + derivative
        double dTx = tx - lastTx;
        double power = Kp * tx + Kd * dTx;

        // Clamp to -1 to 1
        power = Math.max(-1, Math.min(1, power));

        turret.setPower(power);
        lastTx = tx;
    }
}
