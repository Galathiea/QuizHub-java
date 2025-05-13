package com.ustp.quizhub.service;

import com.ustp.quizhub.model.Flashcard;
import com.ustp.quizhub.model.FlashcardSet;
import com.ustp.quizhub.model.Question;
import com.ustp.quizhub.model.Quiz;
import com.ustp.quizhub.repository.FlashcardSetRepository;
import com.ustp.quizhub.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final FlashcardSetRepository flashcardSetRepository;
    private final QuizRepository quizRepository;

    @Transactional
    public Quiz createQuizFromFlashcardSet(Long flashcardSetId) {
        FlashcardSet flashcardSet = flashcardSetRepository.findById(flashcardSetId)
                .orElseThrow(() -> new RuntimeException("Flashcard set not found"));

        Quiz quiz = new Quiz();
        quiz.setTitle(flashcardSet.getTitle());
        quiz.setSubject(flashcardSet.getSubject());
        quiz.setDescription("Auto-generated quiz from flashcard set: " + flashcardSet.getTitle());
        quiz.setCreator(flashcardSet.getCreator());
        quiz.setPublic(flashcardSet.isPublic());

        List<Question> questions = new ArrayList<>();
        for (Flashcard flashcard : flashcardSet.getFlashcards()) {
            Question question = new Question();
            question.setText(flashcard.getFront());
            question.setType(Question.QuestionType.SHORT_ANSWER);
            List<String> correctAnswers = new ArrayList<>();
            correctAnswers.add(flashcard.getBack());
            question.setCorrectAnswers(correctAnswers);
            question.setQuiz(quiz);
            questions.add(question);
        }
        quiz.setQuestions(new HashSet<>(questions));
        return quizRepository.save(quiz);
    }
} 