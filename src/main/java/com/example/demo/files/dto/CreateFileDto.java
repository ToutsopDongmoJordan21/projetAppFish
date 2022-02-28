package com.example.demo.files.dto;

import com.example.demo.files.entities.enumeration.DocType;
import com.example.demo.files.entities.enumeration.EntityFileType;
import com.example.demo.files.entities.enumeration.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFileDto {
    @NotNull
    private DocType type;
    @NotNull
    private EntityFileType entity;
    @NotNull
    private FileType fileType;

    private Long userId;
}
