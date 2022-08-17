package site.yejin.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    //@Autowired // 필드 주입
    private final QuestionService questionService;

    @RequestMapping("/questions")
    public String list(Model model) {
        List<Question> questionList = questionService.list();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }

    @RequestMapping("/questions/{id}")
    public String detail(@PathVariable int id, Model model){
        Question question = questionService.detail(id);
        model.addAttribute("question",question);
        return "question_detail";
    }

    @GetMapping("/question")
    public String create(QuestionCreateForm questionCreateForm){
        return "question_form";
    }

    @PostMapping("/question")
    public String create(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Optional<Integer> oid;

        try {
            oid = questionService.create(
                    questionCreateForm.getSubject(),
                    questionCreateForm.getContent()
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