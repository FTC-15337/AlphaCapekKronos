package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Hood;
import org.firstinspires.ftc.teamcode.Mechanisms.KickConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "Blue Auto Back")
public class BlueAutoBack extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;
    ElapsedTime kickTimer = new ElapsedTime();

    private KickConfig kick = new KickConfig();
    private Hood hood = new Hood();
    private Shooter shooter = new Shooter();

    public enum PathState {
        // START POSITION_END POSITION
        // DRIVE > MOVEMENT STATE
        // SHOOT > ATTEMPT TO SCORE THE ARTIFACT
        SHOOT_PRELOAD,
        DRIVE_START_POS_LEAVE,
        STOP
    }

    PathState pathState;

    private final Pose startPose = new Pose(57.2, 9, Math.toRadians(90));
    private final Pose leavePos = new Pose(13.600482509047042, 9, Math.toRadians(90));

    private PathChain driveStartPosLeavePos;

    public void buildPaths() {
        // put in coordinates for starting pose > ending pose
        driveStartPosLeavePos = follower.pathBuilder()
                .addPath(new BezierLine(startPose, leavePos))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
    }


    public int stepN = -1;
    public void autoKick() {
        if (stepN == -1) return;

        switch (stepN) {
            case 0:
                kick.kickOne();
                kickTimer.reset();
                stepN = 1;
                break;

            case 1:
                if (kickTimer.milliseconds() >= 700) {
                    kick.retractOne();
                    stepN = 2;
                }
                break;

            case 2:
                kick.kickTwo();
                kickTimer.reset();
                stepN = 3;
                break;

            case 3:
                if (kickTimer.milliseconds() >= 700) {
                    kick.retractTwo();
                    stepN = 4;
                }
                break;

            case 4:
                kick.kickThree();
                kickTimer.reset();
                stepN = 5;
                break;

            case 5:
                if (kickTimer.milliseconds() >= 700) {
                    kick.retractThree();
                    stepN = -1;
                }
                break;
        }
    }


    public void statePathUpdate() {
        switch(pathState) {
            case SHOOT_PRELOAD:
                if (pathTimer.getElapsedTimeSeconds() > 4.0) {
                    stepN = 0;
                    telemetry.addLine("Firing");
                    setPathState(PathState.DRIVE_START_POS_LEAVE); // reset timer & transition to new state
                }
                break;
            case DRIVE_START_POS_LEAVE:
                // check is follower done its path
                if(!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5.0) {
                    follower.setMaxPower(1.0);
                    follower.followPath(driveStartPosLeavePos, true);
                    setPathState(PathState.STOP);
                }
                break;
            default:
                telemetry.addLine("No state commanded");
                break;
        }
    }

    public void setPathState(PathState newState) {
        pathState = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathState = PathState.SHOOT_PRELOAD;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);

        kick.init(hardwareMap);
        hood.init(hardwareMap);
        shooter.init(hardwareMap);

        buildPaths();
        follower.setPose(startPose);
    }

    @Override
    public void start() {
        shooter.shooterFar();
        hood.hoodHigh();
        opModeTimer.resetTimer();
        setPathState(pathState);
    }

    @Override
    public void loop() {
        follower.update();
        statePathUpdate();

        autoKick();

        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
    }
}