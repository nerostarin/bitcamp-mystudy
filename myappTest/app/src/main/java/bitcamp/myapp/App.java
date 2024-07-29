package bitcamp.myapp;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.command.listener.InitApplicationListener;
import bitcamp.util.Prompt;

import java.util.ArrayList;
import java.util.List;

public class App {

    List<ApplicationListener> listeners = new ArrayList<>();

    ApplicationContext appCtx = new ApplicationContext();

    public static void main(String[] args) {
        App app = new App();
        app.addEventListener(new InitApplicationListener());
        app.execute();
    }

    public void addEventListener(InitApplicationListener app) {
        listeners.add(app);
    }

    void execute() {

        for (ApplicationListener listener : listeners) {
            listener.onStart(appCtx);
        }
        try {
            appCtx.getMainMenu().execute();

        } catch (Exception ex) {
            System.out.println("실행 오류!");
            ex.printStackTrace();
        }

        System.out.println("종료합니다.");

        Prompt.close();
    }
}
