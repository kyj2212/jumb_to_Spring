package site.yejin.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController
{
    //@Autowired // 필드 주입
    private final QuestionService questionService;

    @RequestMapping("/questions")
    //@ResponseBody
    public String list(Model model) {
        List<Question> questionList = questionService.list();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }
}