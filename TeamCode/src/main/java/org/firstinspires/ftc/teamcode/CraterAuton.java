package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

@Autonomous (name = "crater")
public class CraterAuton extends Team7593Opmode {

    int currEncoderVal;

    public void init() {
        super.init();

        //stop the motor(s) and reset the motor encoders to 0
//        robot.tiltMotor.setPower(0);
//        robot.tiltMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.tiltMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.hangMotor.setPower(0);
        robot.hangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.hangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        currEncoderVal = robot.hangMotor.getCurrentPosition();
    }

    public ArrayList<AutonStep> createAutonSteps() {

        ArrayList<AutonStep> steps = new ArrayList<>();

        steps.add(new Hang(currEncoderVal, -4700)); //get off the lander

        steps.add(new AngleRotate(270, .4)); //get off hook
        steps.add(new DriveY(.45, .65));
        steps.add(new AngleRotate(0, -.4));
        steps.add(new DriveX(.4, .7)); //and then drive back to get a good view of the minerals

        steps.add(new GoldDetection()); //detect duh

        steps.add(new AngleRotate(275, .4)); //rotate to face the blocks
        steps.add(new DriveY(.55, .6)); //drive forward to to get past the lander legs
        steps.add(new DriveX(.2, .75)); //drive to be in the middle

        steps.add(new GoldStrafe()); //strafe to the side to line up with the gold
        steps.add(new DriveY(.6, .95)); //drive forward and knock the right mineral off
        steps.add(new DriveY(.50, -.95));//drive backward; makes sure we are clear when we turn

        steps.add(new DriveX(1.5, .95)); //drive to the wall
        steps.add(new ToWall()); //drive more if you're further away

        steps.add(new AngleRotate(25, -.7)); //turn to line up with the wall
        steps.add(new DriveX(.9, -.85)); //ram into the wall

        steps.add(new DriveY(1.35, .85));
        steps.add(new DropMarker());

        steps.add(new DriveY(2.0, -.95));
        steps.add(new Tilt(1.5, .75));
        return steps;
    }
}
