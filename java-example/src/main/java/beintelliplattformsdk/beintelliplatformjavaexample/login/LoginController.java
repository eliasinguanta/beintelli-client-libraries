package beintelliplattformsdk.beintelliplatformjavaexample.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import beintelliplattformsdk.beintelliplatformjavaexample.utils.Credential;

@Controller
public class LoginController {

    
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("credential", new Credential(null, null));
        return "login";
    }

    @PostMapping("/login")
    public String log_in(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes){
        try {
            redirectAttributes.addAttribute("accessToken", Login.login(credential)) ;
            return "redirect:/weatherInitial";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }
}
