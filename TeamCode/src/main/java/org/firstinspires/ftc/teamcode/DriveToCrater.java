package org.firstinspires.ftc.teamcode;

public class DriveToCrater extends DriveY {

    Object goldMineral;

    DriveToCrater() {
        super(2.2, -1);
    }

    @Override
    public void start(Team7593Opmode opmode) {
        goldMineral = opmode.getSharedInfo("Gold");

        if((Integer)goldMineral == 1){
            this.endTime = 1.9;
        }else if((Integer)goldMineral == 2){
            this.endTime = 1.7;
        }

        super.start(opmode);
    }

}
