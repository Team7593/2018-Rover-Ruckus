package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Extend implements AutonStep {

    int position;

    @Override
    public String name() {
        return "Hang";
    }

    public Extend(int curr, int pos){
        position = curr+pos;
    }

    @Override
    public void start(Team7593Opmode opmode) {
        opmode.robot.extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        opmode.robot.extension.setTargetPosition(position);
    }

    @Override
    public void loop(Team7593Opmode opmode) {
        opmode.robot.extension.setPower(0.95);
    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        if(Math.abs(opmode.robot.extension.getCurrentPosition() - position) <= 60){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {
        opmode.telemetry.addData("position:", opmode.robot.extension.getCurrentPosition());
    }
}
