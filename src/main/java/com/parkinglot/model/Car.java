package com.parkinglot.model;

public class Car {
	  
	
	  String color;
	  String registrationNumber;
	  
	  
	  public Car()
	  {
		  
	  }
	  
	  public Car(String color,String registrationNumber)
	  {
	  	this.color= color;
	  	this.registrationNumber = registrationNumber;
	  }


	  public String getColor() {
			return color;
		}
		
	  public String getRegistrationNumber() {
			return registrationNumber;
	   }
		 
		 public void setCarColors(String carColor) {
			 this.color = carColor;
		 }
		 
		 
		 public void setRegistrationNumber(String registrationNum) {
		   	 this.registrationNumber = registrationNum;
		   }
	}