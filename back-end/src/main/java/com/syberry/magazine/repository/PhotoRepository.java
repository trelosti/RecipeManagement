package com.syberry.magazine.repository;

import com.syberry.magazine.entity.Photo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A repository class for photos.
 */
public interface PhotoRepository extends JpaRepository<Photo, Long> {

  /**
   * Returns Optional with Photo of the photo with the specified name.
   *
   * @param name the name of the photo
   * @return Optional with Photo
   */
  Optional<Photo> findByName(String name);

  /**
   * Returns Optional with Photo of the photo with the specified path.
   *
   * @param path the path to the photo
   * @return Optional with Photo
   */
  Optional<Photo> findByPath(String path);
}
