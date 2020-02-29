/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import option16.util.PID;
import option16.util.PIDShuffleboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.NavX;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Turn extends CommandBase {
  private double angle;

  private PID pid;
  private PIDShuffleboard pidShuffleboard = new PIDShuffleboard("turn");
  /**
   * Creates a new Turn.
   */
  public Turn(double angle) {
	// Use addRequirements() here to declare subsystem dependencies.
    this.angle = angle;
    addRequirements(DriveTrain.getInstance());
    NavX.getInstance().reset();
    pid = new PID(pidShuffleboard.getP(), pidShuffleboard.getI(), pidShuffleboard.getD());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
	  pid.update(angle - NavX.getInstance().getAngle());
	  DriveTrain.getInstance().arcadeDrive(0, pid.getPower(), false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
	return Math.abs(NavX.getInstance().getAngle() - angle) < 10 && pid.getPower() < .2;
  }
}
