package com.example.calcu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
public class HomeController {

    @GetMapping("/")
    String home(Model model, @RequestParam(value = "NamePrint", required = false, defaultValue = "") String name) {

        String test = "Не пусто";

        if (name == null) {
            test = "Пусто";
        }

        model.addAttribute("name", name);
        return "home";
    }

    @PostMapping("/home")
    String toPrintText(Model model, @RequestParam(value = "NamePrint", required = false, defaultValue = "") String name) {
        model.addAttribute("name", name);
        return "home";
    }

    //-------------------------------------------------------



    @GetMapping("/calculator")
    public String calculatorForm() {
        return "calculator";
    }

    @PostMapping("/result")
    public String calculate(@RequestParam("num1") double num1,
                            @RequestParam("num2") double num2,
                            @RequestParam("operation") String operation,
                            Model model) {
        double result = 0;
        if (operation.equals("plus")) {
            result = num1 + num2;
        }
        if (operation.equals("minus")) {
            result = num1 - num2;
        }
        if (operation.equals("umnoz")) {
        result = num1 * num2;
    }
        else if (operation.equals("del")) {
        result = num1 / num2;
        }
        model.addAttribute("result", result);
        return "result";
    }




    //-------------------------------------------------------


    @GetMapping("/converter")
    public String getValueConv(@RequestParam(name = "value", defaultValue = "0") String value, Model model) {
        model.addAttribute("value", value);
        return "converter";
    }

    @PostMapping("/converter")
    public String postValueConv(@RequestParam(name = "value", defaultValue = "0") String value, @RequestParam("first") String first, @RequestParam("second") String second, RedirectAttributes redirectAttributes) {

        float num = Float.parseFloat(value);

        switch (first) {
            case "₽":
                switch (second) {
                    case "$":
                        num *= 0.01;
                        break;
                    case "€":
                        num *= 0.009;
                        break;
                }
                break;
            case "$":
                switch (second) {
                    case "₽":
                        num *= 97;
                        break;
                    case "€":
                        num *= 0.94;
                        break;
                }
                break;
            case "€":
                switch (second) {
                    case "₽":
                        num *= 102;
                        break;
                    case "$":
                        num *= 1.05;
                        break;
                }
                break;
        }
        redirectAttributes.addAttribute("value", num);
        return "redirect:/converter";
    }

}
