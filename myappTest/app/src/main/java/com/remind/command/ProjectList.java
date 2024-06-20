package com.remind.command;

import com.remind.vo.Project;
import com.remind.vo.User;

public class ProjectList {
    private static final int MAX_SIZE = 100;
    private  static Project[] projects = new Project[MAX_SIZE];
    private static  int projectLength = 0;

    public static void add(Project project)
    {
        projects[projectLength++] = project;
    }

    public static Project[] toArray()
    {
        Project[] arr = new Project[projectLength];
        for (int i = 0 ; i < projectLength; i++)
        {
            arr[i] = projects[i];
        }
        return arr;
    }

    public static Project delete(int projectNo)
    {
        Project deleteProject = ProjectList.findByNo(projectNo);
        if(deleteProject == null)
        {
            return null;
        }
        int index = indexOf(deleteProject);
        for(int i = index + 1; i < projectLength; i++)
        {
            projects[i-1] = projects[i];
        }
        projects[projectLength--] = null;
        return deleteProject;
    }
    public static Project findByNo(int userNo) {
        for (int i = 0 ; i < projectLength; i++)
        {
            Project project = projects[i];
            if (project.getNo() == userNo)
            {
                return project;
            }
        }
        return null;
    }

    public static int indexOf( Project project)
    {
        for (int i = 0; i< projectLength; i++)
        {
            if (project == projects[i])
            {
                return i;
            }
        }
        return -1;
    }
}
