package com.amhsrobotics.subsystems;

import com.amhsrobotics.purepursuit.coordinate.CoordinateManager;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		leftDrive[1].set(ControlMode.Follower,LEFT_DRIVE_TALON_ID[0]);
		rightDrive[1].set(ControlMode.Follower,RIGHT_DRIVE_TALON_ID[0]);
	}

	@Override
	public void initDefaultCommand() {
//		setDefaultCommand(new TankDrive());
	}

	public void tankDrive(double left, double right) {
		tankDrive(left, right, 1);
	}

	public void tankDrive(double left, double right, final double percentCap) {
		left *= percentCap;
		right *= percentCap;

			leftDrive[0].set(ControlMode.PercentOutput, left);
			rightDrive[0].set(ControlMode.PercentOutput, right);
	}

	public void tankVelocity(double left, double right) {
		left *= DRIVE_TICKS_PER_INCH/10;
		right *= DRIVE_TICKS_PER_INCH/10;

		leftDrive[0].set(ControlMode.Velocity, left);
		leftDrive[1].set(ControlMode.PercentOutput, leftDrive[0].getMotorOutputPercent());
		rightDrive[0].set(ControlMode.Velocity, right);
		leftDrive[1].set(ControlMode.PercentOutput, rightDrive[0].getMotorOutputPercent());
	}

	double leftLastMeasured = 0;
	double rightLastMeasured = 0;

	final double kV = 0.12; //0.12
	final double kA = 0.0; //0.0
	final double kP = 0.01; //0.01
	final double kT = 0;

	public void customTankVelocity(double leftVel, double rightVel, double angle){
		double left;
		double right;

		angle = CoordinateManager.getInstance().mapAngle(angle);

		SmartDashboard.putNumber("ANGLE_TO_LOOKAHEAD", angle);

		angle = angle / 45;
		angle = Math.max(-1, Math.min(angle,1));

		System.out.println(angle);

		double turn = angle * kT;

		leftVel += turn;
		rightVel -= turn;

		SmartDashboard.putNumber("LEFT_WHEEL_SETPOINT", leftVel);
		SmartDashboard.putNumber("RIGHT_WHEEL_SETPOINT", rightVel);

		SmartDashboard.putNumber("LEFT_WHEEL_SETPOINT_MODIFIED", leftVel);
		SmartDashboard.putNumber("RIGHT_WHEEL_SETPOINT_MODIFIED", rightVel);

		double measuredLeft = DriveTrain.getInstance().getLeftVelocityInches();
		double FFLeft = kV * leftVel + kA * ((measuredLeft - leftLastMeasured)/.02);
		leftLastMeasured = measuredLeft;
		double errorLeft = leftVel - measuredLeft;
		double FBLeft = kP * errorLeft;
		left = (FFLeft + FBLeft);
		left = Math.max(-12, Math.min(12,left));

		double measuredRight = DriveTrain.getInstance().getRightVelocityInches() ;

		double FFRight = kV * rightVel + kA * ((measuredRight - rightLastMeasured)/.02);

		rightLastMeasured = measuredRight;

		double errorRight = rightVel - measuredRight;

		double FBRight = kP * errorRight;

		right = (FFRight + FBRight);

		right = Math.max(-12, Math.min(12,right));

		left = left/12;
		right = right/12;


		SmartDashboard.putNumber("LEFT_WHEEL_VOLTAGE", left);
		SmartDashboard.putNumber("RIGHT_WHEEL_VOLTAGE", right);

		DriveTrain.getInstance().tankDrive(left,right);
	}

	public void customTankVelocity(double leftVel, double rightVel){
		double left;
		double right;

		SmartDashboard.putNumber("LEFT_WHEEL_SETPOINT_MODIFIED", leftVel);
		SmartDashboard.putNumber("RIGHT_WHEEL_SETPOINT_MODIFIED", rightVel);

		double measuredLeft = DriveTrain.getInstance().getLeftVelocityInches();
		double FFLeft = kV * leftVel + kA * ((measuredLeft - leftLastMeasured)/.02);
		leftLastMeasured = measuredLeft;
		double errorLeft = leftVel - measuredLeft;
		double FBLeft = kP * errorLeft;
		left = (FFLeft + FBLeft);
		left = Math.max(-12, Math.min(12,left));

		double measuredRight = DriveTrain.getInstance().getRightVelocityInches() ;

		double FFRight = kV * rightVel + kA * ((measuredRight - rightLastMeasured)/.02);

		rightLastMeasured = measuredRight;

		double errorRight = rightVel - measuredRight;

		double FBRight = kP * errorRight;

		right = (FFRight + FBRight);

		right = Math.max(-12, Math.min(12,right));

		left = left/12;
		right = right/12;

		SmartDashboard.putNumber("LEFT_WHEEL_VOLTAGE", left);
		SmartDashboard.putNumber("RIGHT_WHEEL_VOLTAGE", right);


		DriveTrain.getInstance().tankDrive(left,right);
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

	public double getLeftVelocityInches() {
		return leftDrive[0].getSelectedSensorVelocity() * 10.0 / DRIVE_TICKS_PER_INCH;
	}

	public double getRightVelocityInches() {
		return rightDrive[0].getSelectedSensorVelocity() * 10.0 / DRIVE_TICKS_PER_INCH;
	}

}
