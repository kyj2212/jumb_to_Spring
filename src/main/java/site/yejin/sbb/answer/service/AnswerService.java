package site.yejin.sbb.answer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yejin.sbb.answer.Answer;
import site.yejin.sbb.answer.AnswerRepository;
import site.yejin.sbb.global.exception.DataNotFoundException;
import site.yejin.sbb.question.Question;
import site.yejin.sbb.question.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        question.addAnswer(answer);
        this.answerRepository.save(answer);
    }
}
