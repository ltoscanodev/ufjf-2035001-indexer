<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erro</title>
    </head>
    <body>
        <h1>Error Page</h1>
        <p>Application has encountered an error.</p>


        <p>Failed URL: ${url}</p>
        <p>Exception:  ${exception.message}</p>
        <c:forEach items="${exception.stackTrace}" var="ste">${ste}<br></c:forEach>
</body>
</html>
