package com.wanted.lunchmapservice.restaurant.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class RawRestaurantId implements Serializable {

  @Column(name = "name", nullable = false, updatable = false)
  private String name;

  @Column(name = "lot_number_address", nullable = false, updatable = false)
  private String lotNumberAddress;
}
