package com.parkinglot;

import static org.junit.Assert.*;

import org.junit.Test;

import com.parkinglot.model.Car;

public class CarTest {

	@Test
	public void checkwithGivenCarColor() {
		Car c = new Car("White","KA-01-HH-3141");
		assertEquals("White", c.getColor()); 
	}
	@Test
	public void checkwithGivenCarRegNo() {
		Car c = new Car("White","KA-01-HH-3141");
		assertEquals("KA-01-HH-3141", c.getRegistrationNumber()); 
	}

}
