package site.yejin.sbb;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    private static int num=0;
    @RequestMapping("/sbb")
    @ResponseBody
    public String index(){
        System.out.println("index");
        return "/sbb main page";
    }
    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @PostMapping("/plus")
    @ResponseBody
    public String showPlusPost(@RequestParam(defaultValue = "1") int a, int b) {
        int r=a+b;
        return """
                <h1>%d</h2>
                """.formatted(r);
    }
    @PostMapping("/minus")
    @ResponseBody
    public String showMinusPost(@RequestParam(defaultValue = "1") int a, int b) {
        int r=a-b;
        return """
                <h1>%d</h2>
                """.formatted(r);
    }



    @GetMapping("/page3")
    @ResponseBody
    public String showPage3() {
        return """
                <form method="POST" action="/plus">
                    <input type="number" name="a"  value=1 />
                    <input type="number" name="b" value=5/>
                    <input type="submit" value="더하기" />
                </form>
                """;
    }
    @GetMapping("/page4")
    @ResponseBody
    public String showPage4() {
        return """
                <form method="POST" action="/minus">
                    <input type="number" name="a" value=1 />
                    <input type="number" name="b"  value=5/>
                    <input type="submit" value="빼기" />
                </form>
                """;
    }


    @GetMapping("/increase")
    @ResponseBody
    public String showIncrease() {

        return """
                <h1>%d</h1>
                """.formatted(++num);
    }
    @PostMapping("/increase")
    @ResponseBody
    public String showIncrease(@RequestParam int num) {
        num++;
        return """
                <h1>%d</h1>
                """.formatted(num);
    }

    @GetMapping("/gugudanfor")
    @ResponseBody
    public String showGugudanFor(int dan, int limit) {

        StringBuilder str=new StringBuilder();
        for (int i = 1; i <=limit ; i++) {
            int r=dan*i;
            str.append("""
                <p>%d * %d = %d</p>
                """.formatted(dan,i,r));

        }
        return str.toString();
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String showGugudanStream(Integer dan, Integer limit) {

        if(dan==null)
            dan=4;
        if(limit==null)
            limit=9;

        Integer finalDan= dan;

        return IntStream.rangeClosed(1,limit)
                .mapToObj(i-> "%d * %d = %d".formatted(finalDan,i,finalDan*i))
                .collect(Collectors.joining("<br>\n"));


    }

    @GetMapping("/mbti")
    @ResponseBody
    public String showGugudan(String name) {

        return switch (name){
            case "홍길순" -> "INFP";
            case "임꺽정"-> "ENFP";
            case "홍길동" -> {
                char j = 'J';
                yield "INF" + j;
            }
            case "김예진","김김김" -> "ESTJ";
            default -> "모름";
        };

/*        switch (name){
            case "홍길동" :
                return "<h1>INFP</h1>";

            case "홍길순" :
                return "<h1>ENFP</h1>";

            case "임꺽정" :
                return "<h1>INFJ</h1>";

            case "김예진" :
                return "<h1>ESTJ</h1>";


        }
        return "<h1>없음</h1>";*/
    }

/*    @GetMapping("/page1")
    @ResponseBody
    public String showPage1(){
        return """
                <form method="POST" action "/page2">
                    <input type ="number" name=age placeholder="나이"/>
                    <input type ="submit"/>
                 </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age){
        return """
                <h1>POST 방식으로 넘어옴</h1>
                """;
    }
    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age){
        return """
                <h1>POST 방식으로 넘어옴</h1>
                """;
    }*/



}
