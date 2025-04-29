package com.mercadona.eanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercadona.eanservice.entity.EanEntity;

public interface EanRepository extends JpaRepository<EanEntity, String> {
}