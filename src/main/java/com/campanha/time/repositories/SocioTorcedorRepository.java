package com.campanha.time.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campanha.time.entities.SocioTorcedor;

@Repository
public interface SocioTorcedorRepository extends JpaRepository<SocioTorcedor, Long>{

}
