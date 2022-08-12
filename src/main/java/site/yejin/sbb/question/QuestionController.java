package site.yejin.sbb.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionController
{
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/question/list")
    //@ResponseBody
    public String list(Model model) {
        List<Question> questionList = questionService.findAll();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }
}