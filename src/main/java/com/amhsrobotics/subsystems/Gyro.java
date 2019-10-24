package com.amhsrobotics.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * Class to access the Gyro on the robot
 */
public class Gyro extends ADXRS450_Gyro {
    private static Gyro ourInstance = new Gyro();

    /**
     * Gets the only instance of the gyro being used as a singleton
     * @return a static instance of the gyro
     */
    public static Gyro getInstance() {
        return ourInstance;
    }

    /**
     * Runs the constructor of the ADXRS450_Gyro Class
     */
    private Gyro() {
        super();
    }
}