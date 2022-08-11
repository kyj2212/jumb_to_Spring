package site.yejin.sbb;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import site.yejin.sbb.base.RepositoryUtil;

public interface AnswerRepository extends JpaRepository<Answer,Integer>, RepositoryUtil {
    @Transactional
    @Modifying
    @Query(value="truncate answer", nativeQuery = true)
    void truncateTable();


}
