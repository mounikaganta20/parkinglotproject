package com.parkinglot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.model.Car;
import com.parkinglot.model.Slot;
import com.parkinglot.model.Token;
import com.parkinglot.service.ParkingLot;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController

public class ParkingLotController {
	

	@Autowired 
	private ParkingLot parkinglot;
	        
	       @PostMapping(path = "parkCar",
		   consumes = MediaType.APPLICATION_JSON_VALUE,
		   produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Object> parkCar(@RequestBody Car car) throws Exception
		{
			return new ResponseEntity<>(parkinglot.parkCar(car.getColor(),car.getRegistrationNumber()),HttpStatus.OK);
		}
	  @GetMapping("/showparkingDetails")
	   public ResponseEntity<Object> showListOfCarDetails() throws Exception
	  {
		return new ResponseEntity<>(parkinglot.showListOfCarDetails(),HttpStatus.OK);
	   }
	  
	  @GetMapping("/searchCarByColor/{carColors}")	 
	  String searchColor(@PathVariable String carColors) {
	  parkinglot.searchCarColor(carColors);
	  return parkinglot.searchCarColor(carColors);
	  }
	  @GetMapping("/searchCarByNum/{carNum}")
	  String searchCarNumber(@PathVariable String carNum) {
		  parkinglot.searchCarNumber(carNum);
		  return parkinglot.searchCarNumber(carNum);
	  }
	 
	   @DeleteMapping("/unParkCar")
	     String unParkTheCar(@RequestParam("token") String token ) {
		 String unparkcar=parkinglot.unParkTheCar(token);
	     System.out.println(unparkcar);
	     return unparkcar;
	    }
	  
	   @GetMapping("/initiateLot")
	    public ArrayList<Slot> initiateLot(@RequestParam("NumberOfLot") String numberOfLot){
	        ArrayList<Slot> availableSlot= parkinglot.initializeSlot(Integer.parseInt(numberOfLot));
	        return  availableSlot;
	    }


}
