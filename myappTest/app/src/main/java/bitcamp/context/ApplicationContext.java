package bitcamp.context;

import bitcamp.menu.MenuGroup;

public class ApplicationContext {
    MenuGroup mainMenu = new MenuGroup("메인");

    public MenuGroup getMainMenu() {
        return mainMenu;
    }
}
