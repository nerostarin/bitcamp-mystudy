package bitcamp.myapp.controller;

import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private UserService userService;
    private StorageService storageService;

    private String folderName = "user/";

    public UserController(UserService userService, StorageService storageService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/user/add")
    public String add(User user, MultipartFile file) throws Exception {
        //클라이언트가 보낸 파일을 저장 할 때 다른 파일의 이름과 충돌이 나지 않도록  임의의 새 파일 이름을 생성한다
        String fileName = UUID.randomUUID().toString();

        HashMap<String, Object> options = new HashMap<>();
        options.put(StorageService.CONTENT_TYPE, file.getContentType());
        storageService.upload(
                folderName + fileName,
                file.getInputStream(), options);
        user.setPhoto(fileName);//디비에 저장할때 사용할 사진 파일 이름 설정
        userService.add(user);
        return "redirect:list";
    }

    @GetMapping("/user/list")
    public String list(Model model) throws Exception {
        List<User> list = userService.list();
        model.addAttribute("list", list);
        return "user/list";
    }

    @GetMapping("/user/view")
    public String view(int no, Model model) throws Exception {
        User user = userService.get(no);
        model.addAttribute("user", user);
        return "user/view";
    }

    @PostMapping("/user/update")
    public String update(User user) throws Exception {
        if (userService.update(user)) {
            return "redirect:list";
        } else {
            throw new Exception("없는 회원입니다!");
        }
    }

    @GetMapping("/user/delete")
    public String delete(int no) throws Exception {
        if (userService.delete(no)) {
            return "redirect:list";
        } else {
            throw new Exception("없는 회원입니다.");
        }
    }
}
