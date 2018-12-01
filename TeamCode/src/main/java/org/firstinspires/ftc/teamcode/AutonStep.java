package org.firstinspires.ftc.teamcode;

/**
 * Created by NDHSB-Emma on 9/26/17.
 *
 *The basis of our Auton Steps
 */

public interface AutonStep {

    public String name(); //use this to return the name of the class that you're using. (eg. DriveX)

    public void start(Team7593Opmode opmode);

    public void loop(Team7593Opmode opmode); //this is the loop where all the code is executed

    public boolean isDone(Team7593Opmode opmode); //the condition that ends the loop

    public void updateTelemetry(Team7593Opmode opmode); //method that will update the telemetry

}