package com.example.demo.files.repositories;

import com.example.demo.files.entities.AppFishFile;
import com.example.demo.files.entities.enumeration.DocType;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<AppFishFile, Long> {

    AppFishFile findByUserAndType(User user, DocType type);

    AppFishFile findByFileName(String fileName);
}
