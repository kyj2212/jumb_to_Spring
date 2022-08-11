package site.yejin.sbb;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@TestInstance(PER_CLASS)
class SbbApplicationTests {
    @Autowired
    private QuestionRepository questionRepository; // questionRepo 는 sbbtest 클래스가 생성될때 같이 생성되어야 하는 필드이다. // qeustionController, service를 만든다면 이 필드가 들어가겟지
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void contextLoads() {
    }



    @BeforeAll
    public void beforeAll() {



    }


    @BeforeEach
    public void beforeEach() {
        truncateTable();
        makeTestData();
    }

    private void truncateTable() {
        truncateQuestionTable();
        truncateAnswerTable();
    }

    public void makeTestData() {
        makeQuestionTestData();
        makeAnswerTestData();
    }

    private void makeAnswerTestData() {
        Question q1 = findQuestionById(0);
        Answer a1 = new Answer();
        a1.setContent("sbb는 스프링부트 게시판 프로젝트 입니다");
        a1.setQuestion(q1);
        a1.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a1);

        Question q2 = findQuestionById(1);
        Answer a2 = new Answer();
        a2.setContent("id는 자동으로 생성됩니다.");
        a2.setQuestion(q2);
        a2.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a2);
    }

    private Question findQuestionById(int id) {
        List<Question> all = this.questionRepository.findAll();
        System.out.println(all);

        return all.get(id);
    }

    private void makeQuestionTestData() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        // this를 하는이유?
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }

    public void truncateQuestionTable() {
        this.questionRepository.setForeignKeyChecks(0);
        this.questionRepository.truncateTable();
        this.questionRepository.setForeignKeyChecks(1);
    }
    public void truncateAnswerTable() {
        this.answerRepository.setForeignKeyChecks(0);
        this.answerRepository.truncateTable();
        this.answerRepository.setForeignKeyChecks(1);
    }


    @Test
    public void testJpaTruncateTable(){
        this.questionRepository.setForeignKeyChecks(0);
        this.questionRepository.truncateTable();
        this.questionRepository.setForeignKeyChecks(1);
    }


/*
    @Test
    public void testJpaTruncateTableAnswerNoConstraint(){
        // Answer 의 question field 에 @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) 추가
        this.questionRepository.truncateTable();
    }
*/

    @Test
    void testJpaInsertQuestion() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        // this를 하는이유?
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장

        assertThat(q1.getId()).isGreaterThan(0);
        assertThat(q2.getId()).isGreaterThan(q1.getId());
    }

    @Test
    public void testJpaInsertIntoAnswer() {
        Question q1 = findQuestionById(0);
        Answer a1 = new Answer();
        a1.setContent("sbb는 스프링부트 게시판 프로젝트 입니다");
        a1.setQuestion(q1);
        a1.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a1);

        Question q2 = findQuestionById(1);
        Answer a2 = new Answer();
        a2.setContent("id는 자동으로 생성됩니다.");
        a2.setQuestion(q2);
        a2.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a2);
    }

    @Test
    void testJapSelectAll(){
        // this를 하는이유?
        List<Question> all = this.questionRepository.findAll();
        System.out.println(all.size());
        for(Question q : all){
            System.out.println(q.getSubject());
        }
    }


    @Test
    void testJpaFindAllGetId(){
        // this를 하는이유?
        List<Question> all = this.questionRepository.findAll();
        System.out.println(all);
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpaFindBySubject(){
        // this를 하는 이유
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1,q.getId());
    }
    @Test
    void testJpaFindByAndContent() {
        Question q = this.questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }
    @Test
    void testJpaQFindBySubjectLike() {
        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpaQFindById(){
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        System.out.println(q.getSubject());
        this.questionRepository.save(q);

    }

    @Test
    void testJpaDeleteQuestion(){
        long cntOrg = this.questionRepository.count();
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        System.out.println(this.questionRepository.existsById(1));
        assertEquals(cntOrg-1,this.questionRepository.count() );
    }
    @Test
    void testJpaDeleteBySubject(){
        String subject = "스프링부트 모델 질문입니다.";

        long cntOrg = this.questionRepository.count();
        // findById 와 다르게 Optional을 넣으면 ofNullable 을 붙이라는 에러가 나온다. 왜??
       // Optional<Question> oq = Optional.ofNullable(this.questionRepository.findBySubject(subject));
       // assertTrue(oq.isPresent());
       // Question qByoq = oq.get();

        //Question q = this.questionRepository.findBySubject(subject);
        List<Question> dList = this.questionRepository.findBySubjectLike(subject);
        dList.stream().forEach(q-> System.out.println(q.getSubject()));
        // findById는 CrudRopository 인터페이스에 속해있는 	Optional<T> findById(ID id); 추상 메소드 이고,
        // findBySubject는 JPA(?) 또는 스프링(?) 이 repo 의 필드값을 기준으로 자동으로 생성하는 메소드로 리턴값이 Question이다. 그래서 다르다!!!
        // Optional<Question> oq2 = this.questionRepository.findBySubject(subject);
        //Question q = dList.get(1);
        //this.questionRepository.delete(q);
        //System.out.println(q.getId()+" " +q.getSubject() + " exist? : "+ this.questionRepository.existsById(q.getId()));
        assertEquals(cntOrg,this.questionRepository.count() );
    }



/*
    @Test
    public void testJpaSelectAnswer(){

        List<Answer> all = this.answerRepository.findAll();
        System.out.println(all);
        assertEquals(2, all.size());


        //assertEquals("sbb가 무엇인가요?", q.getSubject());

    }*/
}