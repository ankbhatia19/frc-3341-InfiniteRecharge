package option16.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
	private static NetworkTable table;
	private static NetworkTableEntry tv, tx, ty, ta;
	private static int pipeline = 0;
	private static int v;
	private static double x, y, area;
	private static double targetArea;
	private static double ballheight = 3.5; //inches
	private static double cameraheight = 10.5; //inches

	private static PID movePID, alignPID;

	static {
		table = NetworkTableInstance.getDefault().getTable("limelight");
		tv = table.getEntry("tv");
		tx = table.getEntry("tx");
		ty = table.getEntry("ty");
		ta = table.getEntry("ta");
		movePID = new PID(0, 0, 0);
		alignPID = new PID(0, 0, 0);
	}

	public static void update() {
		table.getEntry("pipeline").setNumber(pipeline);
		table.getEntry("ledMode").setNumber(0);
		v = (int) tv.getDouble(0.0);
		x = tx.getDouble(0.0);
		y = ty.getDouble(0.0);
		area = ta.getDouble(0.0);
		if (v == 0) {
			movePID.reset();
			alignPID.reset();
		}
		movePID.update(targetArea - area);
		alignPID.update(x);
		SmartDashboard.putNumber("x", x);
		SmartDashboard.putNumber("y", y);
		SmartDashboard.putNumber("area", area);
		SmartDashboard.putBoolean("v", v == 1 ? true : false);
	}

	public static void disable() {
		table.getEntry("pipeline").setNumber(0);
	}

	public static void setPipeline(int num) {
		if (num >= 0 && num <= 9) {
			pipeline = num;
		}
	}

	public static int getV() {
		return v;
	}
	public static double getX() {
		return x;
	}
	public static double getY() {
		return y;
	}
	public static double getArea() {
		return area;
	}
	public static NetworkTableEntry getEntry(String entry) {
		return table.getEntry(entry);
	}

	public static void setMoveConstants(double kp, double ki, double kd, double area) {
		movePID.setPID(kp, ki, kd);
		targetArea = area;
	}
	public static void setAlignConstants(double kp, double ki, double kd) {
		alignPID.setPID(kp, ki, kd);
	}
	
	public static double align() {
		if (Math.abs(x) > 1) {
			return alignPID.getPower();
		}
		return 0;
	}
	public static double move() {
		if (v == 1) {
			return movePID.getPower();
		}
		return 0;
	}

	public static void flash() {
		table.getEntry("ledMode").setNumber(2);
	}

	public static double estimateDistance(double a1, double a2){
		return (cameraheight-ballheight)/Math.tan(a1+a2);
	}
}
