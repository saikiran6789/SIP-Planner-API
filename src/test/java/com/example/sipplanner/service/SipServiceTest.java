package com.example.sipplanner.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SipServiceTest {

    SipService svc = new SipService();

    @Test
    void testCalculateZeroRate() {
        double[] r = svc.calculateSip(1000, 0.0, 1);
        assertEquals(12000.0, r[0], 0.01);
        assertEquals(12000.0, r[1], 0.01);
        assertEquals(0.0, r[2], 0.01);
    }

    @Test
    void testCalculateTypical() {
        double[] r = svc.calculateSip(5000, 12.0, 10);
        // basic sanity checks
        assertTrue(r[0] > r[1]);
        assertEquals(r[0], r[1] + r[2], 0.01);
    }

    @Test
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> svc.calculateSip(0, 10, 5));
        assertThrows(IllegalArgumentException.class, () -> svc.calculateSip(1000, 10, 0));
    }

    @Test
    void testPortfolioHealth() {
        assertEquals("Aggressive", svc.portfolioHealth(80,20));
        assertEquals("Balanced", svc.portfolioHealth(50,50));
        assertEquals("Conservative", svc.portfolioHealth(20,80));
        assertEquals("No allocation", svc.portfolioHealth(0,0));
        assertThrows(IllegalArgumentException.class, () -> svc.portfolioHealth(-1,50));
    }
}
