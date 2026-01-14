package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Limelight Distance")
public class LimelightTest extends LinearOpMode {

    private Limelight3A limelight;

    // Constants: Update these for your 2026 robot and field setup
    final double MOUNT_ANGLE = 25.0;
    final double LENS_HEIGHT = 14.5;
    final double TARGET_HEIGHT = 29.5;
    //final double OFFSET = 20?;

    @Override
    public void runOpMode() {
        // Initialize Limelight from Hardware Map
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // Ensure AprilTag pipeline is active
        limelight.start();

        waitForStart();

        while (opModeIsActive()) {
            // Get the latest result directly from the sensor object
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                double ty = result.getTy(); // Vertical offset from crosshair

                // Calculate horizontal llDistance: d = (h2 - h1) / tan(a1 + a2)
                double angleToGoalRadians = Math.toRadians(MOUNT_ANGLE + ty);
                double llDistance = (TARGET_HEIGHT - LENS_HEIGHT) / Math.tan(angleToGoalRadians);
                double actualDistance = 1.365 * llDistance - 14.01024;

                telemetry.addData("Target Found", "ID: " + result.getBotpose());
                telemetry.addData("Horizontal Distance", "%.2f inches", llDistance);
                telemetry.addData("Ty", ty);
                telemetry.addData("Actual Distance is ", "%.2f inches", actualDistance);
            } else {
                telemetry.addData("Status", "No Target Detected");
            }
            telemetry.update();
        }
    }
}