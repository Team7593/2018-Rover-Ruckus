package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.ArrayList;

@Autonomous (name = "regression test")
public class RegressionTest extends Team7593Opmode {
    @Override
    public ArrayList<AutonStep> createAutonSteps() {
        ArrayList<AutonStep> steps = new ArrayList<>();
        steps.add(new DriveY(1, .75));

        return steps;
    }
}
