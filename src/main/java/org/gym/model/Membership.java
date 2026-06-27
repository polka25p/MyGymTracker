package org.gym.model;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Membership {
    private  int id;
    private LocalDate nextPaymentDate;
    private double price;
}
