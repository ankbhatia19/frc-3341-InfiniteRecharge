/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package option16.util;

/**
 * Add your docs here.
 */
public class Constants {

    //ALL UNITS ARE IN FEET
    public static double wheelRadius = 0.25;
    public static double maxVelocity = 2.6;
    public static double wheelBaseWidth = 22.5 / (double)12;
    public static double kP = 0.25;
    public static double kI = 0.001;
    public static double kD = 20;
    public static double kF = 0.1079;

    public static double kV = 1; //needs tuning
    public static int ticksPerRevolution = 4096;

    public static int lookaheadDistance = 6;
}
