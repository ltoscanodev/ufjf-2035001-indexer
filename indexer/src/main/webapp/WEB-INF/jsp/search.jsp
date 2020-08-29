<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pesquisar no índice</title>

        <link rel="icon" type="image/png" href="img/favicon.png"/>

        <link rel="stylesheet" href="css/global.css" />
        <link rel="stylesheet" href="css/search.css" />

        <script src="js/jquery-min.js"></script>
        <script src="js/font-awesome.js"></script>

        <script src="js/global.js"></script>
        <script src="js/search.js"></script>
    </head>
    <body onload="loaded()">
        <div class="search-wrap">
            <div class="search">
                <input id="search-input" type="text" class="search-input" placeholder="Pesquisar...">
                <button type="submit" class="search-button" onclick="search()">
                    <i class="fa fa-search"></i>
                </button>
            </div>
        </div>
    </body>
</html>