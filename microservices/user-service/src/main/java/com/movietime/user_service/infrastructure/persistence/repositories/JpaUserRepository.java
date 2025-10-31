package com.movietime.user_service.infrastructure.persistence.repositories;

import com.movietime.user_service.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {

}