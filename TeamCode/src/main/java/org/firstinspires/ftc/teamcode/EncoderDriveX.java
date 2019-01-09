package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class EncoderDriveX implements AutonStep{

    public int position;
    public double speed;

    WheelSpeeds wheelSpeeds;

    @Override
    public String name() {
        return "EncoderDriveX";
    }

    public EncoderDriveX(int curr, int pos, double speed){
        position = curr+pos;
        this.speed = speed;
    }

    @Override
    public void start(Team7593Opmode opmode) {
        opmode.robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.robot.motorFrontLeft.setTargetPosition(position);

        wheelSpeeds = WheelSpeeds.mecanumDrive(speed, 0.0, 0.0, false);
    }

    @Override
    public void loop(Team7593Opmode opmode) {
        opmode.robot.motorFrontLeft.setPower(wheelSpeeds.v_lf);
        opmode.robot.motorFrontRight.setPower(wheelSpeeds.v_rf);
        opmode.robot.motorRearLeft.setPower(wheelSpeeds.v_lr);
        opmode.robot.motorRearRight.setPower(wheelSpeeds.v_rr);
    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        if(Math.abs(opmode.robot.motorFrontLeft.getCurrentPosition()-position) <= 60){
            return true;
        }else{
            return false;
        }    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {
        opmode.telemetry.addData("current position", opmode.robot.motorFrontLeft.getCurrentPosition());
    }
}
