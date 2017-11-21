package org.usfirst.frc.team4561.robot.subsystems;

import org.usfirst.frc.team4561.robot.RobotMap;
import org.usfirst.frc.team4561.robot.commands.DriveArcadeTwoStick;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	private CANTalon frontRight;
	private CANTalon frontLeft;
	
	private CANTalon midRight;
	private CANTalon midLeft;
	
	private CANTalon rearRight;
	private CANTalon rearLeft;
	
	private RobotDrive robotDrive;
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveArcadeTwoStick());
    }
	
	public DriveTrain(){
		frontRight = new CANTalon(RobotMap.FRONT_RIGHT_MOTOR_PORT);
		frontLeft = new CANTalon(RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		midRight = new CANTalon(RobotMap.MID_RIGHT_MOTOR_PORT);
		midLeft = new CANTalon(RobotMap.MID_RIGHT_MOTOR_PORT);
		
		rearRight = new CANTalon(RobotMap.REAR_RIGHT_MOTOR_PORT);
		rearLeft = new CANTalon(RobotMap.REAR_LEFT_MOTOR_PORT);
		
		//Set the non-front motors to follower mode, so that the motors on a given side always go in the same direction as the front motor
		midRight.changeControlMode(CANTalon.TalonControlMode.Follower);
		midRight.set(RobotMap.FRONT_RIGHT_MOTOR_PORT);
		
		rearRight.changeControlMode(CANTalon.TalonControlMode.Follower);
		rearRight.set(RobotMap.FRONT_RIGHT_MOTOR_PORT);
		
		midLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
		midLeft.set(RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		rearLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
		rearLeft.set(RobotMap.FRONT_LEFT_MOTOR_PORT);
		
		
		robotDrive = new RobotDrive(frontLeft, frontRight);
	}
	
	//All code below this point was taken from Dynamo's code.
	/**
	 * TODO: Document
	 * @param powerLeft
	 * @param powerRight
	 * @see {@link #setLeftMotorPower(double) setLeftMotorPower}, {@link #setRightMotorPower(double) setRightMotorPower}
	 */
	public void setMotorPower(double powerLeft, double powerRight) {
		setLeftMotorPower(powerLeft);
		setRightMotorPower(powerRight);
	}
	
	/**
	 * Sets power to the left side of the Drive Train.
	 * @param power to set the motors to.
	 * @see {@link #setRightMotorPower(double) setRightMotorPower}, {@link #setMotorPower(double, double) setMotorPower}
	 */
	public void setLeftMotorPower(double power) {
		if (power > 1) {
			
			power = 1;
		} else if (power < -1) {
			
			power = -1;
		}
		frontLeft.set(power);
	}
	
	/**
	 * Sets power to the right side of the Drive Train.
	 * @param power to set the motors to.
	 * @see {@link #setLeftMotorPower(double) setLeftMotorPower}, {@link #setMotorPower(double, double) setMotorPower}
	 */
	public void setRightMotorPower(double power) {
		if (power > 1) {
			
			power = 1;
		} else if (power < -1) {
			
			power = -1;
		}
		frontRight.set(power);
	}
	
	/**
	 * TODO: Document
	 * @param drive
	 * @param rot
	 */
	public void arcadeDrive(double drive, double rot) {
		
		double leftMotorSpeed = 0.0;
		double rightMotorSpeed = 0.0;
		if (drive > 0.0) {
			if (rot > 0.0) {
				leftMotorSpeed = drive - rot;
				rightMotorSpeed = Math.max(drive, rot);
		    } else {
		    	leftMotorSpeed = Math.max(drive, -rot);
		    	rightMotorSpeed = drive + rot;
		      }
		    } else {
		      if (rot > 0.0) {
		        leftMotorSpeed = -Math.max(-drive, rot);
		        rightMotorSpeed = drive + rot;
		      } else {
		        leftMotorSpeed = drive - rot;
		        rightMotorSpeed = -Math.max(-drive, -rot);
		      }
		    }
//		frontRight.set(rightMotorSpeed);
//		frontLeft.set(leftMotorSpeed);
		leftMotorSpeed *= 0.925;
		robotDrive.setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
	}
	
	/**
	 * TODO: Document
	 * @param leftpower
	 * @param rightpower
	 */
	public void SwitchToVelocity(){
		frontRight.changeControlMode(TalonControlMode.Speed);	//changes talons to velocity pid mode
		frontLeft.changeControlMode(TalonControlMode.Speed);
		frontRight.setF(0);
		frontRight.setP(.25);
		frontRight.setI(0);
		frontRight.setD(0);
	
		frontLeft.setF(0);
		frontLeft.setP(0);
		frontLeft.setI(0);
		frontLeft.setD(0);
	}

	public void SwitchToPositional(){
		frontRight.changeControlMode(TalonControlMode.Position);	//changes talons to positional pid mode
		frontLeft.changeControlMode(TalonControlMode.Position);
		frontRight.set(1000);				//set to pos pid testing target position
		frontLeft.set(1000);
	
		frontRight.setF(0);
		frontRight.setP(.25);
		frontRight.setI(0);
		frontRight.setD(0);
	
		frontLeft.setF(0);
		frontLeft.setP(0);
		frontLeft.setI(0);
		frontLeft.setD(0);
	}
	
	public void SwitchToDefault(){
		frontRight.changeControlMode(TalonControlMode.PercentVbus);	//changes talons to percentvbus mode
		frontLeft.changeControlMode(TalonControlMode.PercentVbus);	//TODO: verify that percentvbus is correct mode
	}
	
	public void tankDrive(double leftpower, double rightpower) {
		leftpower *= 0.925;
		robotDrive.tankDrive(leftpower, -rightpower);
	}
	
	
	
	/**
	 * Stops the drivetrain.
	 * @see {@link #stopLeft() stopLeft}, {@link #stopRight() stopRight}
	 * @author Kaiz
	 */
	public void stop() {
		stopLeft();
		stopRight();
	}
	
	/**
	 * Stops the left side of the drivetrain.
	 * @see {@link #stop() stop}, {@link #stopRight() stopRight}
	 * @author Kaiz
	 */
	public void stopLeft() {
		frontLeft.set(0);
	}
	
	/**
	 * Stops the right side of the drivetrain.
	 * @see {@link #stop() stop}, {@link #stopLeft() stopLeft}
	 * @author Kaiz
	 */
	public void stopRight() {
		frontRight.set(0);
	}

}
