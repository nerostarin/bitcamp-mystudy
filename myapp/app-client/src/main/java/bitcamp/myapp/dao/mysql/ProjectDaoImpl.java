package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {
    UserDaoImpl user;
    private Connection con;

    public ProjectDaoImpl(Connection con) {
        user = new UserDaoImpl(con);
        this.con = con;
    }

    @Override
    public boolean insert(Project project) throws Exception {
        int key = 0;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(String.format(
                    "insert into myapp_projects(title, description, start_date, end_date)"
                            + "values('%s','%s','%tF','%tF')",
                    project.getTitle(), project.getDescription(), project.getStartDate(), project.getEndDate()));
            ResultSet rs = stmt.executeQuery("SELECT * FROM myapp_projects ORDER BY project_id DESC LIMIT 1");
            while (rs.next()) {
                key = rs.getInt("project_id");
            }
            for (User user : project.getMembers()) {
                stmt.executeUpdate(String.format("insert into myapp_project_members(project_id, user_id)" + "values(%d, %d)", key, user.getNo()));
            }
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
             ResultSet rs = stmt.executeQuery(
                     "SELECT pt.project_id, pt.title, pt.description, pt.start_date, pt.end_date, GROUP_CONCAT(ft.user_id) as user_ids " +
                             "FROM myapp_projects pt " +
                             "JOIN myapp_project_members ft ON pt.project_id = ft.project_id " +
                             "WHERE pt.project_id = " + no + " " +
                             "GROUP BY pt.title, pt.description, pt.start_date, pt.end_date")) {

            if (rs.next()) { // select 실행 결과에서 1 개의 레코드를 가져온다.
                Project project = new Project();
                project.setNo(rs.getInt("project_id"));
                project.setTitle(rs.getString("title"));
                project.setDescription(rs.getString("description"));
                project.setStartDate(rs.getDate("start_date"));
                project.setEndDate(rs.getDate("end_date"));
                String[] strArray = rs.getString("user_ids").split(",");
                
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

        try (Statement stmt = con.createStatement()) {
            String query = String.format(
                    "UPDATE myapp_projects SET " +
                            "title = '%s', " +
                            "description = '%s', " +
                            "start_date = '%tF', " +
                            "end_date = '%tF'" +
                            "WHERE project_id = %d",
                    project.getTitle(),
                    project.getDescription(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getNo()
            );

            stmt.executeUpdate(String.format("delete from myapp_project_members where project_id=%d", project.getNo()));

            for (User user : project.getMembers()) {
                stmt.executeUpdate(String.format("insert into myapp_project_members(project_id, user_id)" + "values(%d, %d)", project.getNo(), user.getNo()));
            }

            int count = stmt.executeUpdate(query);
            return count > 0;
        }
    }

    @Override
    public boolean delete(int no) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(String.format("delete from myapp_project_members where project_id=%d", no));
            // delete 문 전달
            int count = stmt.executeUpdate(String.format("delete from myapp_projects where project_id=%d", no));

            return count > 0;
        }
    }
}
