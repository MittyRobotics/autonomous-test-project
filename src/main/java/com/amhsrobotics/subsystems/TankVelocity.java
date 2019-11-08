package com.amhsrobotics.subsystems;

import com.amhsrobotics.OI;
import com.amhsrobotics.constants.DriveConstants;
import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

public class TankVelocity extends Command {

    private double target;

    public TankVelocity(double target) {
        requires(DriveTrain.getInstance());
        this.target = target;
    }

    @Override
    protected void initialize() {
    }


    @Override
    protected void execute() {
        DriveTrain.getInstance().customTankVelocity(target,target);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
