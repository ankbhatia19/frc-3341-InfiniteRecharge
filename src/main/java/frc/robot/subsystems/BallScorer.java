/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
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
    private double currentThreshold = 0; //amps
    private boolean acquiringBall = false;

    private static BallScorer instance;

    public enum Direction {
        UP,
        DOWN,
        STATIONARY;
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

    public void beltSpin(Direction direction){
        if (direction.equals(Direction.DOWN))
            belt.set(ControlMode.PercentOutput, -0.5);
        else if (direction.equals(Direction.UP))
            belt.set(ControlMode.PercentOutput, 0.5);
        else if (direction.equals(Direction.STATIONARY))
            belt.set(ControlMode.PercentOutput, 0);
    }

    public void depositBalls(Direction dir){
        if (dir.equals(Direction.UP))
            flywheelRight.set(ControlMode.PercentOutput, 1);
        else if (dir.equals(Direction.DOWN))
            flywheelRight.set(ControlMode.PercentOutput, -1);
        else if (dir.equals(Direction.STATIONARY)){
            flywheelRight.set(ControlMode.PercentOutput, 0);
        }

        numBalls = 0;
    }

    public void acquireBalls(Direction dir){
        if (dir.equals(Direction.UP)) {
            acquirer.set(ControlMode.PercentOutput, 0.5);

            if (acquirer.getSupplyCurrent() > currentThreshold && !acquiringBall){
                acquiringBall = true;
                numBalls++;
            }
            else if (acquirer.getSupplyCurrent() == currentThreshold)
                acquiringBall = false;
        }
        else if (dir.equals(Direction.DOWN))
            acquirer.set(ControlMode.PercentOutput, -0.5);
        else if (dir.equals(Direction.STATIONARY)){
            acquirer.set(ControlMode.PercentOutput, 0);
        }


    }

    public void gateSpin(double position) {
        gate.set(position);
    }

    public double returnGatePosition() {
        return gate.getPosition();
    }

    @Override
    public void periodic() {
        /*beltSpin(beltDirection.UP);
        if (DriverStation.getInstance().isOperatorControl()){
            acquireBalls(true);
            depositBalls(false);
        }
        if (DriverStation.getInstance().isTest()){
            acquireBalls(false);
            depositBalls(true);
        }*/
        if (DriverStation.getInstance().isOperatorControl()) {
            beltSpin(Direction.UP);
            acquireBalls(Direction.UP);
            depositBalls(Direction.UP);
            /*if (acquiringBall){
                System.out.println("Ball Current: " + acquirer.getSupplyCurrent());
                long currentTime = System.currentTimeMillis();
                System.out.println("Time remaining: " + (System.currentTimeMillis() - currentTime));
                while (System.currentTimeMillis() - currentTime < 1000){
                    System.out.println("moving balls downwards");
                    acquireBalls(Direction.DOWN);
                    beltSpin(Direction.DOWN);
                    depositBalls(Direction.STATIONARY);
                }
            }*/
        }
        else if (DriverStation.getInstance().isTest()){
            beltSpin(Direction.DOWN);
            acquireBalls(Direction.STATIONARY);
            depositBalls(Direction.DOWN);
        }
        //System.out.println(numBalls);
        // This method will be called once per scheduler run
    }
}