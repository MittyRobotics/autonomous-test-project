package com.amhsrobotics;

import com.amhsrobotics.purepursuit.VelocityConstraints;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TKOSubsystem extends Subsystem {
	public abstract void setMotor(double percentOutput);
	public abstract double getPosition();
	public abstract double getVelocity();
	public abstract double getMaxAcceleration();
	public abstract double getMaxDeceleration();
	public abstract double getMaxVelocity();
	public abstract double getKp();
	public abstract double getKi();
	public abstract double getKd();
	public abstract void initHardware();
}
