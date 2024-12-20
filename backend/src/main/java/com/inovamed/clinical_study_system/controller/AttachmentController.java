package com.inovamed.clinical_study_system.controller;


import com.inovamed.clinical_study_system.model.attachment.AttachmentCreateResponseDTO;
import com.inovamed.clinical_study_system.model.attachment.AttachmentRequestDTO;
import com.inovamed.clinical_study_system.model.attachment.AttachmentFindResponseDTO;
import com.inovamed.clinical_study_system.service.attachment.AttachmentService;
import com.inovamed.clinical_study_system.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    TokenService tokenService;


    @PostMapping
    public ResponseEntity<AttachmentCreateResponseDTO> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file){
        try {
            String token = request.getHeader("Authorization").substring(7);
            Long userId = tokenService.getUserIdFromToken(token);
            AttachmentRequestDTO attachmentRequestDTO = new AttachmentRequestDTO(file.getName(),file.getBytes(),userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.upload(attachmentRequestDTO, userId));

        }catch (IOException exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping
    public ResponseEntity<List<AttachmentFindResponseDTO>> findAllById(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long userId = tokenService.getUserIdFromToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(attachmentService.findAllById(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AttachmentFindResponseDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentFindResponseDTO> findById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentService.findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AttachmentFindResponseDTO> update(@PathVariable("id") Long id, @RequestBody AttachmentRequestDTO attachmentRequestDTO ){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentService.update(id,attachmentRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        this.attachmentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
