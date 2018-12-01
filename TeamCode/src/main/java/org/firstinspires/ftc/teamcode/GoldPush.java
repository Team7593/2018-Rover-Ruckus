package org.firstinspires.ftc.teamcode;

public class GoldPush implements AutonStep {

    Object gold;

    @Override
    public String name() {
        return "Gold Push";
    }

    @Override
    public void start(Team7593Opmode opmode) {
        gold = opmode.getSharedInfo("Gold");
    }

    @Override
    public void loop(Team7593Opmode opmode) {

    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        return false;
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {

    }
}
