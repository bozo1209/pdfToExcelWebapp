package app.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Service
public class HomeService {

    private Map<String, List<File>> filesMapWithSessionId;
    private final String ATTRIBUTE_FILES_NAME = "files";
    private final String REDIRECT_HOME = "redirect:/home";

    public void getHomeController(HttpSession session, Model model){
        createFilesMap(session);
        createNewSessionIdKey(session);
        addFilesListOfSessionIdToModel(session, model, null);
    }

    public void submitService(MultipartFile[] multipartFiles, HttpSession session, RedirectAttributes redirectModel){
        addFilesToMapOfLists(multipartFiles, session);
        addFilesListOfSessionIdToModel(session, null, redirectModel);
    }

    private void createFilesMap(HttpSession session){
        if (filesMapWithSessionId == null){
            filesMapWithSessionId = new HashMap<>();
        }
    }

    private void createNewSessionIdKey(HttpSession session){
        if (filesMapWithSessionId.get(session.getId()) == null){
            filesMapWithSessionId.put(session.getId(), new ArrayList<>());
        }
    }

    private void addFilesListOfSessionIdToModel(HttpSession session, Model model, RedirectAttributes redirectModel){
        if (model != null){
            if (filesMapWithSessionId.get(session.getId()) != null){
                model.addAttribute(ATTRIBUTE_FILES_NAME, filesMapWithSessionId.get(session.getId()));
            }
        }else if (redirectModel != null){
            redirectModel.addFlashAttribute(ATTRIBUTE_FILES_NAME, filesMapWithSessionId.get(session.getId()));
        }
    }

    public void test(MultipartFile[] multipartFiles, HttpSession session){
        System.out.println("********test************");
        getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(multipartFiles, session);
    }

//    private String redirectHome2(){
//        return REDIRECT_HOME;
//    }
//
//    private void redirectHome(){
//        redirectHome2();
//    }

    private String getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(MultipartFile[] multipartFiles, HttpSession session){
        if (multipartFiles[0].getOriginalFilename().equals("")){
            return REDIRECT_HOME;
        }
        String pathToFile = session.getServletContext().getRealPath(multipartFiles[0].getOriginalFilename());
        return pathToFile.replaceAll(multipartFiles[0].getOriginalFilename(), "") + session.getId();
    }

    private void createSessionIdFolder(String path){
        new File(path).mkdir();
    }

    private File[] createFileArray(MultipartFile[] multipartFiles, HttpSession session){
        String path = getPathToPlaceWhereWillBeCreatedFolderForSessionIdFolder(multipartFiles, session);
        if (path.equals(REDIRECT_HOME)){
            return null;
        }
        createSessionIdFolder(path);
        File[] files = new File[multipartFiles.length];
        for (int i = 0; i < multipartFiles.length; i++){
            files[i] = Paths.get(path, multipartFiles[i].getOriginalFilename()).toFile();
            try {
                multipartFiles[i].transferTo(files[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    private void addFilesToMapOfLists(MultipartFile[] multipartFiles, HttpSession session){
        File[] fileArray = createFileArray(multipartFiles, session);
        if (fileArray == null){
            System.out.println("****null*******");
            return;
        }
        if (filesMapWithSessionId.get(session.getId()) != null){
            filesMapWithSessionId.replace(
                    session.getId(),
                    addsTwoLists(
                            filesMapWithSessionId.get(session.getId()),
                            arrayToList(fileArray)
                    )
            );
        }
    }

    private List<File> arrayToList(File[] multipartFiles){
//    public List<MultipartFile> arrayToList(MultipartFile[] multipartFiles){
//        List<MultipartFile> list = new ArrayList<>();
        List<File> list = new ArrayList<>();
        Collections.addAll(list, multipartFiles);
        return list;
    }

    /**
     * thinks about what methods use to delete duplicate
     */
    private List<File> addsTwoLists(List<File> first, List<File> second){
//    public List<MultipartFile> addsTwoLists(List<MultipartFile> first, List<MultipartFile> second){
//        first.addAll(second);
        first.addAll(removeDuplicatedFiles(first, second));
        return first;
//        return first.stream().distinct().collect(Collectors.toList());
    }

    private List<File> removeDuplicatedFiles(List<File> first, List<File> second){
        for (int i = 0; i < first.size(); i++){
            for (int j = 0; j < second.size(); j++){
                System.out.println(first.get(i) + " " + second.get(j));
                if (first.get(i).equals(second.get(j))){
                    second.remove(j);
                }
            }
        }
        return second;
    }

}
