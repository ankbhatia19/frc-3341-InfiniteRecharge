/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.DriverStation;
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
    private double positionLock = -1;

    private double kP, kI, kD, kF;

    public void pivot(double JOY) {
        pivotMotor.set(ControlMode.PercentOutput, JOY);

    }
    public Pivot() {
        kP = 1; //config this madness
        kI = 0;
        kD = 0;
        kF = 20;

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
        pivotMotor.config_kP(0, this.kP);
        pivotMotor.config_kI(0, this.kI);
        pivotMotor.config_kD(0, this.kD);
        pivotMotor.config_kF(0, this.kF);
        // RotatePivot r = new RotatePivot();
    }

    public static Pivot getInstance(){
        if (instance == null)
            instance = new Pivot();
        return instance;
    }

    public void enablePositionLocking(){
        if (positionLock == -1)
            positionLock = pivotMotor.getSelectedSensorPosition(0);
        pivotMotor.set(ControlMode.Position, positionLock);
    }

    @Override
    public void periodic() {
        if (DriverStation.getInstance().isOperatorControl()) {
            double power = Robot.m_robotContainer.getMechOpLeft().getY();
            pivot(power);
        }
    }
    public TalonSRX getPivotTalon() {
        return pivotMotor;
    }
}