/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Robot;
import option16.util.Constants;


public class Pivot extends SubsystemBase {
    /**
     * Creates a new Pivot.
     */
    private static Pivot instance;
    private final TalonSRX pivotMotor;
    // private final TalonSRX motorRight = new TalonSRX(3);
    public boolean lock = false;

    public void pivot(double JOY) {
        pivotMotor.set(ControlMode.PercentOutput, JOY);

    }
    public Pivot() {
        pivotMotor = new TalonSRX(6);
        pivotMotor.configFactoryDefault();
        pivotMotor.setInverted(true);
        pivotMotor.configPeakOutputReverse(-1);
        pivotMotor.configPeakOutputForward(1);
        pivotMotor.setNeutralMode(NeutralMode.Brake);
        pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        pivotMotor.setSensorPhase(false);
        pivotMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        pivotMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        //pivotMotor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        pivotMotor.config_kP(0, Constants.kP);
        pivotMotor.config_kI(0, Constants.kI);
        pivotMotor.config_kD(0, Constants.kD);
        pivotMotor.config_kF(0, Constants.kF);
        // RotatePivot r = new RotatePivot();
    }

    public static Pivot getInstance(){
        if (instance == null)
            instance = new Pivot();
        return instance;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
    public boolean getLock() {
        return lock;
    }
    public boolean atTop() {
        return pivotMotor.getSensorCollection().isRevLimitSwitchClosed();
    }
    public boolean atBottom() {
        return pivotMotor.getSensorCollection().isFwdLimitSwitchClosed();
    }

    @Override
    public void periodic() {
        //setDefaultCommand(new RotatePivot());
        // This method will be called once per scheduler run
        //Pivot(Robot.m_robotContainer.getPivotJoy().getY());
    }
    public TalonSRX getPivotTalon() {
        return pivotMotor;
    }
}