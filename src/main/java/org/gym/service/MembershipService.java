package org.gym.service;

import java.time.LocalDate;

public interface MembershipService {
    long calculateDaysLeft(LocalDate nextPaymentDate);
    boolean isSubscriptionActive(LocalDate nextPaymentDate);
}