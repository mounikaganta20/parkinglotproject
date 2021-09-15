package com.parkinglot;

import org.junit.jupiter.api.Test;

import com.parkinglot.model.Slot;

import static org.junit.jupiter.api.Assertions.*;
public class SlotTest {
    @Test
    public void checkSlot(){
        Slot slot = new Slot(1);
        int slotNumber=slot.getSlotNumber();
        assertEquals(slotNumber,1);
        assertTrue(slot.isSlotFree());
    }
}