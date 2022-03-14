package com.example.demo.models.dto;

import com.example.demo.files.dto.FileDto;
import lombok.Data;

import java.util.List;

@Data
public class EspeceResponseDto {
    private Long id;
    private String especeName;
    private String especeFamily;
    private String especeDescription;
    private String postByName;
    private Long postById;
    private List<FileDto> especeImages;

    public static final class EspeceResponseDtoBuilder {
        private Long id;
        private String especeName;
        private String especeFamily;
        private String especeDescription;
        private String postByName;
        private Long postById;
        private List<FileDto> especeImages;

        private EspeceResponseDtoBuilder() {}

        public static EspeceResponseDtoBuilder aEspeceResponseDto() {
            return new EspeceResponseDtoBuilder();
        }

        public EspeceResponseDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EspeceResponseDtoBuilder withEspeceName(String especeName) {
            this.especeName = especeName;
            return this;
        }

        public EspeceResponseDtoBuilder withEspeceFamily(String especeFamily) {
            this.especeFamily = especeFamily;
            return this;
        }

        public EspeceResponseDtoBuilder withEspeceDescription(String especeDescription) {
            this.especeDescription = especeDescription;
            return this;
        }

        public EspeceResponseDtoBuilder withPostByName(String postByName) {
            this.postByName = postByName;
            return this;
        }

        public EspeceResponseDtoBuilder withPostById(Long postById) {
            this.postById = postById;
            return this;
        }

        public EspeceResponseDtoBuilder withEspeceImages(List<FileDto> especeImages) {
            this.especeImages = especeImages;
            return this;
        }

        public EspeceResponseDto build() {
            EspeceResponseDto especeResponseDto = new EspeceResponseDto();
            especeResponseDto.setId(id);
            especeResponseDto.setEspeceName(especeName);
            especeResponseDto.setEspeceFamily(especeFamily);
            especeResponseDto.setEspeceDescription(especeDescription);
            especeResponseDto.setPostByName(postByName);
            especeResponseDto.setPostById(postById);
            especeResponseDto.setEspeceImages(especeImages);
            return especeResponseDto;
        }
    }
}
