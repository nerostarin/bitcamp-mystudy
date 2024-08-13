package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDaoImpl implements ProjectDao {
    UserDaoImpl user;
    private Connection con;

    public ProjectDaoImpl(Connection con) {
        user = new UserDaoImpl(con);
        this.con = con;
    }

    @Override
    public boolean insert(Project project) throws Exception {
        try (Statement stmt = con.createStatement()) {
            String result = project.getMembers().stream()
                    .map(member -> String.valueOf(member.getNo())) // no 필드만 추출
                    .collect(Collectors.joining(","));
            stmt.executeUpdate(String.format(
                    "insert into myapp_projects(title, description, start_date, end_date, members)"
                            + "values('%s','%s','%tF','%tF','%s')",
                    project.getTitle(), project.getDescription(), project.getStartDate(), project.getEndDate(), result));
            return true;
        }
    }

    @Override
    public List<Project> list() throws Exception {
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("select * from myapp_projects order by project_id asc")) {
            ArrayList<Project> list = new ArrayList<>();

            while (rs.next()) {
                Project project = new Project();
                project.setNo(rs.getInt("project_id"));
                project.setTitle(rs.getString("title"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                list.add(project);
            }
            return list;
        }
    }

    @Override
    public Project findBy(int no) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement();

             // select 문 실행을 요청한다.
             ResultSet rs = stmt.executeQuery("select * from myapp_projects where project_id=" + no)) {

            if (rs.next()) { // select 실행 결과에서 1 개의 레코드를 가져온다.
                Project project = new Project();
                project.setNo(rs.getInt("project_id")); // 서버에서 가져온 레코드에서 user_id 컬럼 값을 꺼내 User 객체에 담는다.
                project.setTitle(rs.getString("title")); // 서버에서 가져온 레코드에서 name 컬럼 값을 꺼내 User 객체에 담는다.
                project.setDescription(rs.getString("description")); // 서버에서 가져온 레코드에서 email 컬럼 값을 꺼내 User 객체에 담는다.
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                String[] strArray = rs.getString("members").split(",");
                for (String nums : strArray) {
                    int num = Integer.parseInt(nums);
                    User members = user.findBy(num);
                    project.setMembers(members);
                }
                return project;
            }

            return null;
        }
    }

    @Override
    public boolean update(Project project) throws Exception {
        String result = project.getMembers().stream()
                .map(member -> String.valueOf(member.getNo())) // no 필드만 추출
                .collect(Collectors.joining(","));

        try (Statement stmt = con.createStatement()) {
            String query = String.format(
                    "UPDATE myapp_projects SET " +
                            "title = '%s', " +
                            "description = '%s', " +
                            "start_date = '%tF', " +
                            "end_date = '%tF', " +
                            "members = '%s' " +
                            "WHERE project_id = %d",
                    project.getTitle(),
                    project.getDescription(),
                    project.getStartDate(),
                    project.getEndDate(),
                    result,
                    project.getNo()
            );

            int count = stmt.executeUpdate(query);
            return count > 0;
        }
    }

    @Override
    public boolean delete(int no) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            // delete 문 전달
            int count = stmt.executeUpdate(String.format("delete from myapp_projects where project_id=%d", no));

            return count > 0;
        }
    }
}
