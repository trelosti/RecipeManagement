package com.syberry.magazine.entity;

import com.syberry.magazine.enumeration.MeasureUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * A class that represents Ingredient entity.
 */
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@Entity
public class Ingredient extends BaseEntity {
  @Column(name = "ingredient_name", length = 100, nullable = false)
  private String ingredientName;
  @Column(nullable = false)
  private Integer quantity;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MeasureUnit measureUnit;
}
