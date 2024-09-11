package bitcamp.myapp.service;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class DefaultProjectService implements ProjectService {

    private ProjectDao projectDao;
    private SqlSessionFactory sqlSessionFactory;

    public DefaultProjectService(ProjectDao projectDao, SqlSessionFactory sqlSessionFactory) {
        this.projectDao = projectDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void add(Project project) throws Exception {
        try {
            projectDao.insert(project);
            if (project.getMembers() != null && project.getMembers().size() > 0) {
                projectDao.insertMembers(project.getNo(), project.getMembers());
            }
            sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public List<Project> list() throws Exception {
        return projectDao.list();
    }

    @Override
    public Project get(int projectNo) throws Exception {
        try {
            Project project = projectDao.findBy(projectNo);
            sqlSessionFactory.openSession(false).commit();
            return project;
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public boolean update(Project project) throws Exception {
        try {
            if (!projectDao.update(project)) {
                return false;
            }
            sqlSessionFactory.openSession(false).commit();
            return true;
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(int projectNo) throws Exception {
        try {
            if (!projectDao.delete(projectNo)) {
                return false;
            }
            sqlSessionFactory.openSession(false).commit();
            return true;
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public void insertMember(int projectNo, List<User> member) throws Exception {
        try {
            projectDao.insertMembers(projectNo, member);
            sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public void deleteMember(int projectNo) throws Exception {
        try {
            projectDao.deleteMembers(projectNo);
            sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }
}
