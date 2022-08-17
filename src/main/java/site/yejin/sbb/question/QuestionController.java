package site.yejin.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public String create(){
        return "question_form";
    }

    @PostMapping("/question")
    public String create(@RequestParam String subject, @RequestParam String content){
        int id = questionService.create(subject,content);
        return "redirect:/questions/%d".formatted(id);
    }
}