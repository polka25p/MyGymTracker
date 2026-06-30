package org.gym.service.impl;

import org.gym.service.MembershipService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Implementation of the {@link MembershipService} interface providing business logic
 * for managing gym subscription lifecycles.
 * <p>
 * This class encapsulates temporal calculations to track remaining membership days
 * and evaluate active subscription statuses based on the current system date.
 * </p>
 *
 * @author Polina
 * @version 1.0
 * @see MembershipService
 * @see LocalDate
 * @see ChronoUnit
 */

public class MembershipServiceImpl implements MembershipService {

    /**
     * Calculates the number of days remaining until the next scheduled payment date.
     * <p>
     * The calculation measures the absolute interval between the current server date
     * ({@link LocalDate#now()}) and the provided target expiration threshold.
     * Note that the resulting value can be negative if the payment deadline has already passed.
     * </p>
     *
     * @param nextPaymentDate the {@link LocalDate} representing the upcoming payment deadline;
     * if {@code null}, this method safely returns {@code 0}.
     * @return the total number of days left until the payment date as a {@code long}.
     */

    @Override
    public long calculateDaysLeft(LocalDate nextPaymentDate) {
        if (nextPaymentDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), nextPaymentDate);
    }

    /**
     * Checks whether the gym subscription is currently active.
     * <p>
     * A subscription is considered active if the {@code nextPaymentDate} is either in the
     * future or corresponds exactly to the current calendar date (inclusive). It returns
     * {@code false} only if the target threshold occurs strictly before today.
     * </p>
     *
     * @param nextPaymentDate the {@link LocalDate} representing the membership expiration threshold;
     * if {@code null}, this method safely returns {@code false}.
     * @return {@code true} if the subscription is still valid or due today;
     * {@code false} if it has expired.
     */

    @Override
    public boolean isSubscriptionActive(LocalDate nextPaymentDate) {
        if (nextPaymentDate == null) {
            return false;
        }
        return !nextPaymentDate.isBefore(LocalDate.now());
    }
}