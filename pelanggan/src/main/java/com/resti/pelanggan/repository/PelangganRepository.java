package com.resti.pelanggan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.resti.pelanggan.entity.Pelanggan;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {
    List<Pelanggan> findByNamaContainingIgnoreCase(String nama);
}