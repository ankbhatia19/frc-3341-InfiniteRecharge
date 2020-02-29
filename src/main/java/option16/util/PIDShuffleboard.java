package option16.util;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class PIDShuffleboard {

    private static ShuffleboardTab tab = Shuffleboard.getTab("PIDtest");
    private static int num = 0;

    private NetworkTableEntry p, i, d;

    public PIDShuffleboard(String name, double defaultP, double defaultI, double defaultD) {
        p = tab.add(name + "P", defaultP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", .5)).withSize(2, 1).withPosition(0, 0 + num).getEntry();
        i = tab.add(name + "I", defaultI).withSize(2, 1).withPosition(2, 0 + num).getEntry();
        d = tab.add(name + "D", defaultD).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).withSize(2, 1).withPosition(4, 0 + num).getEntry();
        num++;
    }

    public PIDShuffleboard(String name) {
        this(name, 0, 0, 0);
    }

    public double getP() {
        return p.getDouble(0);
    }
    public double getI() {
        return i.getDouble(0);
    }
    public double getD() {
        return d.getDouble(0);
    }
}