package site.yejin.sbb;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.base.RepositoryUtil;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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


    @PersistenceContext
    EntityManager entityManager;

    private static int lastTestDataId;
    private List<String> tableNames;
    @Test
    void contextLoads() {
    }



    @BeforeAll
    public void beforeAll() {

        tableNames=new ArrayList<>();
        tableNames.add("question");
        tableNames.add("answer");

    }


/*
    @BeforeEach
    public void beforeEachTruncate() {
        System.out.println("이거 안돼?");

        //entityManager.createNativeQuery("SELECT @@global.foreign_key_checks,@@session.foreign_key_checks");
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        truncateTable();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
       // nativeQuery = entityManager.createNativeQuery("SELECT @@global.foreign_key_checks,@@session.foreign_key_checks");
        //System.out.println(nativeQuery);
    }

    @BeforeEach
    public void beforeEachCreate() {
        createTestData();
    }
*/

    @BeforeEach
    public void beforeEach() {
        truncateTable();
        createTestData();
    }


    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();

        for (String tableName : tableNames) {
            // 테이블의 모든 row 삭제
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }


    private void truncateTable() {

        truncateQuestionTable();
        truncateAnswerTable();


    }


    public void truncateQuestionTable() {
        this.questionRepository.truncate();
        //this.questionRepository.setForeignKeyChecks(1);
    }
    public void truncateAnswerTable() {
        // answers 는 foreign key가 없다!
        this.answerRepository.truncate();
    }

    // create Test Data

    public void createTestData() {
        insertQuestionTestData();
        insertAnswerTestData();
    }


    private void insertQuestionTestData() {
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

        lastTestDataId=q2.getId();
    }



    private void insertAnswerTestData() {
        Question q1 = findQuestionById(1);
        Answer a1 = new Answer();
        a1.setContent("sbb는 스프링부트 게시판 프로젝트 입니다");
        a1.setQuestion(q1);
        a1.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a1);

        Question q2 = findQuestionById(2);
        Answer a2 = new Answer();
        a2.setContent("id는 자동으로 생성됩니다.");
        a2.setQuestion(q2);
        a2.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a2);
    }

    private Question findQuestionById(int id) {
        Optional<Question> oq = this.questionRepository.findById(id);
        if(oq.isPresent()){
            Question q= oq.get();
            return q;
        } else
            return null;
//        List<Question> all = this.questionRepository.findAll();
 //       System.out.println(all);
    }


    @Test
    public void testJpaTruncateTableQ(){
        this.questionRepository.setForeignKeyChecks(0);
        this.questionRepository.truncate();
        this.questionRepository.setForeignKeyChecks(1);
    }
    @Test
    public void testJpaTruncateTableA(){
        this.answerRepository.truncate();
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
        Question q = new Question();
        q.setSubject("new subject");
        q.setContent("new question");
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
        assertThat(q.getId()).isGreaterThan(0);
    }



    @Test
    public void testJpaInsertIntoAnswer() {
        insertAnswerTestData();

        Optional<Question> oq = this.questionRepository.findById(lastTestDataId);
        assertTrue(oq.isPresent());
        Question q= oq.get();
       // System.out.println(q.getContent());
       // System.out.println("처음 질문 에 있는 답변 개수 : "+q.getAnswerList().size());
        Answer a = new Answer();
        a.setContent("new answer");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
      //  int size =q.getAnswerList().size();
      //  System.out.println("답변 추가후 질문에 있는 답변 개수 : "+size+" 넣은 답변 : "+q.getAnswerList().get(size-1).getContent());
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
    void testJpaFindbyId(){
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q= oq.get();
        assertEquals("sbb가 무엇인가요?", q.getSubject());
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
    void testJpaUpdate(){
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
        Question q = dList.get(0); // 일단 첫번째 값만 가져와
        this.questionRepository.delete(q);
        System.out.println(q.getId()+" " +q.getSubject() + " exist? : "+ this.questionRepository.existsById(q.getId()));
        assertEquals(cntOrg-1,this.questionRepository.count() );
    }


    @Test
    public void testJpaQueryParam(){


        //this.questionRepository.selectFromQMark("question");
        //this.questionRepository.selectFromParamName("question");
        //this.questionRepository.selectFromParamAnno("question");
        this.questionRepository.findByUserId("sbb가 무엇인가요?");
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