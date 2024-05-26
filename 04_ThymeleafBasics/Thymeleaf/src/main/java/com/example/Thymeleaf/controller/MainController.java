package com.example.Thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    // When we hit this url, returning file (home.html) is loaded.
    @GetMapping("/home")
    public String loadHome(Model model){

        // Model obj in Spring MVC is used to pass data from controller to view (Thymeleaf template).
        // addAttribute(arg1,arg2) adds an attribute to the model. Takes 2 args :
        // "name" : it's the name of the attribute
        // "Thymeleaf" : it defines the value of the attribute
        model.addAttribute("name","Thymeleaf");

        // This "home" refers to the "home.html" in templates folder.
        // As, we have added the thymeleaf dependency , so it will automatically see "home" as html file, & not as a String.
        return "home";
    }

    /*
      3 conditional statements in thymleaf :

      - Elvis Operator
        - Tenary operator

      - If unless
        - "th:if" is used. This is used to conditionally include or exclude an element based on a condition.
        - If condition is T , element is included, if F, then it is excluded.
        - E.g, Display a mssg only if user is logged in.
        - "th:unless" is used.
        - Includes the element if the condition is F, else excludes if T.
        - E.g, Display a login prompt , if a user is not logged in.

      - Switch case
        - "th:switch" & "th:case", used together to create switch-case strtuctre.
        - Used when multiple conditions to check against a single variable.
        - E.g, Display different mssgs based on the user's role.
     */


    @GetMapping("/elvis")
    public String elvisExample(Model model){
        // tertiary operator false value.
        model.addAttribute("isAdmin",true);

        // if-unless
        model.addAttribute("Gender","K");
        return "elvis";
    }

    @GetMapping("/each")
    public String eachExample(Model model){
        List<String> stringList = List.of("First","Second","Third");
        model.addAttribute("list",stringList);
        return "each";
    }

}
