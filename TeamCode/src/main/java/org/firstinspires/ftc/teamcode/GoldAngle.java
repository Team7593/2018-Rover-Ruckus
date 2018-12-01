package org.firstinspires.ftc.teamcode;

public class GoldAngle extends AngleRotate{

    Object goldMineral;


    public GoldAngle(){
        super(0, .2, false);
    }

    @Override
    public String name() {
        return "GoldAngle";
    }


    @Override
    public void start(Team7593Opmode opmode) {
        goldMineral = opmode.getSharedInfo("Gold");

        if((Integer)goldMineral == 1){
            this.setEndAngle(117);
        }else if((Integer)goldMineral == 3){
            this.setEndAngle(69);
        }else{
            this.setEndAngle(93);
            this.speed = 0;
        }

        super.start(opmode);
    }


}
