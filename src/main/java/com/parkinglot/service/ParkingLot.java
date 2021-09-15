package com.parkinglot.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.parkinglot.model.Car;
import com.parkinglot.model.Slot;
import com.parkinglot.model.Token;

@Service
public class ParkingLot {
    private int totalNumberOfSlots;
    ArrayList<Slot> availableSlotList;
    private List<Token> tokenForLot;
    private List<Token> historyOfParking;
    private ArrayList<Slot> totalSlots;
    
    public ParkingLot(){
      
        this.tokenForLot = new ArrayList<>();
        this.historyOfParking = new ArrayList<>();

       
    }
    
  
        public ArrayList<Slot> initializeSlot(int totalNumberOfSlot){

        this.totalSlots = new ArrayList<Slot>() {};
        for (int i=1; i<= totalNumberOfSlot; i++) {
            Slot getSlotAssignment = new Slot(i);
            totalSlots.add(getSlotAssignment);
        }
        return this.availableSlotList = totalSlots;

    }
        
        public Token parkCar(String color, String registrationNum) throws Exception{
            Car car = new Car(color,registrationNum);
            if(isSlotAvailable()){
               Slot availableSlot = getTheNextFreeSlot();
               Token parkingToken = new Token(String.valueOf(System.currentTimeMillis()),availableSlot,car);
               this.tokenForLot.add(parkingToken);
               return parkingToken;
            }else {
          	  throw new Exception("Sorry, parking lot is full");
            }
         }

    public String unParkTheCar(String token){
        for(Token tokenInLot:tokenForLot){
            if(tokenInLot.getTokenNumber().equals(token)){
                tokenForLot.remove(tokenInLot);
                Slot slot = tokenInLot.getSlotDetails();
                int slotNumber = slot.getSlotNumber();
                return removeCarFromSlot(tokenInLot,slotNumber);
            }
            return "No token found";
        }
        return null;
    }

    private String removeCarFromSlot(Token token, int slotNumber) {
        for (Slot removeEntry:availableSlotList){

            if(removeEntry.getSlotNumber() == slotNumber){
                removeEntry.makeSlotFree();
                Token historyToken = token.updateCheckOutTime();
                historyOfParking.add(historyToken);
                return "Car entry removed";
            }

        }
        return null;
    }

    private Slot getTheNextFreeSlot() {
        for(Slot slot : availableSlotList){
            if(slot.isSlotFree()){
                slot.makeSlotOccupied();
                return slot;
            }
        }
        return null;
    }
    public String searchCarNumber(String carNumber){
        for(Token tokenSearch:tokenForLot){
            String carDetails = tokenSearch.getCarDetails().getRegistrationNumber();
            if(carDetails.equalsIgnoreCase(carNumber)){
                return "Token Number: " +tokenSearch.getTokenNumber()+"\nSlot Number: " +tokenSearch.getSlotDetails().getSlotNumber()+"\nCar Color: " +tokenSearch.getCarDetails().getColor();
            }
        }
        return "Car Not Found";
    }
    public String searchCarColor(String colour){
        for(Token tokenSearch:tokenForLot){
            String carDetails = tokenSearch.getCarDetails().getColor();
            if(carDetails.equalsIgnoreCase(colour)){
                return "Token Number: " +tokenSearch.getTokenNumber()+"\nSlot Number: " +tokenSearch.getSlotDetails().getSlotNumber()+"\nCar reg no: " +tokenSearch.getCarDetails().getRegistrationNumber();
            }
        }
        return "Car Not Found";
    }
    private boolean isSlotAvailable() {
        boolean isSlotAvailable = false;

        for(Slot slot:availableSlotList){
            if(slot.isSlotFree()){
                isSlotAvailable = true;
                break;
            }
        }
        return isSlotAvailable;
    }
    public String listAllCars() throws Exception{
        for(Token tokenSearch:tokenForLot){
            String carDetails ="\nToken Number: " +tokenSearch.getTokenNumber()+"\nSlot Number: " +tokenSearch.getSlotDetails().getSlotNumber()+"\nCar Color: " +tokenSearch.getCarDetails().getColor();
            return "\nToken Number: " +tokenSearch.getTokenNumber()+"\nSlot Number: " +tokenSearch.getSlotDetails().getSlotNumber()+"\nCar Color: " +tokenSearch.getCarDetails().getColor();
        }
       // return "No cars parked";
        throw new Exception("No car found");
    }
  

    
	public List<Token>  showListOfCarDetails() throws Exception {
		for (Token i : tokenForLot) {
			System.out.println("Token Number: " + i.getTokenNumber());
			System.out.println("Slot Number: " + i.getSlotDetails().getSlotNumber());
			System.out.println("car color: " + i.getCarDetails().getColor());
        	System.out.println("Car Number: " + i.getCarDetails().getRegistrationNumber());
		}
		throw new Exception("No car found");
		
	}

	

}