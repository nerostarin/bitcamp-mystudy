package bitcamp.myapp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int no;
    private String name;
    private String email;
    private String password;
    private String tel;
    private String photo;

    public User(int no) {
        this.no = no;
    }

    public User() {
    }
}
