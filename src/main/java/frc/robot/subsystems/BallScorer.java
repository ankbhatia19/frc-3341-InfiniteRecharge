/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Servo;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import option16.util.Constants;

public class BallScorer extends SubsystemBase {
    /**
     * Creates a new BallScorer.
     */
    private TalonSRX belt;
    private TalonSRX acquirer;
    private TalonSRX flywheelLeft, flywheelRight;

    private Servo gate;

    private int numBalls = 3;
    private double currentThreshold = 1.00; //amps
    private boolean acquiringBall = false;

    private static BallScorer instance;

    public enum beltDirection {
        UP(),
        DOWN(),
        STATIONARY();
    }

    public BallScorer() {
        belt = new TalonSRX(12);
        acquirer = new TalonSRX(10);
        flywheelLeft = new TalonSRX(13);
        flywheelRight = new TalonSRX(14);

        belt.configFactoryDefault();
        belt.setInverted(true);
        belt.configPeakOutputReverse(-1);
        belt.configPeakOutputForward(1);
        belt.setNeutralMode(NeutralMode.Brake);

        belt.config_kP(0, Constants.kP);
        belt.config_kI(0, Constants.kI);
        belt.config_kD(0, Constants.kD);
        belt.config_kF(0, Constants.kF);

        acquirer.configFactoryDefault();
        acquirer.setInverted(false);
        acquirer.configPeakOutputReverse(-1);
        acquirer.configPeakOutputForward(1);
        acquirer.setNeutralMode(NeutralMode.Brake);

        acquirer.config_kP(0, Constants.kP);
        acquirer.config_kI(0, Constants.kI);
        acquirer.config_kD(0, Constants.kD);
        acquirer.config_kF(0, Constants.kF);

        flywheelLeft.configFactoryDefault();
        flywheelLeft.setInverted(false);
        flywheelLeft.configPeakOutputReverse(-1);
        flywheelLeft.configPeakOutputForward(1);
        flywheelLeft.setNeutralMode(NeutralMode.Brake);

        flywheelLeft.config_kP(0, Constants.kP);
        flywheelLeft.config_kI(0, Constants.kI);
        flywheelLeft.config_kD(0, Constants.kD);
        flywheelLeft.config_kF(0, Constants.kF);
        flywheelLeft.set(ControlMode.Follower, flywheelRight.getDeviceID());

        flywheelRight.configFactoryDefault();
        flywheelRight.setInverted(true);
        flywheelRight.configPeakOutputReverse(-1);
        flywheelRight.configPeakOutputForward(1);
        flywheelRight.setNeutralMode(NeutralMode.Brake);

        flywheelRight.config_kP(0, Constants.kP);
        flywheelRight.config_kI(0, Constants.kI);
        flywheelRight.config_kD(0, Constants.kD);
        flywheelRight.config_kF(0, Constants.kF);
    }

    public static BallScorer getInstance(){
        if (instance == null)
            instance = new BallScorer();
        return instance;
    }

    public void beltSpin(beltDirection direction){
        if (direction.equals(beltDirection.DOWN))
            belt.set(ControlMode.PercentOutput, -0.5);
        else if (direction.equals(beltDirection.UP))
            belt.set(ControlMode.PercentOutput, 0.5);
        else if (direction.equals(beltDirection.STATIONARY))
            belt.set(ControlMode.PercentOutput, 0);
    }

    public void depositBalls(boolean deposit){
        if (deposit)
            flywheelRight.set(ControlMode.PercentOutput, 1);
        else
            flywheelRight.set(ControlMode.PercentOutput, 0);

        numBalls = 0;
    }

    public void acquireBalls(boolean acquire){
        if (acquire)
            acquirer.set(ControlMode.PercentOutput, 0.5);
        else
            acquirer.set(ControlMode.PercentOutput, 0);

        if (acquirer.getSupplyCurrent() > currentThreshold && !acquiringBall){
            acquiringBall = true;
            numBalls++;
        }
        else
            acquiringBall = false;
    }

    public void gateSpin(double position) {
        gate.set(position);
    }

    public double returnGatePosition() {
        return gate.getPosition();
    }

    @Override
    public void periodic() {
        if (DriverStation.getInstance().isOperatorControl()){
            acquireBalls(true);
            beltSpin(beltDirection.UP);
            depositBalls(false);
        }
        else if (DriverStation.getInstance().isTest()){
            acquireBalls(false);
            beltSpin(beltDirection.STATIONARY);
            depositBalls(true);
        }

        System.out.println(numBalls);
        // This method will be called once per scheduler run
    }
}