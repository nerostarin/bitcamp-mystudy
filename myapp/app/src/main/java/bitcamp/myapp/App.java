package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.util.ArrayList;
import java.util.List;

public class App {
    List<ApplicationListener> listeners = new ArrayList<>();

    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        App app = new App();
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

        try {
            appCtx.getMainMenu().execute();
        } catch (Exception ex) {
            System.out.println("오류가 발생하였습니다.");
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
