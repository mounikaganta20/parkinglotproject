package com.parkinglot.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.parkinglot.exceptions.CustomException;
import com.parkinglot.exceptions.ParkingLotException;
import com.parkinglot.exceptions.ResourceNotFoundException;
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
        
        public Token parkCar(String color, String registrationNum) {
            Car car = new Car(color,registrationNum);
            if(isSlotAvailable()){
               Slot availableSlot = getTheNextFreeSlot();
               Token parkingToken = new Token(String.valueOf(System.currentTimeMillis()),availableSlot,car);
               this.tokenForLot.add(parkingToken);
               return parkingToken;
            }else {
            	 throw new CustomException("No Available Slot");
            }
         }

    public Token unParkTheCar(String token){
        for(Token tokenInLot:tokenForLot){
            if(tokenInLot.getTokenNumber().equals(token)){
                tokenForLot.remove(tokenInLot);
                Slot slot = tokenInLot.getSlotDetails();
                int slotNumber = slot.getSlotNumber();
                return removeCarFromSlot(tokenInLot,slotNumber);
            }
            //throw new Exception("No Token found");
            throw new CustomException("No Token found");
        }
        return null;
    }

    private Token removeCarFromSlot(Token token, int slotNumber) {
        for (Slot removeEntry:availableSlotList){

            if(removeEntry.getSlotNumber() == slotNumber){
                removeEntry.makeSlotFree();
                Token historyToken = token.updateCheckOutTime();
                historyOfParking.add(historyToken);
                return token;
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
        throw new CustomException("No Available Slot");
    }
    public Token searchCarNumber(String carNumber){
        for(Token tokenSearch:tokenForLot){
            String carDetails = tokenSearch.getCarDetails().getRegistrationNumber();
            if(carDetails.equalsIgnoreCase(carNumber)){
            	return tokenSearch;
            }
        }
      //  throw new Exception("No car is found");
        throw new CustomException("No car is found");
    }
    public List<Token> searchCarColor(String colour) {
    	List<Token> carList=new ArrayList<Token>(){};
        for(Token tokenSearch:tokenForLot){
            String carDetails = tokenSearch.getCarDetails().getColor();
            if(carDetails.equalsIgnoreCase(colour)){
            	carList.add(tokenSearch);
            }
        }
       if(carList.size()==0)
       {
    	  
    	   throw new CustomException("No car is found");
       }
       else
       {
    	   return carList;
       }
    }
 

    private boolean isSlotAvailable() {
		if(availableSlotList !=null) {
		boolean isSlotAvailable = false;

		for(Slot slot:availableSlotList){
			if(slot.isSlotFree()){
				isSlotAvailable = true;
				break;
			}
		}
		return isSlotAvailable;
		}else {
			throw new CustomException("No Available Slot");
		}
	}

    
	public List<Token>  showListOfCarDetails()  {
		if(tokenForLot.isEmpty()) {
			throw new CustomException("Parking Lot is empty");
		}
		else {
		for (Token i : tokenForLot) {
			System.out.println("Token Number: " + i.getTokenNumber());
			System.out.println("Slot Number: " + i.getSlotDetails().getSlotNumber());
			System.out.println("car color: " + i.getCarDetails().getColor());
        	System.out.println("Car Number: " + i.getCarDetails().getRegistrationNumber());
		}
		return tokenForLot;
		
	}
	}

	

}