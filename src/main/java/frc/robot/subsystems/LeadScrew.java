/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import option16.util.Constants;

public class LeadScrew extends SubsystemBase {
    /**
     * Creates a new leadScrew.
     */
    private TalonSRX screw;
    private static LeadScrew instance;

    public LeadScrew() {
        screw = new TalonSRX(7);
        screw.configFactoryDefault();
        screw.setInverted(true);
        screw.configPeakOutputReverse(-1);
        screw.configPeakOutputForward(1);
        screw.setNeutralMode(NeutralMode.Brake);
        screw.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        screw.setSensorPhase(false);
        screw.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        screw.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        //screw.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        screw.config_kP(0, Constants.kP);
        screw.config_kI(0, Constants.kI);
        screw.config_kD(0, Constants.kD);
        screw.config_kF(0, Constants.kF);
        //this.setDefaultCommand(new Screwing());
    }

    public static LeadScrew getInstance(){
        if (instance == null)
            instance = new LeadScrew();
        return instance;
    }

    public void spin(double speed){
        screw.set(ControlMode.PercentOutput,speed);
    }

    @Override
    public void periodic() {
        if (DriverStation.getInstance().isOperatorControl()) {
            double power = Robot.m_robotContainer.getMechOpRight().getY();
            spin(power);
            //System.out.println("Current: " + screw.getSupplyCurrent());
        }
        //setDefaultCommand(new Screwing());
        // This method will be called once per scheduler run
    }
    public TalonSRX getScrewTalon() {
        return screw;
    }
}