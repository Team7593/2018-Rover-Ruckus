package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Tilt implements AutonStep {

    public ElapsedTime time; //way to see the time
    public double endTime; //time when the movement should end
    double power;

    public Tilt(double time, double power){
        this.power = power;
        this.endTime = time;
    }

    @Override
    public String name() {
        return "tilt";
    }


    @Override
    public void start(Team7593Opmode opmode) {
        time = new ElapsedTime();
    }

    @Override
    public void loop(Team7593Opmode opmode) {
        opmode.robot.tiltMotor.setPower(power);
    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        if(time.time() > endTime ){
            opmode.robot.tiltMotor.setPower(0);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {
        opmode.telemetry.addData("tilt motor speed", opmode.robot.tiltMotor.getPower());
    }
}
