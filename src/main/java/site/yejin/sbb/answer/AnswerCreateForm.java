package site.yejin.sbb.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AnswerCreateForm {
    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;
}
