package com.example.demo.files.webs;

import com.example.demo.files.dto.CreateFileDto;
import com.example.demo.files.dto.FileDto;
import com.example.demo.files.dto.message.ResponseMessage;
import com.example.demo.files.services.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api/file"})
@CrossOrigin(origins = "*")
@Slf4j
@AllArgsConstructor
public class FileResource {

    private final FileService fileService;

    /**
     * Create file response entity.
     *
     * @param dto   the dto
     * @param file the files
     * @return the response entity
     */
    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> createFile(@ModelAttribute @Valid CreateFileDto dto, @RequestParam("file") MultipartFile file){
        log.info("[API POST] Appel du endpoint createFile");
        String message = "";
        try {
            log.info("Filename orignal {}", file.getOriginalFilename());
            fileService.save(dto, file);
            message = "Uploaded the files successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping(value = "/delete/{filename}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String filename ){
        log.info("[API DELETE] Appel du endpoint deleteFile");
        String message = "";
        try {
            fileService.deleteOneFile(filename);
            message = "Delete the file successfully: " + filename;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to delete files!";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    /**
     * Gets file.
     *
     * @param filename the filename
     * @return the file
     */
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * Gets profile image.
     *
     * @param userId the user id
     * @return the profile image
     */
    @GetMapping("/find/{userId}")
    public ResponseEntity<FileDto> getProfileImage(@PathVariable Long userId) {
        log.info("[API GET] Appel du endpoint getProfileImage");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(fileService.findUserFile(userId));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }


}
