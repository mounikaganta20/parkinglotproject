package com.parkinglot.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import com.parkinglot.model.Car;
import com.parkinglot.model.Slot;
import com.parkinglot.model.Token;
import com.parkinglot.service.ParkingLot;
@RunWith(SpringRunner.class)
@WebMvcTest(value = ParkingLotController.class)
class ParkingLotControllerTests {
	   @Autowired  
	    private MockMvc mockMvc;
	    @MockBean
		private ParkingLot parkingLot;
	   
	 @Test
	    public void testInitializeSlot() throws Exception
	    {
	        Slot slot = new Slot(1);
	        ArrayList<Slot> slotList = new ArrayList<Slot>();
	        slotList.add(slot);

	        when(parkingLot.initializeSlot(10)).thenReturn(slotList);

	        mockMvc.perform(get("/initiateLot")
	                .param("NumberOfLot","10"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].slotNumber").value("1"))
	                .andExpect(jsonPath("$[0].slotFree").value("true"));
	        verify(parkingLot, times(1)).initializeSlot(10);
	        verifyNoMoreInteractions(parkingLot);

	    }
	 @Test
	    public void testParkCar() throws Exception
	    {
	        Token token = new Token("XYZ123123",new Slot(123),new Car("Blue","123"));
	        when(parkingLot.parkCar("Blue","123")).thenReturn(token);
	        mockMvc.perform(post("/parkCar")
	                       .param("color","Blue")
	                        .param("registration_number","123")
	                )
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$['carDetails'].color").value("Blue"))
	                .andExpect(jsonPath("$['carDetails'].registrationNumber").value("123"))
	                .andExpect(jsonPath("$.tokenNumber").value("XYZ123123"));
	        verify(parkingLot, times(1)).parkCar("Blue","123");
	        verifyNoMoreInteractions(parkingLot);

	    }
	 @Test
	    public void testUnPark() throws Exception
	    {
	        String responseString = "Car entry removed";

	        when(parkingLot.unParkTheCar("XYZ123123")).thenReturn(responseString);

	        mockMvc.perform(delete("/unParkCar/")
	                          .param("token","XYZ123123"))
	                        .andExpect(status().isOk())
	                        .andExpect(jsonPath("$", is("Car entry removed")));

	        verify(parkingLot, times(1)).unParkTheCar("XYZ123123");
	        verifyNoMoreInteractions(parkingLot);

	    }
		
}
