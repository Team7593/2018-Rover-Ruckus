package org.firstinspires.ftc.teamcode;

public class DepotAngle extends AngleRotate {

    Object goldMineral;

    public DepotAngle(){
        super(0, .2, false);
    }

    @Override
    public void start(Team7593Opmode opmode) {

        goldMineral = opmode.getSharedInfo("Gold");

        if((Integer)goldMineral == 1){
            this.setEndAngle(60);
        }else if((Integer)goldMineral == 3){
            this.setEndAngle(30);
        }else{
            this.setEndAngle(45);
        }

        super.start(opmode);
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode){
        opmode.telemetry.log().add("gold:"+ goldMineral);

        super.updateTelemetry(opmode);
    }
}
