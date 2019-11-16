package com.amhsrobotics;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TKOSubsystemTest extends TKOSubsystem{
	private WPI_TalonSRX talon;
	
	@Override
	public void setMotor(double percentOutput) {
		talon.set(ControlMode.PercentOutput,percentOutput);
	}
	
	@Override
	public double getPosition() {
		return talon.getSelectedSensorPosition();
	}
	
	@Override
	public double getVelocity() {
		return talon.getSelectedSensorVelocity();
	}
	
	@Override
	public double getMaxAcceleration() {
		return 10;
	}
	
	@Override
	public double getMaxDeceleration() {
		return 10;
	}
	
	@Override
	public double getMaxVelocity() {
		return 100;
	}
	
	@Override
	public double getKp() {
		return 0.2;
	}
	
	@Override
	public double getKi() {
		return 0.0;
	}
	
	@Override
	public double getKd() {
		return 0.0;
	}
	
	@Override
	public void initHardware() {
		talon = new WPI_TalonSRX(0);
	}
	
	@Override
	protected void initDefaultCommand() {
	
	}
}
