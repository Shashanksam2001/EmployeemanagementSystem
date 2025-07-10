package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
public String showRegisterForm(Model model) {
    model.addAttribute("user", new User());
    return "register"; // Must match the name of the template: register.html
}


    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        boolean success=userService.register(user);
        return success ? "redirect:/login" : "register";
    }

    @GetMapping("/login")
public String showLoginForm(Model model) {
    model.addAttribute("user", new User()); // 'user' should match the form th:object name
    return "login"; // return login.html
}

    @PostMapping("/login")
    public String login(@RequestParam String name,@RequestParam String password,HttpSession session){
        User user=userService.login(name, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }
        return "login"; 
    }

     @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
         User user = (User) session.getAttribute("user");
         model.addAttribute("user", user);
        if("ADMIN".equalsIgnoreCase(user.getRole())){
            return "admindashboard"; // dashboard.html
        }else{
            return "userdashboard";
        }
        
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
}
