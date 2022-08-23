package site.yejin.sbb.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.yejin.sbb.answer.AnswerCreateForm;
import site.yejin.sbb.member.entity.Member;
import site.yejin.sbb.member.service.MemberService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {
    //@Autowired // 필드 주입
    private final QuestionService questionService;
    private final MemberService memberService;

    @RequestMapping("/questions")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Question> paging = questionService.list(page);
        model.addAttribute("paging",paging);
        return "question_list";
    }

    @RequestMapping("/questions/{id}")
    public String detail(@PathVariable int id, Model model, AnswerCreateForm answerCreateForm){
        Question question = questionService.detail(id);
        model.addAttribute("question",question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question")
    public String create(QuestionCreateForm questionCreateForm){
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question")
    public String create(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult, Principal principal){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Optional<Integer> oid;

        try {
            log.debug("username : "+ principal.getName());
            log.debug("member : "+ memberService.findByUsername(principal.getName()));
            oid = questionService.create(
                    questionCreateForm.getSubject(),
                    questionCreateForm.getContent(),
                    memberService.findByUsername(principal.getName())
            );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("createQuestionFailed", "이미 등록된 질문입니다.");
            return "question_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("createQuestionFailed", e.getMessage());
            return "question_form";
        }

        int id=oid.orElseThrow(
                () -> {
                    return new RuntimeException("질문을 등록할 수 없습니다.");
                }
        );

        return "redirect:/questions/%d".formatted(id);
    }



}