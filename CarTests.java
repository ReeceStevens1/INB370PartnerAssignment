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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;
import asgn2Vehicles.Vehicle;


/**
 * @author hogan
 *
 */
public class CarTests {

	/**
	 * @throws java.lang.Exception
	 */
	private Car c;
	
	
	@Before
	public void setUp() throws Exception {
	Car c = new Car("abc" , 80, true);	
	this.c = c;	

	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#Car(java.lang.String, int, boolean)}.
	 */
	@Test 
	public void testIsSmallFalse() {
		assertFalse(c.isSmall() == false);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#isSmall()}.
	 */
	@Test
	public void testIsSmallTrue() {
	assertTrue(c.isSmall() == true);
	}
	@Test
	public void VehIDTest(){
	assertTrue(c.getVehID() == "abc");	
		
	}
	@Test
	public void VehIDTestDifferentOrder(){
	assertFalse(c.getVehID() == "bca");	
		
	}
	@Test
	public void ArrivalTimeTest(){
	assertTrue(c.getArrivalTime() == 80);	
		
	}
	@Test 
	public void WrongArrivalTimeTest(){
	assertFalse(c.getArrivalTime() == 70);
		
	}
	@Test
	public void AddingNewCar() throws VehicleException{
	Car a = new Car("cad" , 90, false);	
	assertTrue(a.getArrivalTime() == 90);
		
	}
	@Test
	public void GettingVehIDFromNewCar() throws VehicleException{
	Car a = new Car("cad" , 90, false);	
	assertTrue(a.getVehID() == "cad");
	}
	@Test
	public void FalseSmallCar() throws VehicleException{
	Car a = new Car("cad" , 90, false);	
	assertTrue(a.isSmall() == false);
	}
	@Test
	public void TestCarStateParked(){
	assertTrue(c.isParked() == false);
	}
	@Test
	public void TestCarStateQueued(){
	assertTrue(c.isQueued() == false);
	}
	@Test
	public void TestCarVehIDTenLetter() throws VehicleException{
	Car f = new Car("abcdefghij" , 80, false);
	assertTrue(f.getVehID() == "abcdefghij");		
	}
	@Test (expected = VehicleException.class)
	public void TestNegativeTime() throws VehicleException{
	Car k = new Car("kji" , -10, false);
	
		
	}
	//KEEP FOR CARPARK TESTS
	@Test
	public void TestCarLateArrivalTime() throws VehicleException{
	Car l = new Car("lat" , 1050, false);	
	assertTrue(l.getArrivalTime() == 1050);
		
	}
	
	
	
	
}