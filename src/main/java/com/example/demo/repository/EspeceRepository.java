package com.example.demo.repository;

import com.example.demo.models.Espece;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspeceRepository extends JpaRepository<Espece, Long> {

    @Query(value = "SELECT * FROM espèces where user_id=?", nativeQuery = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

    public List<Espece> findAllByCreatedBy(Long userId);

    public List<Espece> findByEspeceName(String especeName);

    public List<Espece> findByEspeceFamily(String especeFamily);

    public Espece findById(long id);

    public List<Espece> findAll();

    public long count();

}
