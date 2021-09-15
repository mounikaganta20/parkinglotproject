package com.parkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.parkinglot.model.Token;
import com.parkinglot.service.ParkingLot;
import com.parkinglot.model.Slot;

public class ParkingLotTest {
	private ParkingLot parkinglot = new ParkingLot();
	
	@Test
   public void  testToInitializeASlotWithTwoSlots(){
        ArrayList<Slot> availableSlot = parkinglot.initializeSlot(10);
        assertTrue(availableSlot.size()>0);
    }
	@Test    
	public void testParkACar() throws Exception{ 
		 parkinglot.initializeSlot(10);
	        Token token = parkinglot.parkCar("Blue","ABC");
	        assertFalse(token.getTokenNumber().isBlank());
	    }

	    @Test
	    public void testUnParkACar() throws Exception{
	    	 parkinglot.initializeSlot(10);
	        Token Token = parkinglot.parkCar("Blue","ABC");
	        String unParkMessage = parkinglot.unParkTheCar(Token.getTokenNumber());
	        assertEquals(unParkMessage,"Car entry removed");
	    }

	 

	    @Test
	    public void testForColorSearch() throws Exception{
	    	 parkinglot.initializeSlot(10);
	        parkinglot.parkCar("Blue","Ts123");
	        String searchToken = parkinglot.searchCarColor("Blue");
	        assertNotNull(searchToken);
	    }

	    @Test
	    public void testForCarSearch() throws Exception{
	       
	        parkinglot.initializeSlot(10);
	        parkinglot.parkCar("Blue","TS-01-HH-3141");
	        String searchvalue = parkinglot.searchCarNumber("TS-01-HH-3141");
	        assertNotNull(searchvalue);
	    }

	    @Test
	    public void testToListAllCar() throws Exception{
	    	 parkinglot.initializeSlot(10);
		        parkinglot.parkCar("Blue","Ap-01-HH-3141");
	        String searchToken = parkinglot.listAllCars();
	        assertNotNull(searchToken);
	    }

	    @Test
	    public void testListEmpty() throws Exception{
	    	 parkinglot.initializeSlot(10);
		    String searchCar = parkinglot.listAllCars();
	        assertEquals(searchCar,"No cars parked");
	    }
}
