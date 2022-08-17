package site.yejin.sbb.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionCreateForm {

    @Size(max=25)
    @NotEmpty(message = "제목을 입력해 주세요.")
    private String subject;

    @NotEmpty(message = "내용을 입력해 주세요.")
    private String content;
}
