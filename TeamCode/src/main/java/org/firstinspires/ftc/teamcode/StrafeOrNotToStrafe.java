package org.firstinspires.ftc.teamcode;

public class StrafeOrNotToStrafe extends DriveX {

    Object goldMineral;

    public StrafeOrNotToStrafe(){
        super(0,.95);
    }

    @Override
    public void start(Team7593Opmode opmode) {
        goldMineral = opmode.getSharedInfo("Gold");

        if((Integer)goldMineral == 2){
            this.endTime = .95;
        }else if((Integer)goldMineral == 3){
            this.endTime = 1.85;
        }

        super.start(opmode);
    }
}
