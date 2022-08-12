package site.yejin.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
public class Question {


    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Integer id;

    @Column(length = 200) // varchar(200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    public void initAnswerList() {
        if (answerList == null) {
            this.answerList = new ArrayList<>();
        }
    }
    // list는 OneToMany 로 엔티티의 값들이 들어가기 때문에, 굳이 add 할 필요가 없다.
/*    public void addAnswerList(Answer answer){
        if (answerList == null) {
            this.answerList = new ArrayList<>();
        }
        answerList.add(answer);
    }*/
}