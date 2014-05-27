/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 22/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.MotorCycle;

/**
 * @author hogan
 *
 */
public class MotorCycleTests {

	/**
	 * @throws java.lang.Exception
	 */
	private MotorCycle m;
	@Before
	public void setUp() throws Exception {
		MotorCycle m = new MotorCycle("bac" , 50);	
		this.m = m;		
	}

	/**
	 * @throws java.lang.Exception
	 */
	

	@Test
	public void VehIDTest(){
		assertTrue(m.getVehID() == "bac");
		
	}
	@Test
	public void WrongVehIDTest(){
		assertFalse(m.getVehID() == "cab");	
	}
	@Test
	public void GetArrivalTimeTest(){
	assertTrue(m.getArrivalTime() == 50);
	}
	@Test
	public void GetWrongArrivaleTimeTest(){
	assertFalse(m.getArrivalTime() == 30);	
	}
	@Test
	public void AddNewMotorCycle() throws VehicleException{
	MotorCycle h = new MotorCycle("ghj" , 80);	
	assertFalse(h.getArrivalTime() == 50);	
	}
	
	@Test (expected = VehicleException.class)
	public void AddNewMotorCycleException() throws VehicleException{
	MotorCycle h = new MotorCycle("ghj" , -80);	
	}
	
	@Test
	public void NewMotorCycleVehIDTest() throws VehicleException{
	MotorCycle h = new MotorCycle("ghj", 80);
	assertTrue(h.getVehID() == "ghj");
	}
	@Test
	public void MotorCycleParkedTest(){
	assertFalse(m.isParked() == true);	
	}
	@Test
	public void MotorCycleQueuedTest(){
	assertFalse(m.isQueued() == true);		
	}
	

}
