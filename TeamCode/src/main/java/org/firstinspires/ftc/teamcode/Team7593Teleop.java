package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;

@TeleOp (name = "TeleOp")
public class Team7593Teleop extends Team7593Opmode {

    //Declare Variables
    public ElapsedTime time = new ElapsedTime(); //a timer

    public int currEncoderVal;  //encoder values for tilt motor
    public int oldEncoderVal;

    public int cEncoderVal;  //encoder values for tilt motor
    public int oEncoderVal;

    Orientation angles; //to use the imu (mostly for telemetry)

    double extensionPosition = robot.EXTENSION_HOME;
    final double EXTENSION_SPEED = 0.08; //sets rate to move servo

    @Override
    //code block to that will run at the VERY beginning of Teleop
    public ArrayList<AutonStep> createAutonSteps() {
        return null;
    }

    @Override
    public void init(){
        super.init();

        //stop the motor(s) and reset the motor encoders to 0
        robot.tiltMotor.setPower(0);
        robot.tiltMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.tiltMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.extension.setPower(0);
        robot.extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //this declaration is kind of unnecessary but that's fine
//        robot.hangMotor.setPower(0);
//        robot.hangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.hangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //get the starting encoder value of tilt (this is so we don't assume the starting econder value is 0)
        oldEncoderVal = robot.tiltMotor.getCurrentPosition();
        oEncoderVal = robot.extension.getCurrentPosition();

        telemetry.addData("Say", "HELLO FROM THE OTHER SIIIIIDE");
        time.startTime();
    }

    public void loop() {

        //use super's loop so that auton steps can run
        super.loop();

        //get the current encoder value of tilt
        currEncoderVal = robot.tiltMotor.getCurrentPosition();
        cEncoderVal = robot.extension.getCurrentPosition();

        double leftX, rightX, leftY, hangStick, tiltStick, tiltPower; //declaration for the game sticks + power
        boolean slowTilt, spinIn, spinOut, slowDrive; //declaration for the buttons/bumpers
        WheelSpeeds speeds; //variable to hold speeds

        leftX = gamepad1.left_stick_x;
        rightX = gamepad1.right_stick_x;
        leftY = gamepad1.left_stick_y;
        slowDrive = gamepad1.left_bumper;

        hangStick = gamepad2.right_stick_y;
        tiltStick = gamepad2.left_stick_y;
        slowTilt = gamepad2.a;
        spinOut = gamepad2.right_bumper;
        spinIn = gamepad2.left_bumper;
        tiltPower = .3;

        //JUST FOR TESTING
//        if(gamepad2.x){
//            ArrayList<AutonStep> steps = new ArrayList<>();
//            steps.add(new GoldDetection());
//            telemetry.addData("gold step", "added it");
//            this.newDriverAssist(steps);
//        }

        //get the speeds
        if(slowDrive){
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, true);
        }else{
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, false);
        }

        //power the motors
        robot.powerTheWheels(speeds);

        //power the hang motor
        robot.hangMotor.setPower(hangStick);

        //slow the tilt motor
        if(slowTilt){
            tiltPower = tiltPower/3;
        }

        //recursive encoder loop to the keep the tilt motor still-ish
        if (tiltStick > 0) {
            if (robot.tiltMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.tiltMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.tiltMotor.setPower(tiltPower);
            oldEncoderVal = currEncoderVal;
        } else if (tiltStick < 0) {
            if (robot.tiltMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.tiltMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.tiltMotor.setPower(-tiltPower);
            oldEncoderVal = currEncoderVal;
        } else {
            if (robot.tiltMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.tiltMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            robot.tiltMotor.setTargetPosition(oldEncoderVal);
            robot.tiltMotor.setPower(0.1);
        }


        //code to spin the small motor
        if(spinOut){
            robot.spinMotor.setPower(-.7);
        }else if(spinIn){
            robot.spinMotor.setPower(.7);
        }else{
            robot.spinMotor.setPower(0.0);
        }

        //extension

//        if(gamepad2.dpad_up){
//            robot.extension.setPower(.5);
//        }else if(gamepad2.dpad_down){
//            robot.extension.setPower(-.5);
//        }

        //recursive encoder loop to the keep the extension motor still-ish
        if (gamepad2.dpad_up) {
            if (robot.extension.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.extension.setPower(.5);
            oEncoderVal = cEncoderVal;
        } else if (gamepad2.dpad_down) {
            if (robot.extension.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.extension.setPower(-.5);
            oEncoderVal = cEncoderVal;
        } else {
            if (robot.extension.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            robot.extension.setTargetPosition(oEncoderVal);
            robot.extension.setPower(0.1);
        }


//        if(gamepad2.dpad_up){
//            extensionPosition += EXTENSION_SPEED;
//        }else if(gamepad2.dpad_down){
//            extensionPosition -= EXTENSION_SPEED;
//        }
//
//        extensionPosition = Range.clip(extensionPosition, robot.EXTENSION_MIN, robot.EXTENSION_MAX);
        //robot.extension.setPosition(extensionPosition);


        //use the imu
        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        /*
        WHOA TELEMETRY
        */
        telemetry.addData("hang encoder val:", robot.hangMotor.getCurrentPosition());
        telemetry.addData("extension encoder val:", robot.extension.getCurrentPosition());
        //telemetry.addData("extension value:", extensionPosition);

        //angles on each of the axes
        telemetry.addLine().addData("IMU angle Y:", angles.secondAngle);
        telemetry.addData("IMU angle Z:", angles.firstAngle);
        telemetry.addData("IMU angle X:", angles.thirdAngle);

        //angles it's at
        telemetry.addLine();
        telemetry.addData("Current Angle: ", robot.getCurrentAngle());
        telemetry.addData("Init Angle: ", robot.initAngle);

        //gold pos should be either 1,2,3
        telemetry.addData("Gold Mineral Position:", this.getSharedInfo("Gold"));
    }
}
