package com.andesairlaines.checkin.domain.purchase;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "purchaseId")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Purchase")
@Table(name = "purchase")
public class Purchase {

    @Id
    private Long purchaseId;
    private Long purchaseDate;

}
