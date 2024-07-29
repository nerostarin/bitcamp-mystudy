package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.dao.skel.BoardDaoSkel;
import bitcamp.myapp.dao.skel.ProjectDaoSkel;
import bitcamp.myapp.dao.skel.UserDaoSkel;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    List<ApplicationListener> listeners = new ArrayList<>();
    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        ServerApp app = new ServerApp();
        app.addApplicationListener(new InitApplicationListener());
        app.execute();
    }

    private void addApplicationListener(ApplicationListener listener) {
        listeners.add(listener);
    }

    private void removeApplicationListener(ApplicationListener listener) {
        listeners.remove(listener);
    }

    void execute() {

        //애플리케이션이 시작 될때 리스너에게 알린다
        for (ApplicationListener listener : listeners) {
            try {
                listener.onStart(appCtx);
            } catch (Exception e) {
                System.out.println("리스너 실행 중 오류 발생");
            }
        }

        UserDaoSkel userDaoSkel = (UserDaoSkel) appCtx.getAttribute("userDaoSkel");
        BoardDaoSkel boardDaoSkel = (BoardDaoSkel) appCtx.getAttribute("boardDaoSkel");
        ProjectDaoSkel projectDaoSkel = (ProjectDaoSkel) appCtx.getAttribute("projectDaoSkel");


        System.out.println("서버 프로젝트 관리 시스템 시작");

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("서버실행중 .....");

            try (Socket socket = serverSocket.accept()) {
                System.out.println("클라이언트와 연결 되었음");

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                while (true) {

                    String dataName = in.readUTF();
                    if (dataName.equals("quit")) {
                        break;
                    }

                    switch (dataName) {
                        case "users":
                            userDaoSkel.service(in, out);
                            break;
                        case "projects":
                            projectDaoSkel.service(in, out);
                            break;
                        case "boards":
                            boardDaoSkel.service(in, out);
                            break;
                        default:

                    }
                }
            }
        } catch (Exception e) {
            System.out.println("통신중 오류 발생");
            e.printStackTrace();
        }

        System.out.println("종료합니다");

        Prompt.close();

        //애플리케이션이 종료 될때 리스너에게 알린다
        for (ApplicationListener listener : listeners) {
            try {
                listener.onShutDown(appCtx);
            } catch (Exception e) {
                System.out.println("종료되는 리스너 실행 중 오류 발생");
            }
        }
    }
}
