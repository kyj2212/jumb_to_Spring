package site.yejin.sbb.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.yejin.sbb.answer.AnswerCreateForm;
import site.yejin.sbb.answer.service.AnswerService;
import site.yejin.sbb.question.Question;
import site.yejin.sbb.question.QuestionService;

import javax.websocket.server.PathParam;

@RequestMapping("/answers")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    //@Autowired // 필드 주입
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/{id}")
    public String create(@PathVariable int id, Model model, AnswerCreateForm answerCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_detail";
        }
        Question question= this.questionService.detail(id);
        this.answerService.create(question,answerCreateForm.getContent());
        return String.format("redirect:/questions/%s",id);
    }

}