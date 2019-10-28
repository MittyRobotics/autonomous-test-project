package com.amhsrobotics.subsystems;

import com.amhsrobotics.autonomous.commands.TankDrive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static com.amhsrobotics.constants.DriveConstants.*;


public class DriveTrain extends Subsystem {
	private WPI_TalonSRX[] leftDrive = new WPI_TalonSRX[LEFT_DRIVE_TALON_ID.length];
	private WPI_TalonSRX[] rightDrive = new WPI_TalonSRX[RIGHT_DRIVE_TALON_ID.length];

	public static DriveTrain ourInstance = new DriveTrain();

	public static DriveTrain getInstance(){
		return ourInstance;
	}

	private DriveTrain(){
		super("DriveTrain");
	}

	public void initHardware() {
		for (int i = 0; i < leftDrive.length; i++) {
			WPI_TalonSRX talonSRX = new WPI_TalonSRX(LEFT_DRIVE_TALON_ID[i]);
			talonSRX.configFactoryDefault();
			talonSRX.setInverted(LEFT_DRIVE_TALON_INVERSION[i]);
			talonSRX.setNeutralMode(NeutralMode.Brake);
			if (i == 0) {
				talonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
				talonSRX.setSensorPhase(LEFT_DRIVE_ENCODER_INVERSION);
				talonSRX.config_kP(0,DRIVE_PID[0]);
				talonSRX.config_kI(0,DRIVE_PID[1]);
				talonSRX.config_kD(0,DRIVE_PID[2]);
				talonSRX.setSelectedSensorPosition(0);
			}
			leftDrive[i] = talonSRX;
		}
		for (int i = 0; i < rightDrive.length; i++) {
			WPI_TalonSRX talonSRX = new WPI_TalonSRX(RIGHT_DRIVE_TALON_ID[i]);
			talonSRX.configFactoryDefault();
			talonSRX.setInverted(RIGHT_DRIVE_TALON_INVERSION[i]);
			talonSRX.setNeutralMode(NeutralMode.Brake);
			if (i == 0) {
				talonSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
				talonSRX.setSensorPhase(RIGHT_DRIVE_ENCODER_INVERSION);
				talonSRX.config_kP(0, DRIVE_PID[0]);
				talonSRX.config_kI(0, DRIVE_PID[1]);
				talonSRX.config_kD(0, DRIVE_PID[2]);
				talonSRX.setSelectedSensorPosition(0);
			}
			rightDrive[i] = talonSRX;
		}
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TankDrive());
	}

	public void tankDrive(double left, double right) {
		tankDrive(left, right, 0.5);
	}

	public void tankDrive(double left, double right, final double percentCap) {
		left *= percentCap;
		right *= percentCap;

		if (Math.abs(left) < 0.1) {
			leftDrive[0].set(ControlMode.PercentOutput, 0);
			leftDrive[1].set(ControlMode.PercentOutput, 0);
		} else {
			leftDrive[0].set(ControlMode.PercentOutput, left);
			leftDrive[1].set(ControlMode.PercentOutput, left);
		}
		if (Math.abs(right) < 0.1) {
			rightDrive[0].set(ControlMode.PercentOutput, 0);
			rightDrive[1].set(ControlMode.PercentOutput, 0);
		} else {
			rightDrive[0].set(ControlMode.PercentOutput, right);
			rightDrive[1].set(ControlMode.PercentOutput, right);
		}
	}

	public void tankVelocity(double left, double right) {
		left *= DRIVE_TICKS_PER_INCH/10;
		right *= DRIVE_TICKS_PER_INCH/10;

		leftDrive[0].set(ControlMode.Velocity, left);
		leftDrive[1].set(ControlMode.PercentOutput, leftDrive[0].getMotorOutputPercent());
		rightDrive[0].set(ControlMode.Velocity, right);
		leftDrive[1].set(ControlMode.PercentOutput, rightDrive[0].getMotorOutputPercent());
	}

	public void translation(final double leftDistance, final double rightDistance) {
		translation(leftDistance,rightDistance,0.5);
	}

	public void translation(final double leftDistance, final double rightDistance, double maxOutput) {
		leftDrive[0].configClosedLoopPeakOutput(0, maxOutput);
		rightDrive[0].configClosedLoopPeakOutput(0, maxOutput);
		leftDrive[0].set(ControlMode.Position, leftDistance * DRIVE_TICKS_PER_INCH);
		rightDrive[0].set(ControlMode.Position, rightDistance * DRIVE_TICKS_PER_INCH);
	}


	public double getTranslationError() {
		return leftDrive[0].getClosedLoopError();
	}

	public double getLeftEncoder() {
		return leftDrive[0].getSelectedSensorPosition();
	}

	public double getRightEncoder() {
		return rightDrive[0].getSelectedSensorPosition();
	}

	public double getLeftVelocity() {
		return leftDrive[0].getSelectedSensorVelocity();
	}

	public double getRightVelocity() {
		return rightDrive[0].getSelectedSensorVelocity();
	}

}
