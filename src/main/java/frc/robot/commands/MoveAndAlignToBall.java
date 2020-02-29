/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import option16.util.Limelight;

import java.util.Map;

public class MoveAndAlignToBall extends CommandBase {
	
	private ShuffleboardTab PID = Shuffleboard.getTab("PID");
  	private NetworkTableEntry moveP = PID.add("moveP", .0386).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", .5)).withSize(2, 1).withPosition(0, 0).getEntry();
	private NetworkTableEntry moveI = PID.add("moveI", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).withSize(2, 1).withPosition(2, 0).getEntry();
	private NetworkTableEntry moveD = PID.add("moveD", 1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).withSize(2, 1).withPosition(4, 0).getEntry();
	private NetworkTableEntry targetArea = PID.add("targetArea", 1.56).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 20)).withSize(2, 1).withPosition(6, 0).getEntry();
	private NetworkTableEntry alignP = PID.add("alignP", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", .1)).withSize(2, 1).withPosition(0, 1).getEntry();
	private NetworkTableEntry alignI = PID.add("alignI", 0).withSize(2, 1).withPosition(2, 1).getEntry();
	private NetworkTableEntry alignD = PID.add("alignD", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).withSize(2, 1).withPosition(4, 1).getEntry();
	
  /**
   * Creates a new AlignToBall.
   */
  public MoveAndAlignToBall() {
	// Use addRequirements() here to declare subsystem dependencies.
	addRequirements(DriveTrain.getInstance());
	Limelight.setMoveConstants(moveP.getDouble(0), moveI.getDouble(0), moveD.getDouble(0), targetArea.getDouble(0));
	Limelight.setAlignConstants(alignP.getDouble(0), alignI.getDouble(0), alignD.getDouble(0));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  	// Called every time the scheduler runs while the command is scheduled.
 	@Override
  	public void execute() {
		DriveTrain.getInstance().arcadeDrive(Limelight.move(), Limelight.align(), false);
		System.out.println("powers: " + Limelight.move() + " " + Limelight.align());
		System.out.println("constants: " + 
			moveP.getDouble(0) + " " + 
			moveI.getDouble(0) + " " + 
			moveD.getDouble(0) + " " + 
			targetArea.getDouble(0) + " " +
			alignP.getDouble(0) + " " +
			alignI.getDouble(0) + " " +
			alignD.getDouble(0)
			);
		Limelight.setMoveConstants(moveP.getDouble(0), moveI.getDouble(0), moveD.getDouble(0), targetArea.getDouble(0));
		Limelight.setAlignConstants(alignP.getDouble(0), alignI.getDouble(0), alignD.getDouble(0));
  	}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return Math.abs(targetArea.getDouble(0) - Limelight.getArea()) < 1 && Limelight.move() < 0.2 && Limelight.align() < 0.2;
  }
}
