package site.yejin.sbb;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
