package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hang implements AutonStep {


    int position;

    @Override
    public String name() {
        return "Hang";
    }

    public Hang(int curr, int pos){
        position = curr+pos;
    }

    @Override
    public void start(Team7593Opmode opmode) {
        opmode.robot.hangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.robot.hangMotor.setTargetPosition(position);
    }

    @Override
    public void loop(Team7593Opmode opmode) {
        opmode.robot.hangMotor.setPower(0.6);
    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        if(Math.abs(opmode.robot.hangMotor.getCurrentPosition() - position) <= 60){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {
        opmode.telemetry.addData("position:", opmode.robot.hangMotor.getCurrentPosition());
    }
}
