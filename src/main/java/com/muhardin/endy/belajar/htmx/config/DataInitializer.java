package com.muhardin.endy.belajar.htmx.config;

import com.muhardin.endy.belajar.htmx.model.Note;
import com.muhardin.endy.belajar.htmx.repository.NoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final NoteRepository noteRepository;
    private final Random random = new Random();

    private static final String[] CATEGORIES = {
            "Work", "Personal", "Ideas", "Shopping", "Health", "Study", "Travel", "Finance"
    };

    private static final String[] TITLE_TEMPLATES = {
            "Meeting notes for %s",
            "Ideas about %s",
            "Remember to %s",
            "Plan for %s",
            "Notes on %s",
            "Tips for %s",
            "Reminder: %s",
            "Quick note about %s",
            "Important: %s",
            "Don't forget to %s"
    };

    private static final String[] CONTENT_TEMPLATES = {
            "This is a detailed note about %s. Need to review this later and take appropriate action.",
            "Quick reminder to follow up on %s. Important deadline approaching soon.",
            "Brainstorming session results for %s. Many good ideas discussed during the meeting.",
            "Research findings on %s. Needs more investigation before final decision.",
            "Action items from %s discussion. Assigned to team members with clear deadlines.",
            "Summary of %s activities. Overall progress is good but some areas need improvement.",
            "Lessons learned from %s experience. Will apply these insights to future projects.",
            "Key points to remember about %s. These are critical for success going forward.",
            "Status update on %s initiative. On track to meet objectives this quarter.",
            "Feedback received about %s. Need to incorporate suggestions in next iteration."
    };

    private static final String[] SUBJECTS = {
            "project planning", "client meeting", "budget review", "team building",
            "product launch", "quarterly goals", "system upgrade", "customer feedback",
            "market research", "training session", "performance review", "strategic planning",
            "resource allocation", "risk assessment", "quality improvement", "innovation ideas",
            "process optimization", "stakeholder engagement", "vendor selection", "compliance audit"
    };

    public DataInitializer(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void run(String... args) {
        if (noteRepository.count() == 0) {
            generateSampleNotes();
        }
    }

    private void generateSampleNotes() {
        List<Note> notes = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 50; i++) {
            Note note = new Note();
            String subject = SUBJECTS[random.nextInt(SUBJECTS.length)];
            String category = CATEGORIES[random.nextInt(CATEGORIES.length)];

            note.setTitle(String.format(
                    TITLE_TEMPLATES[random.nextInt(TITLE_TEMPLATES.length)],
                    subject
            ));

            note.setContent(String.format(
                    CONTENT_TEMPLATES[random.nextInt(CONTENT_TEMPLATES.length)],
                    subject
            ));

            note.setCategory(category);
            note.setPinned(i < 3);
            note.setCreatedAt(now.minusDays(i).minusHours(random.nextInt(24)));

            notes.add(note);
        }

        noteRepository.saveAll(notes);
    }
}
