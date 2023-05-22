package com.syberry.magazine.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * A class that represents Step entity.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Step extends BaseEntity {
  @Column(nullable = false)
  private String description;

  @OneToOne(cascade = CascadeType.ALL)
  private Photo photo;

  public Step(String description) {
    this.description = description;
  }
}
