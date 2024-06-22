package bitcamp.myapp.command;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.User;

public class UserCommand {

    private static int userNo = 0;
    UserList userList = new UserList();

    public void executeUserCommand(String command) {
        System.out.printf("[%s]\n", command);
        switch (command) {
            case "등록":
                addUser();
                break;

            case "조회":
                viewUser();
                break;

            case "목록":
                listUser();
                break;

            case "변경":
                updateUser();
                break;

            case "삭제":
                deleteUser();
                break;
        }
    }

    private void addUser() {
        User user = new User();
        user.setName(Prompt.input("이름?"));
        user.setEmail(Prompt.input("이메일?"));
        user.setPassword(Prompt.input("암호?"));
        user.setTel(Prompt.input("연락처?"));
        user.setNo(User.getNextSeqNo());
        userList.add(user);
    }

    private void listUser() {
        System.out.println("번호 이름 이메일");
        for (Object obj : userList.toArray()) {
            User user = (User) obj;
            System.out.printf("%d %s %s\n", user.getNo(), user.getName(), user.getEmail());
            /*
            이렇게도 되는데 실행오류가 난다?
             for (Object user userList.toArray()) {
            System.out.printf("%d %s %s\n", ((User)user).getNo(), user.getName(), user.getEmail());
            }
            * */
        }
    }

    private void viewUser() {
        userNo = Prompt.inputInt("회원번호?");
        User user = userList.findByNo(userNo);
        if (user == null) {
            System.out.println("없는 회원입니다.");
            return;
        }
        System.out.printf("이름: %s \n", user.getName());
        System.out.printf("이메일: %s \n", user.getEmail());
        System.out.printf("전화번호: %s \n", user.getTel());
    }

    private void updateUser() {
        userNo = Prompt.inputInt("회원번호?");
        User user = userList.findByNo(userNo);
        if (user == null) {
            System.out.println("없는 회원입니다.");
            return;
        }
        user.setName(Prompt.input("이름 (%s)?", user.getName()));
        user.setEmail(Prompt.input("이메일 (%s)?", user.getEmail()));
        user.setPassword(Prompt.input("암호?"));
        user.setTel(Prompt.input("연락처 (%s)?", user.getTel()));
    }

    private void deleteUser() {
        userNo = Prompt.inputInt("회원번호?");
        User deletedUser = userList.findByNo(userNo);
        if (deletedUser != null) {
            userList.remove(userList.indexOf(deletedUser));
            System.out.printf("%s 삭제 했습니다\n", deletedUser.getName());
            return;
        }
        System.out.println("없는 회원입니다.");
    }

    public UserList getUserList() {
        return userList;
    }
}
