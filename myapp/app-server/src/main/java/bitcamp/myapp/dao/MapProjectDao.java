package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapProjectDao implements ProjectDao {

    private static final String DEFAULT_DATANAME = "projects";
    Map<Integer, Project> projectMap = new HashMap<>();
    List<Integer> projectNoList = new ArrayList<>();
    private int seqNo;
    private String path;
    private String dataName;

    public MapProjectDao(String path, UserDao userDao) {
        this(path, DEFAULT_DATANAME, userDao);
    }

    public MapProjectDao(String path, String dataName, UserDao userDao) {
        this.path = path;
        this.dataName = dataName;
        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheet(dataName);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Project project = new Project();
                Row row = sheet.getRow(i);
                project.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                project.setTitle(row.getCell(1).getStringCellValue());
                project.setDescription(row.getCell(2).getStringCellValue());
                project.setStartDate(row.getCell(3).getStringCellValue());
                project.setEndDate(row.getCell(4).getStringCellValue());
                String[] members = row.getCell(5).getStringCellValue().split(",");
                for (String memberNo : members) {
                    User member = userDao.findBy(Integer.parseInt(memberNo));
                    if (member != null) {
                        project.getMembers().add(member);
                    }
                }
                projectMap.put(project.getNo(), project);
                projectNoList.add(project.getNo());
            }
            seqNo = projectNoList.getLast();
        } catch (Exception e) {
            System.out.println("프로젝트 데이터 로딩중 오류 발생");
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        try (FileInputStream in = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(in)) {

            int sheetIndex = workbook.getSheetIndex(dataName);
            if (sheetIndex != -1) {
                workbook.removeSheetAt(sheetIndex);
            }

            XSSFSheet sheet = workbook.createSheet(dataName);

            String[] cellHeaders = {"no", "title", "description", "start_date", "end_date", "members"};
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < cellHeaders.length; i++) {
                headerRow.createCell(i).setCellValue(cellHeaders[i]);
            }
            int row = 1;
            for (Integer no : projectNoList) {
                Project project = projectMap.get(no);
                Row dataRow = sheet.createRow(row++);
                dataRow.createCell(0).setCellValue(String.valueOf(project.getNo()));
                dataRow.createCell(1).setCellValue(project.getTitle());
                dataRow.createCell(2).setCellValue(project.getDescription());
                dataRow.createCell(3).setCellValue(project.getStartDate());
                dataRow.createCell(4).setCellValue(project.getEndDate());

                StringBuilder strBuilder = new StringBuilder();
                for (User member : project.getMembers()) {
                    if (strBuilder.length() > 0) {
                        strBuilder.append(",");
                    }
                    strBuilder.append(member.getNo());
                }
                dataRow.createCell(5).setCellValue(strBuilder.toString());
            }
            //엑셀파일로 데이터를 출력하기 전에
            //workbook을 위해 연결한 입력 스트림을 먼저 종료한다
            in.close();

            try (FileOutputStream out = new FileOutputStream(path)) {
                workbook.write(out);
            }
        }
    }

    @Override
    public boolean insert(Project project) throws Exception {
        project.setNo(++seqNo);
        projectMap.put(project.getNo(), project);
        projectNoList.add(project.getNo());
        return true;
    }

    @Override
    public List<Project> list() throws Exception {
        ArrayList<Project> projects = new ArrayList<>();
        for (Integer no : projectNoList) {
            projects.add(projectMap.get(no));
        }
        return projects;
    }

    @Override
    public Project findBy(int no) throws Exception {
        return projectMap.get(no);
    }

    @Override
    public boolean update(Project project) throws Exception {
        if (!projectMap.containsKey(project.getNo())) {
            return false;
        }
        projectMap.put(project.getNo(), project);
        return true;
    }

    @Override
    public boolean delete(int no) throws Exception {
        if (projectMap.remove(no) == null) {
            return false;
        }
        projectNoList.remove(Integer.valueOf(no));
        return true;
    }
}
