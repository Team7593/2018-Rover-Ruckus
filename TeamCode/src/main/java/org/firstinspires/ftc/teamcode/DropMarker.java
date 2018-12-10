package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class DropMarker implements AutonStep {

    public ElapsedTime time = new ElapsedTime();

    @Override
    public String name() {
        return "Drop Marker";
    }

    @Override
    public void start(Team7593Opmode opmode) {
        time.startTime();
        time.reset();
    }

    @Override
    public void loop(Team7593Opmode opmode) {
        opmode.robot.spinMotor.setPower(.7);

    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        if(time.time() > 1.3){
            opmode.robot.spinMotor.setPower(0);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {
        opmode.telemetry.addData("time", time.time());
    }
}
