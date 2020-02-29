/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import option16.util.Limelight;
import option16.util.PIDShuffleboard;

public class AlignToBall extends CommandBase {
  /**
   * Creates a new AligntoBall.
   */

  private PIDShuffleboard pidShuffleboard = new PIDShuffleboard("ballAlign");
  
  public AlignToBall() {
    // Use addRequirements() here to declare subsystem dependencies.
    Limelight.setAlignConstants(pidShuffleboard.getP(), pidShuffleboard.getI(), pidShuffleboard.getD());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveTrain.getInstance().align(Limelight.align());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
