package org.firstinspires.ftc.teamcode;

public class DriveToDepot extends DriveY {

    Object goldMineral;

    DriveToDepot() {
        super(0, .7);
    }

    @Override
    public void start(Team7593Opmode opmode) {
        goldMineral = opmode.getSharedInfo("Gold");

        if((Integer)goldMineral == 2){
            this.endTime = .25;
        }else if((Integer)goldMineral == 3){
            this.endTime = .75;
        }

        super.start(opmode);
    }

}
