package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientApp {
    List<ApplicationListener> listeners = new ArrayList<>();
    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
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
        try {
            //상대편과 연결을 시도한다 연결이 되면 객체를 생성한후 리턴한다
            Socket socket = new Socket("127.0.0.1", 8888);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            appCtx.setAttribute("inputStream", in);
            appCtx.setAttribute("outputStream", out);

            //애플리케이션이 시작 될때 리스너에게 알린다
            for (ApplicationListener listener : listeners) {
                try {
                    listener.onStart(appCtx);
                } catch (Exception e) {
                    System.out.println("리스너 실행 중 오류 발생");
                }
            }
            System.out.println("[프로젝트 관리 시스템]");

            appCtx.getMainMenu().execute();

            out.writeUTF("quit");
            out.flush();

        } catch (Exception ex) {
            System.out.println("오류가 발생하였습니다.");
            ex.printStackTrace();
        }
        System.out.println("종료합니다.");

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
