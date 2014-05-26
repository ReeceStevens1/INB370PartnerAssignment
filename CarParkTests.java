/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 29/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;


/**
 * @author hogan
 *
 */


public class CarParkTests {

	/**
	 * @throws java.lang.Exception
	 */
	private CarPark cp;
	private Vehicle car1;
	private Vehicle car2;
	private Vehicle car3;
	private Vehicle car4;
	private Vehicle car5;
	private Vehicle car6;
	private Vehicle car7;
	private Vehicle car8;
	private Vehicle car9;
	private Vehicle car10;
	private Vehicle mc1;
	private Vehicle mc2;
	private Vehicle mc3;
	private Vehicle mc4;
	private Vehicle mc5;
	
	
	@Before
	public void setUp() throws Exception {
		CarPark cp = new CarPark(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
		this.cp = cp;
		this.car1 = new Car("abc" , 80, true);	
		this.car2 = new Car("def" , 90, true);
		this.car3 = new Car("ghi" , 100, true);	
		this.car4 = new Car("jkk" , 110, false);	
		this.car5 = new Car("mno" , 120, false);	
		this.car6 = new Car("pqr" , 130, false);	
		this.car7 = new Car("stu" , 140, false);	
		this.car8 = new Car("vwx" , 150, false);	
		this.car9 = new Car("yza" , 160, false);	
		this.car10 = new Car("bac" , 170, false);
		this.mc1 = new MotorCycle("jki" , 90);
		this.mc2 = new MotorCycle("iop" , 100);
		this.mc3 = new MotorCycle("hop" , 110);
		this.mc4 = new MotorCycle("and" , 120);
		this.mc5 = new MotorCycle("wat" , 130);
		
	
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.cp = new CarPark(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveDepartingVehicles(int, boolean)}.
	 * @throws SimulationException 
	 * @throws VehicleException 
	 */
	@Test
	public void testArchiveDepartingVehicles() throws VehicleException, SimulationException {
		cp.parkVehicle(car2, 10,60);
		cp.archiveDepartingVehicles(80, false);
		assertTrue(cp.past.contains(car2) == true);
		
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveNewVehicle(asgn2Vehicles.Vehicle)}.
	 * @throws SimulationException 
	 */
	@Test 
	public void testArchiveNewVehicle() throws SimulationException {
		cp.archiveNewVehicle(car3);
		assertTrue(cp.past.size() == 1);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveQueueFailures(int)}.
	 * @throws SimulationException 
	 * @throws VehicleException 
	 */
	@Test
	public void testArchiveQueueFailures() throws VehicleException, SimulationException {
		/*cp.parkVehicle(car1, 10, 50);
		cp.unparkVehicle(car1, 60);
		cp.archiveDepartingVehicles(60, true);
		assertTrue(cp.carParkEmpty() == true);*/
		cp.enterQueue(car8);
		cp.archiveQueueFailures(180);
		assertTrue(cp.past.size() == 1);
		
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkEmpty()}.
	 */
	@Test
	public void testCarParkEmpty() {
		assertTrue(cp.carParkEmpty() == true);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkFull()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testCarParkNotEmpty() throws SimulationException, VehicleException{
		cp.parkVehicle(car4, 20, 40);
		assertTrue(cp.carParkEmpty() == false);
	}
	
	
	
	@Test
	public void testCarParkFull() throws SimulationException, VehicleException {
		this.cp = new CarPark(0,3,0,Constants.DEFAULT_MAX_QUEUE_SIZE);
		cp.parkVehicle(car1, 10, 40);
		cp.parkVehicle(car2, 10, 40);
		cp.parkVehicle(car3, 10, 40);
		assertTrue(cp.carParkFull() == true);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#enterQueue(asgn2Vehicles.Vehicle)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testEnterQueue() throws SimulationException, VehicleException {
		cp.enterQueue(car7);
		assertTrue(cp.queueEmpty() == false);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#exitQueue(asgn2Vehicles.Vehicle, int)}.
	 */
	@Test
	public void testExitQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#finalState()}.
	 */
	@Test
	public void testFinalState() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumCars()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testGetNumCars() throws SimulationException, VehicleException {
		cp.parkVehicle(car1, 10, 40);
		cp.parkVehicle(car2, 10, 40);
		cp.parkVehicle(car3, 10, 40);
		cp.parkVehicle(car4, 10, 40);
		cp.parkVehicle(car5, 10, 40);
		assertTrue(cp.getNumCars() == 5);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumMotorCycles()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testGetNumMotorCycles() throws SimulationException, VehicleException {
	cp.parkVehicle(mc1, 10, 50);
	cp.parkVehicle(car1, 10, 50);
	assertTrue(cp.getNumMotorCycles() == 1);
	
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumSmallCars()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testGetNumSmallCars() throws SimulationException, VehicleException {
	cp.parkVehicle(car1, 10, 50);
	cp.parkVehicle(car2, 10, 50);
	cp.parkVehicle(mc1,10,50);
	cp.parkVehicle(car5, 10, 50);
	cp.parkVehicle(car6, 10, 50);
	assertTrue(cp.getNumSmallCars() == 2);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getStatus(int)}.
	 */
	@Test
	public void testGetStatus() {
		fail("Not yet implemented"); // TODO
	}


	/**
	 * Test method for {@link asgn2CarParks.CarPark#numVehiclesInQueue()}.
	 */
	@Test
	public void testNumVehiclesInQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#parkVehicle(asgn2Vehicles.Vehicle, int, int)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testParkVehicle() throws SimulationException, VehicleException {
		cp.parkVehicle(car1, 20, 60);
		assertTrue(cp.carParkEmpty() == false);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#processQueue(int, asgn2Simulators.Simulator)}.
	 */
	@Test
	public void testProcessQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueEmpty()}.
	 */
	@Test
	public void testQueueEmpty() {
		assertTrue(cp.queueEmpty() == true);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueFull()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueFull() throws SimulationException, VehicleException {
		cp.enterQueue(car1);
		cp.enterQueue(car2);
		cp.enterQueue(car3);
		cp.enterQueue(car4);
		cp.enterQueue(car5);
		cp.enterQueue(mc1);
		cp.enterQueue(mc2);
		cp.enterQueue(mc3);
		cp.enterQueue(mc4);
		cp.enterQueue(mc5);
		assertTrue(cp.queueFull() == true);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#spacesAvailable(asgn2Vehicles.Vehicle)}.
	 */
	@Test
	public void testSpacesAvailable() {
		assertTrue(cp.spacesAvailable(car1) == true);
	}
	
	@Test
	public void testSpacesNotAvailable() throws SimulationException, VehicleException{
		this.cp = new CarPark(8,3,2,Constants.DEFAULT_MAX_QUEUE_SIZE);
		cp.parkVehicle(car1, 10, 60);
		cp.parkVehicle(car2, 10, 60);
		cp.parkVehicle(car3, 10, 60);
		cp.parkVehicle(mc1, 10, 120);
		cp.parkVehicle(mc2, 10, 120);
		assertTrue(cp.spacesAvailable(mc5) == false);
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#tryProcessNewVehicles(int, asgn2Simulators.Simulator)}.
	 */
	@Test
	public void testTryProcessNewVehicles() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#unparkVehicle(asgn2Vehicles.Vehicle, int)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testUnparkVehicle() throws SimulationException, VehicleException {
		cp.parkVehicle(car1,10,50);
		cp.unparkVehicle(car1, 60);
		assertTrue(cp.carParkEmpty() == true);
		
	}

}
