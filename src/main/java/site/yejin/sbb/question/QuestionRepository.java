package site.yejin.sbb.question;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.base.RepositoryUtil;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> , RepositoryUtil {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);
    Question findByContent(String content);
    List<Question> findBySubjectLike(String s);

    @Transactional
    @Modifying
   // @Query(value="truncate question", nativeQuery = true)
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void truncateTable();

    @Query(value = "SET FOREIGN_KEY_CHECKS=0",nativeQuery = true)
    void enableForeignKeyChecks();
    @Query(value = "SET FOREIGN_KEY_CHECKS=1",nativeQuery = true)
    void disableForeignKeyChecks();



/*    @Query(value = "select * from ?",nativeQuery = true)
    public List<Question> selectFromQMark(String tableName);

    @Query(value = "select * from tableName", nativeQuery = true)
    public List<Question> selectFromParamName(String tableName);


    @Query(value = "select * from tableName",nativeQuery = true)
    public List<Question> selectFromParamAnno(@Param("tableName") String tableName);*/

    // ? 파라미터는 where절에 쓰이는 조건을 위한 표현으로, FROM 절에는 안된다.
    @Query(value="SELECT * FROM question q WHERE q.subject = ?", nativeQuery = true)
    public List<Question> findByUserId(String subject);



}