<%@ page
    language="java"
    contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ page import="bitcamp.myapp.vo.Board"%>
<%@ page import="bitcamp.myapp.vo.AttachedFile"%>

<jsp:include page="/header.jsp"/>

<h1>게시글 조회</h1>

<%
Board board = (Board) request.getAttribute("board");
if (board == null) {
%>

<p>없는 게시글입니다.</p>

<%
} else {
%>

<form action='/board/update' method="post">
      번호: <input readonly name='no' type='text' value='<%=board.getNo()%>'><br>
      제목: <input name='title' type='text' value='<%=board.getTitle()%>'><br>
      내용: <textarea name='content'><%=board.getContent()%></textarea><br>
      작성일: <input readonly type='text'
                     value='<%=String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", board.getCreatedDate())%>'><br>
      조회수: <input readonly type='text' value='<%=board.getViewCount()%>'><br>
      작성자: <input readonly type='text' value='<%=board.getWriter().getName()%>'><br>
      첨부파일: <br>
<% if (board.getAttachedFiles().size() > 0) { %>
      <ul>
<%    for (AttachedFile attachedFile : board.getAttachedFiles()) { %>
        <li>
        <a href="/download?path=board&fileNo=<%=attachedFile.getFileNo()%>"><%=attachedFile.getOriginFilename()%></a>
        <a href="/board/file/delete?boardNo=<%=board.getNo()%>&fileNo=<%=attachedFile.getFileNo()%>">[삭제]</a>
        </li>
<%    } %>
      </ul>
<% } %>
      <button>변경</button>
      <button type='button' onclick='location.href="/board/delete?no=<%=board.getNo()%>"'>삭제</button>
</form>

<%
}
%>

</body>
</html>