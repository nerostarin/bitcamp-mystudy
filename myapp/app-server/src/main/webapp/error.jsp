<%@ page 
        contentType="text/html;charset=UTF-8"
        pageEncoding = "UTF-8"
%>
<%@ page import="java.io.PrintWriter"%>

<jsp:include page="/header.jsp"/>
        <h1>실행중에 오류가 발생하였습니다</h1>
        <pre>
        <%
                Exception e = (Exception) request.getAttribute("exception");
                if(e != null){
                        e.printStackTrace(new PrintWriter(out));
                }_
        %>
        </pre>
        
        <p></p>
</body>
</html>