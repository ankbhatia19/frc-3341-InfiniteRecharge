/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import option16.util.PID;
import option16.util.PIDShuffleboard;

public class Move extends CommandBase {
	private double distance;
	
	private PID leftPID, rightPID;
  private PIDShuffleboard leftPIDShuffleboard, rightPIDShuffleboard;
  /**
   * Creates a new Move.
   */
  public Move(double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.distance = distance;
    addRequirements(DriveTrain.getInstance());
    leftPID = new PID(0, 0, 0);
    rightPID = new PID(0, 0, 0);
    leftPIDShuffleboard = new PIDShuffleboard("leftMove");
    rightPIDShuffleboard = new PIDShuffleboard("rightMove");

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    leftPID.setPID(leftPIDShuffleboard);
    rightPID.setPID(rightPIDShuffleboard);
	  leftPID.update(distance - DriveTrain.getInstance().getLeftPosition());
	  rightPID.update(distance - DriveTrain.getInstance().getRightPosition());
	  DriveTrain.getInstance().tankDrive(leftPID.getPower(), rightPID.getPower(), false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	return Math.abs(distance - DriveTrain.getInstance().getLeftPosition()) < 4000 && leftPID.getPower() < .2 &&
			Math.abs(distance - DriveTrain.getInstance().getRightPosition()) < 4000 && rightPID.getPower() < .2;
  }
}
