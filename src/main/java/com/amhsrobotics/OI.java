/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.amhsrobotics;

import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static OI ourInstance = new OI();

	public static OI getInstance(){
		return ourInstance;
	}

	private XboxController driveController = new XboxController(0);

	private OI(){

	}

	public XboxController getDriveController() {
		return driveController;
	}

	public void setDriveController(XboxController driveController) {
		this.driveController = driveController;
	}
}
