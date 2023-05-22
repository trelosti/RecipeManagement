package com.syberry.magazine.repository;

import com.syberry.magazine.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for Recipe entity.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

  /**
   * Returns Page with recipes sorted in descending order.
   *
   * @param pageable information about requested page
   * @return page with recipes
   */
  Page<Recipe> findAllByOrderByUpdatedAtDesc(Pageable pageable);

  /**
   * Returns Page with recipes by specified author id and sorted in descending order.
   *
   * @param id author id
   * @param pageable information about requested page
   * @return page with recipes
   */
  Page<Recipe> findAllByAuthorIdOrderByUpdatedAtDesc(Long id, Pageable pageable);

  /**
   * Returns Page with saved recipes by specified user id and sorted in descending order.
   *
   * @param id authenticated user id
   * @param pageable information about requested page
   * @return page with saved recipes
   */
  Page<Recipe> findAllBySavesIdOrderByUpdatedAtDesc(Long id, Pageable pageable);
}
