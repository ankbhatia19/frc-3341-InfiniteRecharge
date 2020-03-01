/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AlignToBall;
import frc.robot.commands.Move;
import frc.robot.commands.MoveAndAlignToBall;
import frc.robot.commands.Path1;
import frc.robot.commands.Path2;
import frc.robot.commands.Path3;
import frc.robot.commands.Turn;

import frc.robot.commands.ReverseTankDrive;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.NavX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...


  public final MoveAndAlignToBall moveAndAlignToBall = new MoveAndAlignToBall();
  public final AlignToBall alignToBall = new AlignToBall();
  public final Turn turn = new Turn(90);
  public final Move move = new Move(10000);

  private NetworkTableEntry delay = Shuffleboard.getTab("SmartDashboard").add("delay", 5).getEntry();

  private Joystick leftJoy;
  private Joystick rightJoy;
  private Joystick mechOpLeft;
  private Joystick mechOpRight;

  public final Path1 path1 = new Path1();
  public final Path2 path2 = new Path2(delay);
  public final Path3 path3 = new Path3();


  public static DriveTrain drive;
  public NavX navx = new NavX();

  public JoystickButton reverseButton;


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    mechOpLeft = new Joystick(0);
    mechOpRight = new Joystick(1);
    leftJoy = new Joystick(2);
    rightJoy = new Joystick(3);
    // Configure the button bindings
    configureButtonBindings();
  }
  public Joystick getLeftJoy() {
    return leftJoy;
  }
  public Joystick getRightJoy() {
    return rightJoy;
  }
  public Joystick getMechOpLeft(){ return mechOpLeft; }
  public Joystick getMechOpRight(){
    return mechOpRight;
  }
  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    new JoystickButton(leftJoy, 3).whileHeld(alignToBall);

    reverseButton = new JoystickButton(rightJoy, 2);
    reverseButton.whenPressed(new ReverseTankDrive());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous

    return moveAndAlignToBall;
  }

  public boolean leftGetRawButton(int n) {
	  return leftJoy.getRawButton(n);
  }
  public boolean rightGetRawButton(int n) {
	  return rightJoy.getRawButton(n);
  }
}
