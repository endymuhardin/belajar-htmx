package com.muhardin.endy.belajar.htmx.repository;

import com.muhardin.endy.belajar.htmx.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findAllByOrderByPinnedDescCreatedAtDesc(Pageable pageable);
}
