package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AutoConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.Hood;
import org.firstinspires.ftc.teamcode.Mechanisms.IntakeConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.KickConfig;
import org.firstinspires.ftc.teamcode.Mechanisms.Shooter;
import org.firstinspires.ftc.teamcode.Mechanisms.Turret;
@Autonomous
public class BlueAutoFar extends LinearOpMode {
    private AutoConfig robot = new AutoConfig(this);
    Shooter shooter = new Shooter();
    KickConfig kick = new KickConfig();
    int step = -1;
    int delay = 400;
    ElapsedTime kickTimer = new ElapsedTime();
    IntakeConfig intake = new IntakeConfig();
    Turret turret = new Turret();
    Hood hood = new Hood();
    ElapsedTime timer =  new ElapsedTime();


    public void autoKick() {
        if (step == -1) return;
        switch (step) {
            case 0:
                kick.kickOne();
                kickTimer.reset();
                step = 1;
                break;
            case 1:
                if (kickTimer.milliseconds() >= delay) {
                    kick.retractOne();
                    step = 2;
                }
                break;
            case 2:
                kick.kickTwo();
                kickTimer.reset();
                step = 3;
                break;
            case 3:
                if (kickTimer.milliseconds() >= delay) {
                    kick.retractTwo();
                    step = 4;
                }
                break;
            case 4:
                kick.kickThree();
                kickTimer.reset();
                step = 5;
                break;
            case 5:
                if (kickTimer.milliseconds() >= delay) {
                    kick.retractThree();
                    step = -1;
                }
                break;


        }

    }
    @Override
    public void runOpMode(){
        robot.initialize(false);
        shooter.init(hardwareMap);
        kick.init(hardwareMap);
        intake.init(hardwareMap);
        turret.init(hardwareMap);
        hood.init(hardwareMap);

        waitForStart();

        robot.drive(-40,0.5,1.0);

//        turret.setPower(0.0);
//        autoKick();
//        hood.hoodHigh();
//        shooter.shooterFar();
//        step = 0;
//        robot.strafe(-36, 0.5 , 0.5);
//        step = -1;
//        intake.IntakeMotorMax();
//        robot.drive(65,0.5,1.0);
//        robot.drive(-65,0.5,0.5);
//        robot.strafe(36,0.5,0.5);
//        step = 0;
//        robot.strafe(-72,1.0,0.5);
//        step = -1;
//        robot.drive(65,0.5,1.0);
//        robot.drive(-65,0.5,0.5);
//        robot.strafe(72,0.5,0.5);
//        step = 0;
//        robot.strafe(-108,1.0,0.5);
//        robot.drive(65,0.5,1.0);
//        robot.drive(-65,0.5,0.5);
//        robot.strafe(108,1.0,0.5);
//        step = 0;
//        robot.strafe(-10,1.0,0.0);
//        step = -1;




    }
}
