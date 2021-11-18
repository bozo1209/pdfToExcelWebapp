package app.controllers;

import app.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class HomeController {

    private HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService){
        this.homeService = homeService;
    }

    @GetMapping(path = {"/", "/home"})
    public String getHome(HttpSession session, Model model){
        homeService.getHomeService(session, model);
        return "home.html";
    }

    @GetMapping(path = "/generateExcel")
    public ResponseEntity<Resource> downloadExcel(HttpSession session) throws IOException {
        return homeService.downloadExcelService(session);
    }
}
