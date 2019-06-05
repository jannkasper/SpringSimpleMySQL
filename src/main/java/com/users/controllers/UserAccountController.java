package com.users.controllers;

import com.users.entities.UserAccount;
import com.users.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping("/")
    public String showMain (Map<String,Object> model){
        model.put("userAccountLogin", new UserAccount());
        model.put("userAccountRegister", new UserAccount());
        return "formPage";
    }
    @PostMapping("/delete")
    public String delete (@Valid UserAccount userAccount, BindingResult bindingResult, Model model){
        userAccountService.deleteById(userAccount.getId());
        return "redirect:/";
    }
    @PostMapping("/update")
    public String update (@Valid UserAccount userAccount, BindingResult bindingResult, Model model){
        UserAccount updateUserAccount = userAccountService.findById(userAccount.getId());
        updateUserAccount.setLogin(userAccount.getLogin());
        updateUserAccount.setEmail(userAccount.getEmail());
        updateUserAccount.setPassword(userAccount.getPassword());
        userAccountService.save(updateUserAccount);
        model.addAttribute(updateUserAccount);
        return "accountPage";
    }

    @PostMapping("/register")
    public String register (@Valid UserAccount userAccount, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userAccountLogin", new UserAccount());
            model.addAttribute("userAccountRegister", userAccount);
            return "formPage";
        } else {
            this.userAccountService.save(userAccount);
            model.addAttribute("userAccount", userAccount);
            return "accountPage";
        }
    }
    @PostMapping("logIn")
    public String logIn (@Valid UserAccount userAccount, BindingResult bindingResult, Model model){
        if ( bindingResult.hasErrors()) {
            System.out.println("ERROR");
        }
        if (userAccountService.findUserAccountByLogin(userAccount.getLogin()) != null) {
            if ( userAccount.getPassword().equals(userAccountService.findUserAccountByLogin(userAccount.getLogin()).getPassword())){
                model.addAttribute(userAccountService.findUserAccountByLogin(userAccount.getLogin()));
                return "accountPage";
            } else {
                model.addAttribute("userAccountLogin", userAccount);
                model.addAttribute("userAccountRegister", new UserAccount());
                return "formPage";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/user_list")
    public String showList(Model model){
        model.addAttribute("accounts", userAccountService.findAll());
        return "listPage";
    }


}
