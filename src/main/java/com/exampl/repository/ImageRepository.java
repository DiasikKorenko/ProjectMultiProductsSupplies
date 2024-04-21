package com.exampl.repository;

import com.exampl.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findAllByProductId(int productId);

    ImageEntity findByProductIdAndId(int productId, Long imageId);
}
