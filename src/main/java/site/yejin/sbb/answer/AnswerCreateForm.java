package site.yejin.sbb.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class AnswerCreateForm {
    @NotBlank(message = "제목을 입력해 주세요.")
    private String content;
}
