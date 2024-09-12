<%@ page 
    language="java"
    contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>실행 오류!</h1>
<pre>
<%
Exception e = (Exception) request.getAttribute("exception");
if (e != null) {
    e.printStackTrace(new PrintWriter(out));
}
%>
</pre>

</body>
</html>
