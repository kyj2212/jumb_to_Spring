package site.yejin.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yejin.sbb.global.exception.DataNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> list() {
        return questionRepository.findAll();
    }

    public Question detail(int id) {
        return this.questionRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("%d question not found".formatted(id)));
    }
}