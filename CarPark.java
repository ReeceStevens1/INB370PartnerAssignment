/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2CarParks 
 * 21/04/2014
 * 
 */
package asgn2CarParks;

import java.util.ArrayList;
import java.util.Iterator;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * The CarPark class provides a range of facilities for working with a car park in support 
 * of the simulator. In particular, it maintains a collection of currently parked vehicles, 
 * a queue of vehicles wishing to enter the car park, and an historical list of vehicles which 
 * have left or were never able to gain entry. 
 * 
 * The class maintains a wide variety of constraints on small cars, normal cars and motorcycles 
 * and their access to the car park. See the method javadoc for details. 
 * 
 * The class relies heavily on the asgn2.Vehicle hierarchy, and provides a series of reports 
 * used by the logger. 
 * 
 * @author hogan
 *
 */
public class CarPark {
	
	/**
	 * CarPark constructor sets the basic size parameters. 
	 * Uses default parameters
	 */
	
	public CarPark c;
	private int maxCarSpaces, maxSmallCarSpaces, maxMotorCycleSpaces, maxQueueSize;
	private int count;
	public ArrayList<Vehicle> spaces;
	public ArrayList<Vehicle> queue;
	public ArrayList<Vehicle> past;
	private int numDissatisfied = 0;
	private String status;
	
	public CarPark() {
		this(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,
				Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
	}
		
	/**
	 * CarPark constructor sets the basic size parameters. 
	 * @param maxCarSpaces maximum number of spaces allocated to cars in the car park 
	 * @param maxSmallCarSpaces maximum number of spaces (a component of maxCarSpaces) 
	 * 						 restricted to small cars
	 * @param maxMotorCycleSpaces maximum number of spaces allocated to MotorCycles
	 * @param maxQueueSize maximum number of vehicles allowed to queue
	 */
	//@Author - Reece Stevens
	public CarPark(int maxCarSpaces,int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize) {
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;
		spaces = new ArrayList<Vehicle>();
		queue = new ArrayList<Vehicle>();
		past = new ArrayList<Vehicle>();
		
	}

	/**
	 * Archives vehicles exiting the car park after a successful stay. Includes transition via 
	 * Vehicle.exitParkedState(). 
	 * @param time int holding time at which vehicle leaves
	 * @param force boolean forcing departure to clear car park 
	 * @throws VehicleException if vehicle to be archived is not in the correct state 
	 * @throws SimulationException if one or more departing vehicles are not in the car park when operation applied
	 */
	//@Author - Reece Stevens
	public void archiveDepartingVehicles(int time,boolean force) throws VehicleException, SimulationException {
		if(force){
			for(int i = 0; i < this.spaces.size(); i++) {
				Vehicle thisVehicle = this.spaces.get(i);
				if (!thisVehicle.isParked()){
					throw new VehicleException("The vehicle is not in the right state to be archived");
				}
				else {
					this.past.add(thisVehicle);
					this.unparkVehicle(thisVehicle, time);
				}
			}
		}
		else {
			for(int i = 0; i < this.spaces.size(); i++) {
				Vehicle thisVehicle = this.spaces.get(i);
				if (!thisVehicle.isParked()){
					throw new VehicleException("The vehicle is not in the right state to be archived");
				}
				else if (thisVehicle.getDepartureTime() + thisVehicle.getArrivalTime() == time) {
					this.past.add(thisVehicle);
					this.unparkVehicle(thisVehicle, time);
				}
				else {

				}
			}
		}
	}
		
	/**
	 * Method to archive new vehicles that don't get parked or queued and are turned 
	 * away
	 * @param v Vehicle to be archived
	 * @throws SimulationException if vehicle is currently queued or parked
	 */
	//@Author - Reece Stevens
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
		if (v.isParked() == true || v.isQueued() == true) {
			throw new SimulationException("The vehicle is either already parked or queued");
		}
		else {
			this.past.add(v);
		}
	}
	
