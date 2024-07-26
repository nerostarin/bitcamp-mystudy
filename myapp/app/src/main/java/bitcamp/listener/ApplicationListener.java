package bitcamp.listener;

import bitcamp.context.ApplicationContext;

//어플리케이션 상태 변경을 알림 받을 객체의 호출 규칙
public interface ApplicationListener {
    void onStart(ApplicationContext ctx);//어플리케이션이 시작될 때 호풀된다

    void onShutDown(ApplicationContext ctx);//어플리케이션이 종료 될때 호출됨
}
