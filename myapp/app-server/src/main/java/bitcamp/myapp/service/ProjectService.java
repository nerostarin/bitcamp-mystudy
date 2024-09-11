package bitcamp.myapp.service;

import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.util.List;

public interface ProjectService {
    void add(Project project) throws Exception;

    List<Project> list() throws Exception;

    Project get(int projectNo) throws Exception;

    boolean update(Project project) throws Exception;

    boolean delete(int projectNo) throws Exception;

    void insertMember(int projectNo, List<User> member) throws Exception;

    void deleteMember(int projectNo) throws Exception;
}
