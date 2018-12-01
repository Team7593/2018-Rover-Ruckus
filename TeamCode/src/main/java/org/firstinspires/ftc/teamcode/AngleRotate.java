package org.firstinspires.ftc.teamcode;

/**
 * Created by vineelavandanapu on 11/20/17.
 *
 * makes the robot rotate the until it reaches a desired angle (you have to set the speed and
 * use the correct negatives. a pain ikr)
 */

public class AngleRotate implements AutonStep {
    public double speed; //speed of the robot
    private float endAngle; //angle the robot should go to
    public boolean enabled;

    double v_lf; //variable that sets the left front wheel power
    double v_lr; //variable that sets the left rear wheel power
    double v_rf; //variable that sets the right front wheel power
    double v_rr; //variable that sets the right rear wheel power

    public final double INITIAL_RANGE = 5.0;

    public double range = INITIAL_RANGE;

    @Override
    public String name() {
        return "Rotate";
    }

    AngleRotate(float angle, double speed){
        this(angle, speed, false);
    }

    AngleRotate(float angle, double speed, boolean enabled){
        this.speed = speed; //set the speed to the given speed
        this.enabled = enabled;
        this.setEndAngle(angle);
    }

    public void setEndAngle(float angle) {
        if(angle > 180) {
            this.endAngle = angle; //set angle to given angle
        } else {
            this.endAngle = angle + 360;
        }
    }

    @Override
    public void start(Team7593Opmode opmode) {

    }

    @Override
    public void loop(Team7593Opmode opmode) {

        double direction = 0; //direction we want the wheels to go (in this case kind of unnecessary)

        //use formulas for mecanum wheels
        v_lf = -(0 * Math.sin(direction + Math.PI/4) + speed);
        v_rf = (0 * Math.cos(direction + Math.PI/4) - speed);
        v_lr = -(0 * Math.cos(direction + Math.PI/4) + speed);
        v_rr = (0 * Math.sin(direction + Math.PI/4) - speed);

        //set motor power
        opmode.robot.motorFrontLeft.setPower(v_lf);
        opmode.robot.motorFrontRight.setPower(v_rf);
        opmode.robot.motorRearLeft.setPower(v_lr);
        opmode.robot.motorRearRight.setPower(v_rr);
    }

    @Override
    public boolean isDone(Team7593Opmode opmode) {
        float currentAngle;

        //condition to normalize the negative angles to be 180-360
        currentAngle = opmode.robot.getCurrentAngle();
        if(currentAngle <= 180) {
            currentAngle += 360;
        }

        if(endAngle - range < currentAngle && currentAngle < endAngle+range){
            opmode.robot.motorFrontLeft.setPower(0);
            opmode.robot.motorFrontRight.setPower(0);
            opmode.robot.motorRearLeft.setPower(0);
            opmode.robot.motorRearRight.setPower(0);

            if(enabled == false) {
                return true;
            }

            if (range == INITIAL_RANGE && Math.abs(endAngle - currentAngle) > 1) {
                range = 1;
                // Change the speed depending on the condition to minimum and direction
                if(currentAngle > endAngle){
                    speed = 0.2;
                }else{
                    speed = -0.2;
                }
                opmode.telemetry.log().add("-------");
                opmode.telemetry.log().add("current angle: " + currentAngle);
                opmode.telemetry.log().add("end angle: " + endAngle);
                opmode.telemetry.log().add("current speed: " + speed);
                opmode.telemetry.log().add("-------");
                return false;
            } else {
                return true;
            }
        }else{
            if(range == 1) {
                if(currentAngle > endAngle){
                    speed = 0.2;
                }else{
                    speed = -0.2;
                }
            }
            return false;
        }
    }

    @Override
    public void updateTelemetry(Team7593Opmode opmode) {
        //set telemetry
        opmode.telemetry.addData("Front Left", v_lf);
        opmode.telemetry.addData("Right Front", v_rf);
        opmode.telemetry.addData("Left Rear", v_lr);
        opmode.telemetry.addData("Right Rear", v_rr);
        opmode.telemetry.addData("target angle", endAngle);
        opmode.telemetry.addData("current angle", opmode.robot.getCurrentAngle());
        opmode.telemetry.addData("speed", speed);

    }

}