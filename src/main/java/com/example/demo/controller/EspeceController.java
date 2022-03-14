package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.Espece;
import com.example.demo.models.dto.EspeceDto;
import com.example.demo.models.dto.EspeceResponseDto;
import com.example.demo.repository.EspeceRepository;
import com.example.demo.service.EspeceDetailsService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class EspeceController {
    @Autowired
    private EspeceRepository especeRepository;

    @Autowired
    private EspeceDetailsService especeDetailsService;

    @GetMapping("/especes")
    public ResponseEntity<?> getAllEspece() {
        return ResponseEntity.ok(especeDetailsService.findAllEspece());
    }

    @GetMapping("/especes/userCreated/{id}")
        public ResponseEntity<?> getUserCreated(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(especeDetailsService.findAllByCreatedBy(userId));
    }

    @GetMapping("/especes/{id}")
    public ResponseEntity<?> getEspeceById(@PathVariable(value ="id") Long especeId) {
        return ResponseEntity.ok(especeDetailsService.findOneEspeceById(especeId));
    }

    @GetMapping("/especes/especeName")
    public ResponseEntity<List<EspeceResponseDto>> getEspeceByName(@RequestParam String especeName){
        return ResponseEntity.ok(especeDetailsService.findAllEspeceByName(especeName));
    }

    @GetMapping("/especes/especeFamily")
    public ResponseEntity<List<EspeceResponseDto>> getEspeceByFamily(@RequestParam String especeFamily) {
        return ResponseEntity.ok(especeDetailsService.findAllEspeceByFamily(especeFamily));
    }

    @DeleteMapping("/especes/{id}")
    public Map<String, Boolean> deleteEspece(@PathVariable(value = "id") Long especeId)
        throws ResourceNotFoundException {
        Espece espece = especeRepository.findById(especeId)
                .orElseThrow(() -> new ResourceNotFoundException("Espece not found for this id:: " +especeId));

        especeRepository.delete(espece);
        Map<String, Boolean> response = new HashMap<>();
        response.put("espece was successful delete", Boolean.FALSE);
        return response;
    }

    @PostMapping("/especes")
    public ResponseEntity<?> saveEspece(@RequestBody EspeceDto especeDto) throws Exception {
        return ResponseEntity.ok(especeDetailsService.save(especeDto));
    }

    @PutMapping("/especes/{id}")
    public ResponseEntity<?> updateEspece(@PathVariable(value = "id") Long especeId,
                                          @RequestBody EspeceDto especeDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(especeDetailsService.updateEspece(especeId, especeDto));
    }

}
