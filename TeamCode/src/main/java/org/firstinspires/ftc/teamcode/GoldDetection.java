package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class GoldDetection implements AutonStep {

    //strings google needs ig
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    //key
    private static final String VUFORIA_KEY = "AQQ2oEX/////AAABmRhglvRgY03kixkUDOfRK4OB/Wl2Cq/lViWatt49JItTvIef39HtYxno77KU7POLMXgLnNBHqczE+7f77oej6SgdZFcejqj2O532qs80h1orf0qXXUMfiw/ZGLPozBnCec3h2v44KkGZwzekYjBlQQsedUiOdP0CgFpkR5Bxc7TiTCwCI94tKl4QgXTQZ84TWpT3lypUBBskpFbXgc4glmDnwejJ9CcKd7R/lWAhJcw/4u2ypuRoh+ngU//1xjkXJ02IPbh8YmkWy8LyhbWPVAwTT/+hz0+DH546x4m+TLc1N9AaEvYRgnYqpbfH8HhILRc3lNixb/8EKYFZ6dB2bLa74MUonpoy092zths61eRb";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    int goldMineral;

    GoldDetection(){
        goldMineral = -1;
    }

    @Override
    public String name() {
        return "Gold Detection";
    }

    @Override
    public void start(Team7593Opmode opmode) {

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            //init tfod didn't work because we don't pass opmode in a step
            int tfodMonitorViewId = opmode.hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", opmode.hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        } else {
            opmode.telemetry.log().add("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }
    }

    @Override
    public void loop(Team7593Opmode opmode) {

        if (tfod != null) {

            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

            if (updatedRecognitions != null) { //got new information = robot moved
                opmode.telemetry.addData("# Object Detected ",updatedRecognitions.size());


                if (updatedRecognitions.size() == 2) {

                    int goldMineralX = -1; //don't have any positions yet
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;

                    for (Recognition recognition : updatedRecognitions) { //looking through list of found objects
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }

                    if(goldMineralX == -1){
                        opmode.telemetry.addData("Gold Mineral Position", "Right");
                        goldMineral = 3; //right
                    }else if(goldMineralX < silverMineral1X){
                        opmode.telemetry.addData("Gold Mineral Position", "Left");
                        goldMineral = 1; //left
                    }else{
                        opmode.telemetry.addData("Gold Mineral Position", "Center");
                        goldMineral = 2;
                    }

                }
                opmode.telemetry.update();
                opmode.shareInfo("Gold", goldMineral); //share what position it is
            }
        }
    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        if(goldMineral != -1){

            if (tfod != null) {
                tfod.shutdown();
            }
            return true;

        }else if(tfod == null){
            opmode.shareInfo("Gold", 2);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {

    }


    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
//    private void initTfod() {
//        int tfodMonitorViewId = opmode.robot.hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", opmode.robot.hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
//    }
}
