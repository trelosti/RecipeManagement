package com.syberry.magazine.entity;

import com.syberry.magazine.enumeration.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * A class that represents User entity.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "users")
@SuperBuilder
public class User extends BaseEntity {
  @Column(nullable = false)
  private String login;
  @Column(nullable = false)
  @EqualsAndHashCode.Exclude
  private String password;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @EqualsAndHashCode.Exclude
  private Role role;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "saves")
  private List<Recipe> favoriteRecipes;
  @OneToOne(cascade = CascadeType.ALL)
  private Photo userPhoto;
}
