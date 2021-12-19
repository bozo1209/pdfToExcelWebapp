//package app.controllers;
//
//import app.services.TextService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class TextUpdateController {
//
//    private TextService textService;
//
//    @Autowired
//    public TextUpdateController(TextService textService){
//        this.textService = textService;
//    }
//
////    List<String> textList = new ArrayList<>();
//    ModelAndView modelAndView = new ModelAndView("textUpdateWithoutRefreshing.html");
//
//    @GetMapping(path = "/text")
//    public String getText(Model model){
////        textList.add("test");
//        model.addAttribute("textList", textService.getTextList());
//
//        return "textUpdateWithoutRefreshing.html";
//    }
//
////    @PostMapping(path = "/update")
////    public String updateList(@RequestParam(name = "text") String text, RedirectAttributes redirectAttributes){
////        textList.add(text);
//////        redirectAttributes.addFlashAttribute("textList", textList);
////        return "redirect:/text";
////    }
//
//    @PostMapping(path = "/update")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void updateList(@RequestParam(name = "text") String text, RedirectAttributes redirectAttributes, Model model){
//        System.out.println("text = " + text);
//        textService.addElement(text);
//        model.addAttribute("textList", textService.getTextList());
//        redirectAttributes.addFlashAttribute("textList", textService.getTextList());
//    }
//
////    @PostMapping(path = "/update")
////    public ModelAndView updateList(@RequestParam(name = "text") String text, RedirectAttributes redirectAttributes, Model model){
////        textList.add(text);
////        redirectAttributes.addFlashAttribute("textList", textList);
////        model.addAttribute("textList", textList);
////        ModelAndView modelAndView = new ModelAndView("textUpdateWithoutRefreshing::text");
////        modelAndView.addObject("textList", textList);
////        return modelAndView;
////    }
//
//    @GetMapping("/ss")
//    public TextService ss(Model model){
//        model.addAttribute("test", "test");
//        return textService;
//    }
//
//
//}
