<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado da pesquisa</title>
        
        <link rel="icon" type="image/png" href="img/favicon.png"/>

        <link rel="stylesheet" href="css/global.css" />
        <link rel="stylesheet" href="css/result.css" />

        <script src="js/jquery-min.js"></script>
        <script src="js/font-awesome.js"></script>
        
        <script src="js/global.js"></script>
        <script src="js/search.js"></script>
    </head>
    <body onload="loaded()">
        <div class="container">
            <div>
                <div class="search-wrap">
                    <div class="search">
                        <input id="search-input" type="text" class="search-input" placeholder="Pesquisar...">
                        <button type="submit" class="search-button" onclick="search()"><i class="fa fa-search"></i></button>
                    </div>
                </div>
            </div>
            <div>
                <div class="result-wrap">
                    <c:if test="${not empty resultList}">
                        <c:forEach var="result" items="${resultList}">
                            <div>
                                <a href="${result.link}">
                                    <h2>${result.headline}</h2>
                                </a>
                                <p>${result.shortDescription}</p>
                                <p>${result.authors}, ${result.date}</p>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
