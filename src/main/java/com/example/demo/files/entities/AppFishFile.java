package com.example.demo.files.entities;

import com.example.demo.files.entities.enumeration.DocType;
import com.example.demo.files.entities.enumeration.FileType;
import com.example.demo.models.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "appfish_files")
@Data
@ToString
public class AppFishFile implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String url;
    @Enumerated(EnumType.STRING)
    private DocType type;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public static final class AppFishFileBuilder {
        private Long id;
        private String code;
        private String fileName;
        private String url;
        private DocType type;
        private FileType fileType;
        private User user;

        private AppFishFileBuilder() {
        }

        public static AppFishFileBuilder aAppFishFileFile() {
            return new AppFishFileBuilder();
        }

        public AppFishFileBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AppFishFileBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public AppFishFileBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public AppFishFileBuilder withType(DocType type) {
            this.type = type;
            return this;
        }

        public AppFishFileBuilder withFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public AppFishFileBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public AppFishFile build() {
            AppFishFile appFishFile = new AppFishFile();
            appFishFile.setId(id);
            appFishFile.setFileName(fileName);
            appFishFile.setUrl(url);
            appFishFile.setType(type);
            appFishFile.setFileType(fileType);
            appFishFile.setUser(user);
            return appFishFile;
        }
    }
}


