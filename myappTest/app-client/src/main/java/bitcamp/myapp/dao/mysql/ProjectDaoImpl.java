package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {
    UserDaoImpl user;
    private Connection con;

    public ProjectDaoImpl(Connection con) {
        //user = new UserDaoImpl(con);
        this.con = con;
    }

    @Override
    public boolean insert(Project project) throws Exception {
        int key = 0;
        try (PreparedStatement stmt = con.prepareStatement("insert into myapp_projects(title, description, start_date, end_date) values(?, ?, ?, ?)")) {
            stmt.setString(1, project.getTitle());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, project.getStartDate());
            stmt.setDate(4, project.getEndDate());
            stmt.executeUpdate();

            ResultSet rs = stmt.executeQuery("SELECT project_id FROM myapp_projects ORDER BY project_id DESC LIMIT 1");
            while (rs.next()) {
                key = rs.getInt("project_id");
            }
            for (User user : project.getMembers()) {
                PreparedStatement stmt2 = con.prepareStatement("insert into myapp_project_members(project_id, user_id) values(?, ?)");
                stmt2.setInt(1, key);
                stmt2.setInt(2, user.getNo());
                stmt2.executeUpdate();
            }
            return true;
        }
    }

    @Override
    public List<Project> list() throws Exception {
        try (PreparedStatement stmt = con.prepareStatement("select project_id, title, start_date, end_date from myapp_projects order by project_id asc"); ResultSet rs = stmt.executeQuery()) {
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
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT pt.project_id, pt.title, pt.description, pt.start_date, pt.end_date, GROUP_CONCAT(ft.user_id) as user_ids " +
                        "FROM myapp_projects pt " +
                        "JOIN myapp_project_members ft ON pt.project_id = ft.project_id " +
                        "WHERE pt.project_id = ? " +
                        "GROUP BY pt.project_id, pt.title, pt.description, pt.start_date, pt.end_date")) {
            stmt.setInt(1, no);

            try (ResultSet rs = stmt.executeQuery()) {

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
            }

            return null;
        }

    }

    @Override
    public boolean update(Project project) throws Exception {
        int count = 0;

        try (PreparedStatement stmt = con.prepareStatement("UPDATE myapp_projects SET title=?, description=?, start_date=?,end_date=? WHERE project_id=?")) {
            stmt.setString(1, project.getTitle());
            stmt.setString(2, project.getDescription());
            stmt.setDate(3, project.getStartDate());
            stmt.setDate(4, project.getEndDate());
            stmt.setInt(5, project.getNo());
            stmt.executeUpdate();

            PreparedStatement stmt2 = con.prepareStatement("delete from myapp_project_members where project_id=?");
            stmt2.setInt(1, project.getNo());
            stmt2.executeUpdate();

            for (User user : project.getMembers()) {
                PreparedStatement stmt3 = con.prepareStatement("insert into myapp_project_members(project_id, user_id) values(?, ?)");
                stmt3.setInt(1, project.getNo());
                stmt3.setInt(2, user.getNo());

                stmt3.executeUpdate();
            }
            ++count;
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean delete(int no) throws Exception {
        try (PreparedStatement stmt = con.prepareStatement("delete from myapp_project_members where project_id=?")) {
            stmt.setInt(1, no);
            stmt.executeUpdate();

            PreparedStatement stmt2 = con.prepareStatement("delete from myapp_projects where project_id=?");
            stmt2.setInt(1, no);

            return stmt2.executeUpdate() > 0;
        }
    }
}
