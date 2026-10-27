package com.example.demo.repository;

import com.example.demo.model.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUsername(String username);

    Page<UserProfile> findAllByActiveTrue(Pageable pageable);

    Page<UserProfile> findAllByCity(String city, Pageable pageable);

    @Query("SELECT DISTINCT u FROM UserProfile u JOIN u.stack s " +
            "WHERE (:firstName IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT(:firstName, '%'))) " +
            "AND (:lastName IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT(:lastName, '%'))) " +
            "AND (:stack IS NULL OR LOWER(s) LIKE LOWER(CONCAT(:stack, '%')))")
    Page<UserProfile> searchByFilters(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("stack") String stack,
            Pageable pageable
    );

    @Query("SELECT DISTINCT u FROM UserProfile u LEFT JOIN u.stack s " +
            "WHERE (:firstName IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT(:firstName, '%'))) " +
            "AND (:lastName IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT(:lastName, '%'))) " +
            "AND (:stack IS NULL OR LOWER(s) LIKE LOWER(CONCAT(:stack, '%')))")
    Page<UserProfile> searchByFiltersV2(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("stack") String stack,
            Pageable pageable
    );
}
