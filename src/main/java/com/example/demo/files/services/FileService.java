package com.example.demo.files.services;

import com.example.demo.files.dto.CreateFileDto;
import com.example.demo.files.dto.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public void init();

    public void save(CreateFileDto createFileDto, MultipartFile file) throws Exception;

    public Resource load(String filename);

    public void deleteOneFile();

    public void deleteOneFile(String filename) throws IOException;

    public List<FileDto> findEspeceFile(Long especeId);

    public FileDto findUserFile(Long userId);

}

