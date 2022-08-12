package site.yejin.sbb;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AnswerRepoTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
    //private int lastSampleDataId;
    private int lastTestAnswerQuestionId;
    private int lastTestAnswerId;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }
    private void clearData() {
        AnswerRepoTests.clearData(answerRepository);
        answerRepository.truncate();
    }
    public static void clearData(AnswerRepository answerRepository) {
        answerRepository.deleteAll(); // DELETE FROM question;
        answerRepository.truncate();
    }

    private void createSampleData() {
        QuestionRepoTests.createSampleData(questionRepository);

        Question q = questionRepository.findById(1).get();
        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판 입니다.");
        a1.setQuestion(q);
        a1.setCreateDate(LocalDateTime.now());
k
        answerRepository.save(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링부트관련 내용을 다룹니다.");
        a2.setQuestion(q);
        a2.setCreateDate(LocalDateTime.now());
        //q.addAnswerList(a2);
        answerRepository.save(a2);

        lastTestAnswerId=a2.getId();
        lastTestAnswerQuestionId=a2.getQuestion().getId();
    }

    @Test
    void test__save() {
        Question q = questionRepository.findById(2).get();
        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);
    }

    @Test
    void test_findById() {
        Answer a = this.answerRepository.findById(1).get();
        assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test_Insert() {

        Optional<Question> oq = this.questionRepository.findById(lastTestAnswerQuestionId);
        assertTrue(oq.isPresent());
        Question q= oq.get();
        // System.out.println(q.getContent());
        //System.out.println("처음 질문 에 있는 답변 개수 : "+q.getAnswerList().size());
        Answer a = new Answer();
        a.setContent("new answer");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
        //  int size =q.getAnswerList().size();
        //  System.out.println("답변 추가후 질문에 있는 답변 개수 : "+size+" 넣은 답변 : "+q.getAnswerList().get(size-1).getContent());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test_selectAnswerByQuestionId() {
        Optional<Question> oq = this.questionRepository.findById(lastTestAnswerQuestionId);
        assertTrue(oq.isPresent());
        Question q= oq.get();
        System.out.println(q.getContent());

        System.out.println("answerList is null? : "+q.getAnswerList());
        System.out.println("처음 질문 에 있는 답변 개수 : "+q.getAnswerList().size());

        List<Answer> answerList = q.getAnswerList();
        assertThat(answerList.size()).isEqualTo(2);
        assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");

        //  int size =q.getAnswerList().size();
        //  System.out.println("답변 추가후 질문에 있는 답변 개수 : "+size+" 넣은 답변 : "+q.getAnswerList().get(size-1).getContent());
    }

}