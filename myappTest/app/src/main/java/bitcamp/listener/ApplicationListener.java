package bitcamp.listener;

import bitcamp.context.ApplicationContext;

public interface ApplicationListener {
    void onShutdown();

    void onStart(ApplicationContext ctx);
}
