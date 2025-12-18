package com.muhardin.endy.belajar.htmx.repository;

import com.muhardin.endy.belajar.htmx.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByOrderByUploadedAtDesc();
}
