package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.listener.AuthApplicationListener;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.util.ArrayList;
import java.util.List;

public class ClientApp {
    List<ApplicationListener> listeners = new ArrayList<>();
    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
        app.addApplicationListener(new InitApplicationListener());
        app.addApplicationListener(new AuthApplicationListener());
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

            for (ApplicationListener listener : listeners) {
                try {
                    if (!listener.onStart(appCtx)) {
                        System.out.println("종료합니다");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("리스너 실행 중 오류 발생");
                    e.printStackTrace();
                }
            }
            System.out.println("[프로젝트 관리 시스템]");

            appCtx.getMainMenu().execute();

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
