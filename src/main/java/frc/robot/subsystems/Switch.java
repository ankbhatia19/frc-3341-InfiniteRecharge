/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import option16.util.Constants;

public class Switch extends SubsystemBase {
    /**
     * Creates a new Switch.
     */
    private TalonSRX balance;
    private static Switch instance;

    public Switch() {
        balance = new TalonSRX(8);
        balance.configFactoryDefault();
        balance.setInverted(false);
        balance.configPeakOutputReverse(-1);
        balance.configPeakOutputForward(1);
        balance.setNeutralMode(NeutralMode.Brake);
        balance.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
        balance.setSensorPhase(false);

        balance.config_kP(0, Constants.kP);
        balance.config_kI(0, Constants.kI);
        balance.config_kD(0, Constants.kD);
        balance.config_kF(0, Constants.kF);
    }

    public static Switch getInstance(){
        if (instance == null)
            instance = new Switch();
        return instance;
    }

    public void move(double speed) {
        balance.set(ControlMode.PercentOutput, speed);
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public TalonSRX getBalanceTalon() {
        return balance;
    }
}