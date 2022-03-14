package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.files.dto.FileDto;
import com.example.demo.files.services.FileService;
import com.example.demo.models.Espece;
import com.example.demo.models.User;
import com.example.demo.models.dto.EspeceDto;
import com.example.demo.models.dto.EspeceResponseDto;
import com.example.demo.repository.EspeceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EspeceDetailsService {

    private final EspeceRepository especeRepository;
    private final FileService fileService;
    private final UserRepository userRepository;

    public EspeceResponseDto save(EspeceDto espece) {
        User user = userRepository.getOne(SecurityUtils.getCurrentUserId());
        Espece newEspece = new Espece();
        newEspece.setEspeceName(espece.getEspeceName());
        newEspece.setEspeceFamily(espece.getEspeceFamily());
        newEspece.setEspeceDescription(espece.getEspeceDescription());
        newEspece.setUser(user);
        newEspece = especeRepository.save(newEspece);

        return findOneEspeceById(newEspece.getId());
    }


    public EspeceResponseDto findOneEspeceById(Long especeId) {
        Espece espece = especeRepository.getOne(especeId);
        Optional<User> optionalUser = userRepository.findByUsername(espece.getCreatedBy());
        List<FileDto> files = fileService.findEspeceFile(especeId);
        return EspeceResponseDto.EspeceResponseDtoBuilder.aEspeceResponseDto()
                .withId(especeId)
                .withEspeceName(espece.getEspeceName())
                .withEspeceFamily(espece.getEspeceFamily())
                .withEspeceDescription(espece.getEspeceDescription())
                .withPostById(optionalUser.map(User::getId).orElse(null))
                .withPostByName(optionalUser.map(User::getUsername).orElse(null))
                .withEspeceImages(files)
                .build();
    }

    public EspeceResponseDto updateEspece(Long especeId, EspeceDto dto) throws ResourceNotFoundException {
        Espece espece = especeRepository.findById(especeId).orElseThrow(() -> new ResourceNotFoundException("Espece not found for this ::" +especeId));

        if(Objects.nonNull(dto.getEspeceName())) {
            espece.setEspeceName(dto.getEspeceName());
        }
        if(Objects.nonNull(dto.getEspeceFamily())) {
            espece.setEspeceFamily(dto.getEspeceFamily());
        }
        if(Objects.nonNull(dto.getEspeceDescription())) {
            espece.setEspeceDescription(dto.getEspeceDescription());
        }

        espece = especeRepository.save(espece);
        return findOneEspeceById(espece.getId());
    }

    public List<EspeceResponseDto> findAllEspece() {
        return especeRepository.findAll().stream().map(buildListEspeceResponseDto()).collect(Collectors.toList());
    }

    public List<EspeceResponseDto> findAllByCreatedBy(Long userId) {
        return especeRepository.findAllByCreatedBy(userId).stream().map(buildListEspeceResponseDto()).collect(Collectors.toList());
    }

    public List<EspeceResponseDto> findAllEspeceByName(String especeName) {
        return especeRepository.findByEspeceName(especeName).stream().map(buildListEspeceResponseDto()).collect(Collectors.toList());
    }

    public List<EspeceResponseDto> findAllEspeceByFamily(String especeFamily) {
        return especeRepository.findByEspeceFamily(especeFamily).stream().map(buildListEspeceResponseDto()).collect(Collectors.toList());
    }

    private Function<Espece, EspeceResponseDto> buildListEspeceResponseDto() {
        return espece -> findOneEspeceById(espece.getId());
    }
}
