package com.amhsrobotics.autonomous.commands;

import com.amhsrobotics.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CommandGroupForTesting extends CommandGroup {
    public CommandGroupForTesting(){
        addSequential(new MotionProfileTranslate(48));
        addSequential(new MotionProfileTranslate(-48));
    }
}
