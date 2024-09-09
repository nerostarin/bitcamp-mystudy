package bitcamp.myapp.vo;

public class AttachedFile {
    private int fileNo;
    private String filename;
    private String originFilename;
    private int boardNo;

    @Override
    public String toString() {
        return "AttachedFile{" +
                "originFilename='" + originFilename + '\'' +
                ", boardNo=" + boardNo +
                ", filename='" + filename + '\'' +
                ", fileNo=" + fileNo +
                '}';
    }

    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOriginFilename() {
        return originFilename;
    }

    public void setOriginFilename(String originFilename) {
        this.originFilename = originFilename;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }
}
