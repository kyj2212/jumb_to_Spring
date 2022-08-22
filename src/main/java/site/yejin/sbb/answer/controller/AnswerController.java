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
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.service.MemberService;
import site.yejin.sbb.member.service.MemberUserDetailService;
import site.yejin.sbb.question.Question;
import site.yejin.sbb.question.QuestionService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;

@RequestMapping("/answers")
@Controller
@RequiredArgsConstructor
public class AnswerController {
    //@Autowired // 필드 주입
    private final AnswerService answerService;
    private final QuestionService questionService;

    private final MemberService memberService;
    @PostMapping("/{id}")
    public String create(@PathVariable int id, Model model, @Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult, Principal principal) {
        Question question= this.questionService.detail(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        Member member = memberService.findByUsername(principal.getName());
        this.answerService.create(question,answerCreateForm.getContent(), member);
        return String.format("redirect:/questions/%s",id);
    }

}