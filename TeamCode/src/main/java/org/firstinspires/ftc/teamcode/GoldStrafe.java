package org.firstinspires.ftc.teamcode;

public class GoldStrafe extends DriveX {

    Object goldMineral;

    public GoldStrafe() {
        super(0,0);
    }

    public void start(Team7593Opmode opmode){

        goldMineral = opmode.getSharedInfo("Gold");

        if((Integer)goldMineral == 1){
            this.endTime = 1.4;
            this.speed = .9;
        }else if((Integer)goldMineral == 3){
            this.endTime = 1.4;
            this.speed = -.9;
        }else{
            this.endTime = 0;
            this.speed = 0.0;
        }

        super.start(opmode);
    }
}
