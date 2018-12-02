package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

@Autonomous (name = "depot")
public class DepotAuton extends Team7593Opmode {

    int currEncoderVal;

    @Override
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

    @Override
    public ArrayList<AutonStep> createAutonSteps() {

        ArrayList<AutonStep> steps = new ArrayList<>();
        steps.add(new Hang(currEncoderVal, 4540));
        steps.add(new DriveY(.25, -.4));
        steps.add(new DriveX(.25, .4));
        steps.add(new DriveY(.5, .4)); //get off the hook
        steps.add(new AngleRotate(92, -.45)); //angle yourself so the camera sees the minerals
        steps.add(new DriveY(.35, -.4)); //drive back
        steps.add(new GoldDetection()); //detect duh
        steps.add(new DriveY(.58, .6)); //drive forward to see
        steps.add(new GoldStrafe()); //strafe to the side to go to the gold
        //steps.add(new GoldAngle());
        steps.add(new DriveY(1.15, .85)); //drive forward and knock the right mineral off
        steps.add(new AngleRotate(135, -.4)); //rotate to line up with the wall
        steps.add(new DriveX(1.7, -.9)); //strafe to reach wall
        //steps.add(new DepotAngle());
        steps.add(new StrafeOrNotToStrafe()); //strafe if you need to b
        steps.add(new DriveY(2.2, -1)); //drive to the crater

        return steps;
    }
}
