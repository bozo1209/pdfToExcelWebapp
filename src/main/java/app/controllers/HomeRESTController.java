package app.controllers;

import app.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@RestController
public class HomeRESTController {

    private HomeService homeService;

    @Autowired
    public HomeRESTController(HomeService homeService){
        this.homeService = homeService;
    }

    @GetMapping(path = "/resthome")
    public List<File> getHomeREST(HttpSession session){
        return homeService.getHomeRESTService(session);
    }

    @PostMapping(path = "/restuploadFile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submitREST(@RequestParam("files") MultipartFile[] multipartFile, HttpSession session){
//        System.out.println("submitREST");
//        for (int i = 0; i < multipartFile.length; i++){
//            System.out.println("multipartFile[" + i + "] " + multipartFile[i]);
//        }
        homeService.submitRESTService(multipartFile, session);
    }

    @PostMapping(path = "/restdeleteFile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFileREST(@RequestParam(name = "filename") String filename, HttpSession session){
//        System.out.println("************** session = " + session.getId() + " *************");
//        System.out.println("filename = " + filename);
        homeService.deleteFileService(filename, session);
    }

    @PostMapping(path = "/restdeleteSessionFolder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSessionFolder(HttpSession session){
//        System.out.println("delete start");
        homeService.deleteSessionFolderService(session);
//        System.out.println("delete stop");
    }
}
