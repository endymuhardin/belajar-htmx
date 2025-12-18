package com.muhardin.endy.belajar.htmx.controller;

import com.muhardin.endy.belajar.htmx.model.Document;
import com.muhardin.endy.belajar.htmx.repository.DocumentRepository;
import com.muhardin.endy.belajar.htmx.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    public DocumentController(DocumentRepository documentRepository, FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/documents")
    public String documentsPage(Model model) {
        model.addAttribute("documents", documentRepository.findAllByOrderByUploadedAtDesc());
        return "document-list";
    }

    @GetMapping("/documents/list")
    public String getDocumentList(Model model) {
        model.addAttribute("documents", documentRepository.findAllByOrderByUploadedAtDesc());
        return "fragments/document-list :: document-list";
    }

    @PostMapping("/documents/upload")
    public String uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            Model model) throws InterruptedException {

        // Simulate upload delay for learning purposes
        Thread.sleep(2000);

        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload");
            return "fragments/upload-result";
        }

        try {
            String storedFilename = fileStorageService.storeFile(file);

            Document document = new Document();
            document.setOriginalFilename(file.getOriginalFilename());
            document.setStoredFilename(storedFilename);
            document.setContentType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setDescription(description);

            documentRepository.save(document);

            model.addAttribute("success", true);
            model.addAttribute("document", document);
            model.addAttribute("documents", documentRepository.findAllByOrderByUploadedAtDesc());

            return "fragments/upload-result";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload file: " + e.getMessage());
            return "fragments/upload-result";
        }
    }

    @GetMapping("/documents/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id, HttpServletRequest request) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        Resource resource = fileStorageService.loadFileAsResource(document.getStoredFilename());

        String contentType = document.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + document.getOriginalFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/documents/{id}")
    public String deleteDocument(@PathVariable Long id, Model model) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        fileStorageService.deleteFile(document.getStoredFilename());
        documentRepository.delete(document);

        model.addAttribute("documents", documentRepository.findAllByOrderByUploadedAtDesc());
        return "fragments/document-list";
    }
}