	/**
	 * Archive vehicles which have stayed in the queue too long 
	 * @param time int holding current simulation time 
	 * @throws VehicleException if one or more vehicles not in the correct state or if timing constraints are violated
	 */
	//@Author - Reece Stevens
	public void archiveQueueFailures(int time) throws VehicleException {
			for (int i = 0; i <queue.size(); i++) {
			Vehicle thisVehicle = queue.get(i);
			if ((time-(thisVehicle.getArrivalTime())) >= Constants.MAXIMUM_QUEUE_TIME) {
				this.numDissatisfied++;
				past.add(thisVehicle);
				thisVehicle.exitQueuedState(time);
				queue.remove(i);
			}
		}
	}
	
	/**
	 * Simple status showing whether carPark is empty
	 * @return true if car park empty, false otherwise
	 */
	//@Author - Reece Stevens
	public boolean carParkEmpty() {
		if (this.spaces.size() <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Simple status showing whether carPark is full
	 * @return true if car park full, false otherwise
	 */
	//@Author - Reece Stevens
	public boolean carParkFull() {
		if (this.spaces.size() >= this.maxCarSpaces + this.maxSmallCarSpaces + this.maxMotorCycleSpaces){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Method to add vehicle successfully to the queue
	 * Precondition is a test that spaces are available
	 * Includes transition through Vehicle.enterQueuedState 
	 * @param v Vehicle to be added 
	 * @throws SimulationException if queue is full  
	 * @throws VehicleException if vehicle not in the correct state 
	 */
	//@Author - Reece Stevens
	public void enterQueue(Vehicle v) throws SimulationException, VehicleException {
		if (this.queueFull()) {
			throw new SimulationException("The queue is full");
		}
		else if (v.isParked()) {
			throw new VehicleException("The vehicle is not in the correct state");
		}
		else {
			v.enterQueuedState();
			this.queue.add(v);
		}
	}
	
	
	/**
	 * Method to remove vehicle from the queue after which it will be parked or 
	 * removed altogether. Includes transition through Vehicle.exitQueuedState.  
	 * @param v Vehicle to be removed from the queue 
	 * @param exitTime int time at which vehicle exits queue
	 * @throws SimulationException if vehicle is not in queue 
	 * @throws VehicleException if the vehicle is in an incorrect state or timing 
	 * constraints are violated
	 */
	//@Author - Reece Stevens
	public void exitQueue(Vehicle v,int exitTime) throws SimulationException, VehicleException {
		if (v.isQueued() == false) {
			throw new SimulationException("The vehicle is not in the queue");
		}
		else {
			v.exitQueuedState(exitTime);
			this.queue.remove(v);
			}
	}
	
	/**
	 * State dump intended for use in logging the final state of the carpark
	 * All spaces and queue positions should be empty and so we dump the archive
	 * @return String containing dump of final carpark state 
	 */
	//@Author - James Church
	public String finalState() {
		String str = "Vehicles Processed: count:" + 
				this.count + ", logged: " + this.past.size() 
				+ "\nVehicle Record: \n";
		for (Vehicle v : this.past) {
			str += v.toString() + "\n\n";
		}
		return str + "\n";
	}
	
	/**
	 * Simple getter for number of cars in the car park 
	 * @return number of cars in car park, including small cars
	 */
	//@Author - James Church
	public int getNumCars() {
		int count = 0;
		for (Vehicle v: this.spaces) {
			if (v instanceof Car)
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Simple getter for number of motorcycles in the car park 
	 * @return number of MotorCycles in car park, including those occupying 
	 * 			a small car space
	 */
	//@Author - James Church
	public int getNumMotorCycles() {
		int count = 0;
		for (Vehicle v: this.spaces) {
			if (v instanceof MotorCycle)
			{
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Simple getter for number of small cars in the car park 
	 * @return number of small cars in car park, including those 
	 * 		   not occupying a small car space. 
	 */
	//@Author - James Church
	public int getNumSmallCars() {
		int count = 0;
		for (Vehicle v: this.spaces) {
			if (v instanceof Car)
			{
				if(((Car)v).isSmall()) {
				count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * Method used to provide the current status of the car park. 
	 * Uses private status String set whenever a transition occurs. 
	 * Example follows (using high probability for car creation). At time 262, 
	 * we have 276 vehicles existing, 91 in car park (P), 84 cars in car park (C), 
	 * of which 14 are small (S), 7 MotorCycles in car park (M), 48 dissatisfied (D),
	 * 176 archived (A), queue of size 9 (CCCCCCCCC), and on this iteration we have 
	 * seen: car C go from Parked (P) to Archived (A), C go from queued (Q) to Parked (P),
	 * and small car S arrive (new N) and go straight into the car park<br>
	 * 262::276::P:91::C:84::S:14::M:7::D:48::A:176::Q:9CCCCCCCCC|C:P>A||C:Q>P||S:N>P|
	 * @return String containing current state 
	 */
	//@Author - James Church
	public String getStatus(int time) {
		String str = time +"::"
		+ this.count + "::" 
		+ "P:" + this.spaces.size() + "::"
		+ "C:" + this.getNumCars() + "::S:" + this.getNumSmallCars() 
		+ "::M:" + this.getNumMotorCycles() 
		+ "::D:" + this.numDissatisfied 
		+ "::A:" + this.past.size()  
		+ "::Q:" + this.queue.size(); 
		for (Vehicle v : this.queue) {
			if (v instanceof Car) {
				if (((Car)v).isSmall()) {
					str += "S";
				} else {
					str += "C";
				}
			} else {
				str += "M";
			}
		}
		str += this.status;
		this.status="";
		return str+"\n";
	}
	

	/**
	 * State dump intended for use in logging the initial state of the carpark.
	 * Mainly concerned with parameters. 
	 * @return String containing dump of initial carpark state 
	 */
	//@Author - James Church
	public String initialState() {
		return "CarPark [maxCarSpaces: " + this.maxCarSpaces
				+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces 
				+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces 
				+ " maxQueueSize: " + this.maxQueueSize + "]";
	}

	/**
	 * Simple status showing number of vehicles in the queue 
	 * @return number of vehicles in the queue
	 */
	//@Author - James Church
	public int numVehiclesInQueue() {
		
		return this.queue.size();
	}
	
	/**
	 * Method to add vehicle successfully to the car park store. 
	 * Precondition is a test that spaces are available. 
	 * Includes transition via Vehicle.enterParkedState.
	 * @param v Vehicle to be added 
	 * @param time int holding current simulation time
	 * @param intendedDuration int holding intended duration of stay 
	 * @throws SimulationException if no suitable spaces are available for parking 
	 * @throws VehicleException if vehicle not in the correct state or timing constraints are violated
	 */
	//@Author - James Church
	public void parkVehicle(Vehicle v, int time, int intendedDuration) throws SimulationException, VehicleException {
		if (!spacesAvailable(v)) {
			throw new SimulationException("There are no suitable car spaces for this car");
		}
		else if(v.isQueued()) {
			v.exitQueuedState(time);
		}
		else if (v.isParked()) {
			throw new VehicleException("The vehicle is not in the right state to be parked or has incorrect timing");
		}
		else {
			this.spaces.add(v);
			v.enterParkedState(time, intendedDuration);
		}
	}

	/**
	 * Silently process elements in the queue, whether empty or not. If possible, add them to the car park. 
	 * Includes transition via exitQueuedState where appropriate
	 * Block when we reach the first element that can't be parked. 
	 * @param time int holding current simulation time 
	 * @throws SimulationException if no suitable spaces available when parking attempted
	 * @throws VehicleException if state is incorrect, or timing constraints are violated
	 */
	//@Author - James Church
	public void processQueue(int time, Simulator sim) throws VehicleException, SimulationException {
		for(int i=0;i<queue.size();i++) {
			Vehicle v = queue.get(i);
			if(!spacesAvailable(v)) {
				throw new SimulationException("There are no suitable spaces for parking.");
			}
			else if (v.isParked() || v.getArrivalTime() >= time) {
				throw new VehicleException("The car is not in the right state or the timing is wrong");
			}
			else {
				this.exitQueue(v, time);
				this.parkVehicle(v, time, (int) Constants.DEFAULT_INTENDED_STAY_MEAN);
			}
		}
	}

	/**
	 * Simple status showing whether queue is empty
	 * @return true if queue empty, false otherwise
	 */
	//@Author - James Church
	public boolean queueEmpty() {
		if (this.queue.size() <= 0) {
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Simple status showing whether queue is full
	 * @return true if queue full, false otherwise
	 */
	//@Author - James Church
	public boolean queueFull() {
		if (this.queue.size() == maxQueueSize) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Method determines, given a vehicle of a particular type, whether there are spaces available for that 
	 * type in the car park under the parking policy in the class header.  
	 * @param v Vehicle to be stored. 
	 * @return true if space available for v, false otherwise 
	 */
	//@Author - Reece Stevens
	public boolean spacesAvailable(Vehicle v) {
		if (this.carParkFull() == true) {
			return false;
		}
		else if(v instanceof Car) {
			if(this.getNumCars() < this.maxCarSpaces) {
				return true;
			}
		}
		else if(v instanceof Car) {
			if(((Car)v).isSmall()) {
				if((this.getNumSmallCars() < this.maxSmallCarSpaces) || (this.getNumCars() < this.maxCarSpaces)) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else if (v instanceof MotorCycle) {
			if((this.getNumSmallCars() < this.maxSmallCarSpaces) || (this.getNumMotorCycles() < this.maxMotorCycleSpaces)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return null;
	}

	/**
	 * Method to try to create new vehicles (one trial per vehicle type per time point) 
	 * and to then try to park or queue (or archive) any vehicles that are created 
	 * @param sim Simulation object controlling vehicle creation 
	 * @throws SimulationException if no suitable spaces available when operation attempted 
	 * @throws VehicleException if vehicle creation violates constraints 
	 */
	//@Author - James Church
	public void tryProcessNewVehicles(int time,Simulator sim) throws VehicleException, SimulationException {
			if (sim.newCarTrial()) {
				count++;
				Car c = new Car("C"+this.count , time, false);
				if(spacesAvailable(c)){
					parkVehicle(c, time, (int)Constants.DEFAULT_INTENDED_STAY_MEAN);
				}
				else if (!queueFull()) {
					enterQueue(c);
				}
				else {
					archiveNewVehicle(c);
				}
			}
			if (sim.smallCarTrial()) {
				count++;
				Car sc = new Car("SC"+this.count , time, true);
				if(spacesAvailable(sc)){
					parkVehicle(sc, time, (int)Constants.DEFAULT_INTENDED_STAY_MEAN);
				}
				else if (!queueFull()) {
					enterQueue(sc);
				}
				else {
					archiveNewVehicle(sc);
				}
			}
			if (sim.motorCycleTrial()) {
				count++;
				MotorCycle m = new MotorCycle("M"+this.count , time);
				if(spacesAvailable(m)){
					parkVehicle(m, time, (int)Constants.DEFAULT_INTENDED_STAY_MEAN);
				}
				else if (!queueFull()) {
					enterQueue(m);
				}
				else {
					archiveNewVehicle(m);
				}
			}
	}

	/**
	 * Method to remove vehicle from the carpark. 
	 * For symmetry with parkVehicle, include transition via Vehicle.exitParkedState.  
	 * So vehicle should be in parked state prior to entry to this method. 
	 * @param v Vehicle to be removed from the car park 
	 * @throws VehicleException if Vehicle is not parked, is in a queue, or violates timing constraints 
	 * @throws SimulationException if vehicle is not in car park
	 */
	//@Author - James Church
	public void unparkVehicle(Vehicle v,int departureTime) throws VehicleException, SimulationException {
		if (((v.isParked() == false) || (v.isQueued() == true))) {
			throw new VehicleException("The vehicle either isnt parked, is in the queue, or the departure time is incorrect");
		}
		else {
			this.spaces.remove(v);
			v.exitParkedState(departureTime);
		}
	}
	
	/**
	 * Helper to set vehicle message for transitions 
	 * @param v Vehicle making a transition (uses S,C,M)
	 * @param source String holding starting state of vehicle (N,Q,P,A) 
	 * @param target String holding finishing state of vehicle (Q,P,A) 
	 * @return String containing transition in the form: |(S|C|M):(N|Q|P|A)>(Q|P|A)| 
	 */
	//@Author - James Church
	private String setVehicleMsg(Vehicle v,String source, String target) {
		String str="";
		if (v instanceof Car) {
			if (((Car)v).isSmall()) {
				str+="S";
			} else {
				str+="C";
			}
		} else {
			str += "M";
		}
		return "|"+str+":"+source+">"+target+"|";
	}
}
