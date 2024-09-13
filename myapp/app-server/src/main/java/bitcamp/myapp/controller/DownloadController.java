package bitcamp.myapp.controller;

import bitcamp.myapp.mybatis.annotaion.RequestMapping;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class DownloadController extends HttpServlet {

    private BoardService boardService;
    private Map<String, String> downloadPathMap = new HashMap<>();

    public DownloadController(BoardService boardService, ServletContext ctx) {
        this.boardService = boardService;
        this.downloadPathMap = downloadPathMap;
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest req, HttpServletResponse res) throws Exception {

        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            throw new Exception("로그인 하지 않았습니다.");
        }

        String path = req.getParameter("path");
        String downloadDir = downloadPathMap.get(path);

        if (path.equals("board")) {
            int fileNo = Integer.parseInt(req.getParameter("fileNo"));
            AttachedFile attachedFile = boardService.getAttachedFile(fileNo);

            res.setHeader(
                    "Content-Disposition",
                    String.format("attachment; filename=\"%s\"", attachedFile.getOriginFilename())
            );

            BufferedInputStream downloadFileIn = new BufferedInputStream(
                    new FileInputStream(downloadDir + "/" + attachedFile.getFilename()));
            OutputStream out = res.getOutputStream();

            int b;
            while ((b = downloadFileIn.read()) != -1) {
                out.write(b);
            }

            downloadFileIn.close();


        } else if (path.equals("user")) {

        } else {

        }
    }

}
