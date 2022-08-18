package site.yejin.sbb.question;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.yejin.sbb.global.exception.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Page<Question> list(int page) {
        log.debug("int page "+page);
        Pageable pageable = PageRequest.of(page, 10, Sort.by("CreateDate").descending());
        return questionRepository.findAll(pageable);
    }

    public Question detail(int id) {
        return this.questionRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("%d question not found".formatted(id)));
    }

    public Optional<Integer> create(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.initAnswerList();
        questionRepository.save(question);
        return Optional.ofNullable(question.getId());
    }
}