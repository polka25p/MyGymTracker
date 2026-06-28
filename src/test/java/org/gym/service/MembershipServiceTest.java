package org.gym.service;

import org.gym.service.impl.MembershipServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MembershipServiceTest {

    private final MembershipService membershipService = new MembershipServiceImpl();

    @Test
    public void testCalculateDaysLeft_InFuture() {
        LocalDate nextPayment = LocalDate.now().plusDays(10); // Оплата через 10 днів

        long daysLeft = membershipService.calculateDaysLeft(nextPayment);

        assertEquals(10, daysLeft, "Кількість днів до кінця абонемента порахована неправильно!");
    }

    @Test
    public void testIsSubscriptionActive_ValidAndExpired() {
        LocalDate futureDate = LocalDate.now().plusDays(5);
        assertTrue(membershipService.isSubscriptionActive(futureDate), "Абонемент має бути активним!");

        LocalDate pastDate = LocalDate.now().minusDays(1);
        assertFalse(membershipService.isSubscriptionActive(pastDate), "Абонемент вже мав закінчитися!");
    }
}