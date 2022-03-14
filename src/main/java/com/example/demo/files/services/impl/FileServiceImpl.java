package com.example.demo.files.services.impl;

import com.example.demo.files.dto.CreateFileDto;
import com.example.demo.files.dto.FileDto;
import com.example.demo.files.entities.AppFishFile;
import com.example.demo.files.entities.enumeration.DocType;
import com.example.demo.files.repositories.FileRepository;
import com.example.demo.files.services.FileService;
import com.example.demo.files.webs.FileResource;
import com.example.demo.models.User;
import com.example.demo.repository.EspeceRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
@Primary
public class FileServiceImpl implements FileService {

    private final Path root = Paths.get("uploads");
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    EspeceRepository especeRepository;

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    @Transactional
    public void save(CreateFileDto dto, MultipartFile filePart) throws Exception {
        String filename = filePart.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf('.') + 1);

        AppFishFile appFishFile = AppFishFile.AppFishFileBuilder.aAppFishFileFile()
                .withType(dto.getType())
                .withFileType(dto.getFileType())
                .build();
        try {
            switch (dto.getEntity()) {
                case USER: {
                    Optional<User> user = userRepository.findById(dto.getUserId());
                    if (user.isPresent()) {
                        filename = "APPFISH_MEDIA_" + dto.getEntity().name() + "_" + user.get().getId() + "_" + Calendar.getInstance().getTimeInMillis() + "." + ext;
                        appFishFile.setUser(user.get());
                    }
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FileResource.class, "getFile", filename).build().toString();
                    Files.copy(filePart.getInputStream(), this.root.resolve(filename));
                    appFishFile.setFileName(filename);
                    appFishFile.setUrl(url);
                    fileRepository.save(appFishFile);
                }
            }
        }catch(Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteOneFile() {
        List<AppFishFile> files = fileRepository.findAll();
        FileSystemUtils.deleteRecursively(root.toFile());
        files.forEach(file -> fileRepository.delete(file));
    }

    @Override
    public void deleteOneFile(String filename) throws IOException {
        AppFishFile file = fileRepository.findByFileName(filename);
        fileRepository.delete(file);
        Path oldFile = root.resolve(filename);
        FileSystemUtils.deleteRecursively(oldFile);
    }

    @Override
    public List<FileDto> findEspeceFile(Long especeId) {
        return fileRepository.findByEspece(especeRepository.getOne(especeId)).stream().map(buildFileDtoCollection()).collect(Collectors.toList());
    }


    private Function<AppFishFile, FileDto> buildFileDtoCollection() {
        return appFishFile -> buildFileDto(appFishFile);
    }

    private FileDto buildFileDto(AppFishFile appFishFile) {
        if (Objects.nonNull(appFishFile)) {
            return FileDto.FileDtoBuilder.aFileDto()
                    .withId(appFishFile.getId())
                    .withFileName(appFishFile.getFileName())
                    .withFileType(appFishFile.getFileType())
                    .withUrl(appFishFile.getUrl())
                    .withType(appFishFile.getType())
                    .withUserId(Objects.nonNull(appFishFile.getUser()) ? appFishFile.getUser().getId() : null)
                    .build();
        } else {
            return null;
        }
    }


    @Override
    public FileDto findUserFile(Long userId) {
        return buildFileDto(fileRepository.findByUserAndType(userRepository.getOne(userId), DocType.PROFILE_IMAGE));
    }

}

