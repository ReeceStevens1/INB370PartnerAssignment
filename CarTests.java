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
	public void TestCarStateParkedFalse() throws VehicleException{
	this.c.enterParkedState(10, 20);
	this.c.exitParkedState(30);
	assertTrue(c.isParked() == false);
	}
	
	@Test
	public void TestCarStateParkedTrue() throws VehicleException {
	this.c.enterParkedState(10, 20);
	assertTrue(c.isParked() == true);
	}
	
	@Test
	public void TestCarStateQueuedFalse() throws VehicleException{
	this.c.enterQueuedState();
	this.c.exitQueuedState(100);
	this.c.enterParkedState(100, 20);
	assertTrue(c.isQueued() == false);
	}
	
	@Test
	public void TestCarStateQueuedTrue() throws VehicleException{
	this.c.enterQueuedState();
	assertTrue(c.isQueued() == true);
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
	
	@Test
	public void TestCarLateArrivalTime() throws VehicleException{
	Car l = new Car("lat" , 1050, false);	
	assertTrue(l.getArrivalTime() == 1050);
	}
	
	@Test
	public void TestParkingTime() throws SimulationException, VehicleException{
		Car i = new Car("kmn", 200, false);
		Car o = new Car("okm", 200, false);
		i.enterParkedState(80, 60);
		o.enterParkedState(80, 60);
		assertTrue(i.getParkingTime() == 80);	
	}
	@Test
	public void TestIsSatisfiedTrue() throws VehicleException{
		Car u = new Car("old", 100, false);
		u.enterParkedState(100, 20);
		u.exitParkedState(120);
		assertTrue(u.isSatisfied() == true);
	}
	@Test
	public void TestIsSatisfiedFalse() throws VehicleException{
		Car u = new Car("old", 100, false);
		u.enterQueuedState();
		assertTrue(u.isSatisfied() == false);
	}
	@Test
	public void TestGetDepartureTime() throws VehicleException{
		Car u = new Car("old", 100, false);
		u.enterParkedState(100, 20);
		u.exitParkedState(120);
		assertTrue(u.getDepartureTime() == 120);
		
		
		
	}
	
	
}