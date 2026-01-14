package org.firstinspires.ftc.teamcode.Mechanisms;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@TeleOp(name = "PP")
public class PinpointTest extends LinearOpMode {
    MecDrivebase drive = new MecDrivebase();
    Shooter shooter = new Shooter();
    Hood hood = new Hood();

    GoBildaPinpointDriver pinpoint;

    double forward, strafe, rotate;

    @Override
    public void runOpMode() throws InterruptedException {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        pinpoint.setOffsets(-4, -6.5, DistanceUnit.INCH);

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        pinpoint.resetPosAndIMU();

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));

        drive.init(hardwareMap);
        shooter.init(hardwareMap);
        hood.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            pinpoint.update();

            Pose2D pose2D = pinpoint.getPosition();

            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;
            drive.driveFieldRelative(forward, strafe, rotate);

            if (gamepad1.dpad_up) {
                drive.imu.resetYaw();
            }

            if (gamepad1.dpad_down) {
                pinpoint.resetPosAndIMU();
            }

            double distance = Math.sqrt((pose2D.getX(DistanceUnit.INCH) - 1) * (pose2D.getX(DistanceUnit.INCH) - 1) + (pose2D.getY(DistanceUnit.INCH) - 59) * (pose2D.getY(DistanceUnit.INCH) - 59));

            if (gamepad1.right_trigger >= 0.7) {
                shooter.setVelocity(875.25982 * Math.pow(1.00377, distance));
                hood.setHood(0.00396959 * distance + 0.166253);
            } else if (gamepad1.a) {
                shooter.setVelocity(1380);
                hood.setHood(0.4);
            } else {
                shooter.shooterStop();
            }

            telemetry.addData("Distance is (INCH)", distance);

            telemetry.addData("X Coordinate (INCH)", pose2D.getX(DistanceUnit.INCH));
            telemetry.addData("Y Coordinate (INCH)", pose2D.getY(DistanceUnit.INCH));
            telemetry.addData("Heading Angle (DEGREES)", pose2D.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
    }
}
