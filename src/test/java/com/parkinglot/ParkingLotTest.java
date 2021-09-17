package com.parkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.parkinglot.model.Token;
import com.parkinglot.service.ParkingLot;
import com.parkinglot.exceptions.CustomException;
import com.parkinglot.model.Slot;

public class ParkingLotTest {
	private ParkingLot parkinglot = new ParkingLot();
	
	@Test
   public void  testToInitializeASlotWithTwoSlots(){
        ArrayList<Slot> availableSlot = parkinglot.initializeSlot(2);
        assertTrue(availableSlot.size()>0);
    }
	@Test    
	public void testParkACar() { 
		 parkinglot.initializeSlot(10);
	        Token token = parkinglot.parkCar("Blue","ABC");
	        assertFalse(token.getTokenNumber().isBlank());
	    }

	    @Test
	    public void testUnParkACar() {
	    	parkinglot.initializeSlot(10);
	        Token Token = parkinglot.parkCar("Blue","ABC");
	        Token returntoken = parkinglot.unParkTheCar(Token.getTokenNumber());
	        assertFalse(returntoken.getTokenNumber().isBlank());
	    }

	 

	    @Test
	    public void testForColorSearch() {
	    	 parkinglot.initializeSlot(10);
	        parkinglot.parkCar("Blue","Ts123");
	        List<Token> searchToken = parkinglot.searchCarColor("Blue");
	        assertNotNull(searchToken);
	    }

	    @Test
	    public void testForCarSearch()  {
	       
	        parkinglot.initializeSlot(10);
	        parkinglot.parkCar("Blue","TS-01-HH-3141");
	        Token searchvalue = parkinglot.searchCarNumber("TS-01-HH-3141");
	        assertNotNull(searchvalue);
	    }

	    @Test
	    public void testToListAllCar() {
	    	 parkinglot.initializeSlot(10);
		     parkinglot.parkCar("Blue","Ap-01-HH-3141");
	        List<Token> searchToken = parkinglot.showListOfCarDetails();
	        assertNotNull(searchToken);
	    }

	    @Test
	    public void testListEmpty(){
	    	   Exception exception = assertThrows(CustomException.class, () -> {

	    	 parkinglot.initializeSlot(10); 
		    parkinglot.showListOfCarDetails();
	    	   });
		    String expectedMessage = "Parking Lot is empty";
    	    String actualMessage = exception.getMessage();
    	    assertTrue(actualMessage.contains(expectedMessage));
	    }
	    @Test
	    public void testsearchCarColorWithInvalidCar() {
	    	   Exception exception = assertThrows(CustomException.class, () -> {
	    		   parkinglot.initializeSlot(1);
	    			parkinglot.parkCar("red","Ts-123");
	    		   parkinglot.searchCarColor("blue");
	    	    });
	    	    String expectedMessage = "No car is found";
	    	    String actualMessage = exception.getMessage();
	    	    assertTrue(actualMessage.contains(expectedMessage));
	    }
	    @Test    
		public void testParkACarwWithFullSlots() { 
	    	   Exception exception = assertThrows(CustomException.class, () -> {
			     parkinglot.initializeSlot(1);
		         parkinglot.parkCar("Blue","AP-12345");
			     parkinglot.parkCar("Red","Ts-123");

	    	   });
	    	   String expectedMessage = "No Available Slot";
	    	    String actualMessage = exception.getMessage();
	    	    assertTrue(actualMessage.contains(expectedMessage));
		    }
}
