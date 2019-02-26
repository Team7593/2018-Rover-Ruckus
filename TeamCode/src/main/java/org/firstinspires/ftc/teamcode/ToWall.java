package org.firstinspires.ftc.teamcode;

public class ToWall extends DriveX {

    Object goldMineral;

    public ToWall() {
        super(0, 0);
    }

    public void start(Team7593Opmode opmode){

        goldMineral = opmode.getSharedInfo("Gold");

        if((Integer)goldMineral == 2){
            this.endTime = .85;
            this.speed = .95;
        }else if((Integer)goldMineral == 3){
            this.endTime = 1.45;
            this.speed = .95;
        }else{
            this.endTime = 0;
            this.speed = 0.0;
        }

        super.start(opmode);
    }
}
