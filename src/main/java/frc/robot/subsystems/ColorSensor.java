/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import option16.util.Constants;

/**
 * Add your docs here.
 */
public class ColorSensor extends SubsystemBase {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final I2C.Port i2cPort;
    private final ColorSensorV3 m_colorSensor;

    private colorWheelStatus status;

    Color detectedColor;
    private  ColorMatch m_colorMatcher;
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    private final TalonSRX wheel;
    private final TalonSRX pivot;

    private static ColorSensor instance;


    public ColorSensor() {
        wheel = new TalonSRX(9);
        pivot = new TalonSRX(11);

        i2cPort = I2C.Port.kOnboard;
        m_colorSensor = new ColorSensorV3(i2cPort);
        detectedColor  = null;
        m_colorMatcher = new ColorMatch();
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);

        wheel.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 1);
        wheel.configFactoryDefault();
        wheel.setInverted(false);
        wheel.configPeakOutputReverse(-1);
        wheel.configPeakOutputForward(1);
        wheel.setNeutralMode(NeutralMode.Brake);
        wheel.config_kP(0, Constants.kP);
        wheel.config_kI(0, Constants.kI);
        wheel.config_kD(0, Constants.kD);
        wheel.config_kF(0, Constants.kF);

        pivot.configFactoryDefault();
        pivot.setInverted(true);
        pivot.configPeakOutputReverse(-1);
        pivot.configPeakOutputForward(1);
        pivot.setNeutralMode(NeutralMode.Brake);
        pivot.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        pivot.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        //screw.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        pivot.config_kP(0, Constants.kP);
        pivot.config_kI(0, Constants.kI);
        pivot.config_kD(0, Constants.kD);
        pivot.config_kF(0, Constants.kF);

        status = colorWheelStatus.DOWN;
        System.out.println("color sensor constructor");
    }

    public static ColorSensor getInstance(){
        if (instance == null){
            instance = new ColorSensor();
        }
        return instance;
    }

    public enum colorWheelStatus{
        UP(), DOWN();
    }

    public String printColors(){ //don't need to know if sensing RGB
        String RGB = "";
        detectedColor = m_colorSensor.getColor();
        RGB = "Red: " + detectedColor.red + ", Green: " + detectedColor.green + ", Blue: " + detectedColor.blue;
        System.out.println(RGB);
        return RGB;
    }

    public String matchColor(){

        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(m_colorSensor.getColor());
        //System.out.println("I'm in matchColor !!" + match.color);

        if (match.color == kBlueTarget) {
            colorString = "blue";
        } else if (match.color == kRedTarget) {
            colorString = "red";
        } else if (match.color == kGreenTarget) {
            colorString = "green";
        } else if (match.color == kYellowTarget) {
            colorString = "yellow";
        } else {
            colorString = "unknown";
        }
        //System.out.println(colorString);
        return colorString;

    /*colorString += " , confidence: " + match.confidence();
    colorString += m_colorSensor.hasReset();
    return colorString;*/
    }

    public void moveColorWheelPivot(colorWheelStatus status){
        if (status.equals(colorWheelStatus.UP))
            pivot.set(ControlMode.PercentOutput, 0.5);
        else if (status.equals(colorWheelStatus.DOWN))
            pivot.set(ControlMode.PercentOutput, -0.5);
    }

    public double velocity(){

        double velocity = wheel.getSelectedSensorVelocity(0);
        return velocity;
    }


    public double getTicks(){
        return wheel.getSelectedSensorPosition();
    }
    public boolean getButton(){

        return false;//button.get();
    }

    public void spinWheel(double speed){

        wheel.set(ControlMode.PercentOutput, speed);
    }

    public void resetSensorPosition(){
        wheel.setSelectedSensorPosition(0);
    }

    public void colorControl(int n){
        wheel.set(ControlMode.PercentOutput, 0.2);
        if(n == 1){
            if(matchColor() == "red"){
                wheel.set(ControlMode.PercentOutput, 0);
            }
        }
        else if(n == 2){
            if(matchColor() == "blue"){
                wheel.set(ControlMode.PercentOutput, 0);
            }
        }
        else if(n == 3){
            if(matchColor() == "green"){
                wheel.set(ControlMode.PercentOutput, 0);
            }
        }
        else if(n == 4){
            if(matchColor() == "yellow"){
                wheel.set(ControlMode.PercentOutput, 0);
            }
        }
        else{
            wheel.set(ControlMode.PercentOutput, 0.2);
        }
    }



    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // wheel.set(ControlMode.PercentOutput, 0.4);
        /*if (DriverStation.getInstance().isOperatorControl()){
            moveColorWheelPivot(colorWheelStatus.UP);
        }
        else if (DriverStation.getInstance().isTest()) {
            moveColorWheelPivot(colorWheelStatus.DOWN);
        }*/
    }


    //@Override
    //public void setDefaultCommand(new MeasureColors(this)) {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // setDefaultCommand(new MeasureColors(this));
    //}
}