package org.gym.service.impl;

import org.gym.service.MembershipService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MembershipServiceImpl implements MembershipService {

    @Override
    public long calculateDaysLeft(LocalDate nextPaymentDate) {
        if (nextPaymentDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), nextPaymentDate);
    }

    @Override
    public boolean isSubscriptionActive(LocalDate nextPaymentDate) {
        if (nextPaymentDate == null) {
            return false;
        }
        return !nextPaymentDate.isBefore(LocalDate.now());
    }
}