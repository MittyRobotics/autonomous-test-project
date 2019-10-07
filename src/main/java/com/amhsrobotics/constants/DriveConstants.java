package com.amhsrobotics.constants;

public class DriveConstants {
	public static final int[] LEFT_DRIVE_TALON_ID = {20, 21};
	public static final int[] RIGHT_DRIVE_TALON_ID = {23, 22};

	public static final boolean[] LEFT_DRIVE_TALON_INVERSION = {false, false};
	public static final boolean[] RIGHT_DRIVE_TALON_INVERSION = {true, true};

	public static final boolean LEFT_DRIVE_ENCODER_INVERSION = false;
	public static final boolean RIGHT_DRIVE_ENCODER_INVERSION = true;

	public static final double[] DRIVE_PID = {2, 0, 0};

	public static final double DRIVE_TICKS_PER_INCH =  99.9;
}

