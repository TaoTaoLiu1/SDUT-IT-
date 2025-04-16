import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/user")
public class AuthApplication {

    // 模拟数据库
    private final Map<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    // 注册接口
    @PostMapping("/register")
    public String register(@RequestParam String userName, @RequestParam String password) {
        if (users.containsKey(userName)) {
            return "Registration failed: Username already exists";
        }
        users.put(userName, password);
        return "Registration successful";
    }

    // 登录接口
    @PostMapping("/login")
    public boolean login(@RequestParam String userName, @RequestParam String password, HttpSession session) {
        if (!users.containsKey(userName) || !users.get(userName).equals(password)) {
            return false;
        }
        session.setAttribute("username", userName);
        return true;
    }

    // 获取用户信息接口
    @GetMapping("/getUserInfo")
    public String getUserInfo(HttpSession session) {
        return (String) session.getAttribute("username");
    }
}