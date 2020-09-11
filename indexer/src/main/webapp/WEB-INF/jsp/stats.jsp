<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estatísticas do índice</title>

        <link rel="icon" type="image/png" href="img/favicon.png"/>

        <link rel="stylesheet" href="css/global.css" />
        <link rel="stylesheet" href="css/result.css" />

        <script src="js/jquery-min.js"></script>
        <script src="js/font-awesome.js"></script>

        <script src="js/global.js"></script>
    </head>
    <body onload="loaded()">
        <div class="container">
            <div class="center">
                <a href="/">Retornar para a pesquisa</a>
            </div>
            <div class="center">
                <p><b>Tamanho do dataset:</b> ${datasetSize} itens</p>
                <p><b>Quantidade de entradas no índice:</b> ${indexEntries} entradas</p>
                <p><b>Tempo de construção do índice:</b> ${indexBuildTime} seg</p>
            </div>
            <c:if test="${not empty queryStats}">
                <div class="result-wrap">
                    <p><b>Última consulta:</b> "${query}"</p>
                    <p><b>Tempo da última consulta:</b> ${queryTime} ms</p>
                    <c:if test="${empty queryResultList}">
                        <p><b>Resultado:</b> Nenhum resultado encontrado</p>
                    </c:if>
                    <c:if test="${not empty queryResultList}">
                        <p><b>Resultado:</b></p>
                        <c:forEach var="result" items="${queryResultList}">
                            <div>
                                <p>[${result.relevance}] - ${result.news.headline}</p>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </c:if>
        </div>
    </body>
</html>
