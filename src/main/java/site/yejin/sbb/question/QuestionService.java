package site.yejin.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yejin.sbb.question.Question;
import site.yejin.sbb.question.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Question findById(int id) {
        Question q = questionRepository.findById(id).get();
        return q;
    }

    public List<Question> list() {
        return questionRepository.findAll();
    }
}