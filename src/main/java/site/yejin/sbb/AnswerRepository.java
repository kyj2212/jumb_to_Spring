package site.yejin.sbb;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query(value = "SET FOREIGN_KEY_CHECKS=?",nativeQuery = true)
    void setForeignKeyChecks(int i);
    @Transactional
    @Modifying
    @Query(value="truncate question", nativeQuery = true)
    void truncateTable();
}
