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
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

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
			 String slotNumber = "{\"slotNumber\":\"10\"}";

	        when(parkingLot.initializeSlot(10)).thenReturn(slotList);

	        mockMvc.perform(post("/initiateLot")
	        		.contentType(MediaType.APPLICATION_JSON)
	                .content(slotNumber) 
	                .accept(MediaType.APPLICATION_JSON)) 
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].slotNumber").value("1"))
	                .andExpect(jsonPath("$[0].slotFree").value("true"));
	        verify(parkingLot, times(1)).initializeSlot(10);
	        verifyNoMoreInteractions(parkingLot);

	    }
	 @Test
	    public void testParkCar() throws Exception
	    {
		 String car = "{\"color\":\"Blue\",\"registrationNumber\":\"123\"}";
	        Token token = new Token("XYZ123123",new Slot(1),new Car("Blue","123"));
	        when(parkingLot.parkCar("Blue","123")).thenReturn(token);
	        mockMvc.perform(post("/parkCar")
	        		.contentType(MediaType.APPLICATION_JSON)
	                .content(car) 
	                .accept(MediaType.APPLICATION_JSON)      
	                )
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

	                .andExpect(jsonPath("$['carDetails'].color").value("Blue"))
	                .andExpect(jsonPath("$['carDetails'].registrationNumber").value("123"))
	                .andExpect(jsonPath("$.tokenNumber").value("XYZ123123"));
	        verify(parkingLot, times(1)).parkCar("Blue","123");
	        verifyNoMoreInteractions(parkingLot);

	    }
	 @Test
	    public void testUnPark() throws Exception
        { 
	    
	        Token token = new Token("XYZ123123",new Slot(1),new Car("Blue","123"));


	        when(parkingLot.unParkTheCar("XYZ123123")).thenReturn(token);

	        mockMvc.perform(delete("/unParkCar/")
	                          .param("token","XYZ123123"))
	                        .andExpect(status().isOk())
	                        .andExpect(jsonPath("$['carDetails'].color").value("Blue"))
	    	                .andExpect(jsonPath("$['carDetails'].registrationNumber").value("123"))
	    	                .andExpect(jsonPath("$.tokenNumber").value("XYZ123123"));

	        verify(parkingLot, times(1)).unParkTheCar("XYZ123123");
	        verifyNoMoreInteractions(parkingLot);

	    }
	 @Test
	    public void testForParkCarWithOutSlots() throws Exception 
	    {
		 parkingLot.initializeSlot(0);
		 String car = "{\"color\":\"Blue\",\"registrationNumber\":\"123\"}";
	        Token token = new Token("XYZ123123",new Slot(null),new Car("Blue","123"));
	        when(parkingLot.parkCar("Blue","123")).thenReturn(token);
	        mockMvc.perform(post("/parkCar")
	        		.contentType(MediaType.APPLICATION_JSON)
	                .content(car) 
	                .accept(MediaType.APPLICATION_JSON)      
	                )
	                .andExpect(status().isBadRequest())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.message").value("No Slot Available"));
	               
	        verify(parkingLot, times(1)).parkCar("Blue","123");
	        verifyNoMoreInteractions(parkingLot);

	    }

	 @Test
	    public void searchCarByRegNo() throws Exception 
	    {
	        Token token = new Token("123123",new Slot(123),new Car("Blue","123"));

	        when(parkingLot.searchCarNumber("123")).thenReturn(token);

	        mockMvc.perform(get("/searchCarByNum/{carNum}", "123"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.tokenNumber", is("123123")))
	                .andExpect(jsonPath("$['carDetails'].registrationNumber", is("123"))
	                );

	        verify(parkingLot, times(1)).searchCarNumber("123");
	        verifyNoMoreInteractions(parkingLot);

	    }
	   
}
