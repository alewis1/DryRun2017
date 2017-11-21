package org.usfirst.frc.team4561.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

import org.usfirst.frc.team4561.robot.commands.DriveArcadeTwoStick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private static Joystick rightStick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);
	private static Joystick leftStick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
	private static Joystick controller = new Joystick(RobotMap.CONTROLLER_PORT);
	
	public double getRightStickY() {
		double rightStickY = rightStick.getY();
		
		
		return rightStickY;
	}

	
	public double getRightStickX() {
		double rightStickX = rightStick.getX();
		
		
		return rightStickX;
	}
	
	
	public double getLeftStickY() {
		double leftStickY = leftStick.getY();
		
		
		return leftStickY;
	}
	
	
	public double getLeftStickX() {
		double leftStickX = leftStick.getX();
		
		
		return leftStickX;
	}
	
}
