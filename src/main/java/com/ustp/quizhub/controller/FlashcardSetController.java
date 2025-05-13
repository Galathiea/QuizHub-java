package com.ustp.quizhub.controller;

import com.ustp.quizhub.model.FlashcardSet;
import com.ustp.quizhub.repository.FlashcardSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcard-sets")
@RequiredArgsConstructor
public class FlashcardSetController {
    private final FlashcardSetRepository flashcardSetRepository;

    // List all public flashcard sets
    @GetMapping("/public")
    public List<FlashcardSet> getAllPublicFlashcardSets() {
        return flashcardSetRepository.findByIsPublicTrue();
    }

    // Set a flashcard set as public or private
    @PatchMapping("/{id}/visibility")
    public ResponseEntity<?> setFlashcardSetVisibility(@PathVariable Long id, @RequestParam boolean isPublic) {
        return flashcardSetRepository.findById(id)
                .map(set -> {
                    set.setPublic(isPublic);
                    flashcardSetRepository.save(set);
                    return ResponseEntity.ok().body("Visibility updated");
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 