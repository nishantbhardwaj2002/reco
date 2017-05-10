<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->

    <head>
	<title>Newsfeed (Reco)</title>

	<!-- Google Webfonts -->
	<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,500' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
	
	<!-- Animate.css -->
	<link rel="stylesheet" href="resources/hydrogen/css/animate.css">
	<!-- Icomoon Icon Fonts-->
	<link rel="stylesheet" href="resources/hydrogen/css/icomoon.css">
	<!-- Magnific Popup -->
	<link rel="stylesheet" href="resources/hydrogen/css/magnific-popup.css">
	<!-- Salvattore -->
	<link rel="stylesheet" href="resources/hydrogen/css/salvattore.css">
	<!-- Theme Style -->
	<link rel="stylesheet" href="resources/hydrogen/css/style.css">
	<!-- Modernizr JS -->
	<script src="resources/hydrogen/js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="resources/hydrogen/js/respond.min.js"></script>
	<![endif]-->
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>

	<script>

        var context = "";
        var columnNum = 0;

        function getDocHeight() {
            const documentVar = document;
            return Math.max(
                documentVar.body.scrollHeight, documentVar.documentElement.scrollHeight,
                documentVar.body.offsetHeight, documentVar.documentElement.offsetHeight,
                documentVar.body.clientHeight, documentVar.documentElement.clientHeight
            );
        }

        $(window).scroll(function() {
            if($(window).scrollTop() + $(window).height() == getDocHeight()) {
                loadNews();
            }
        });

        function loadNewsItem(newsId) {

            // Just inform server that this newsId has been clicked.
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST", "newsItem", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("newsId=" + newsId);
        }

        function loadNews() {

            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {

                    // Server should make sure that news don't repeat.
                    const responseJson = JSON.parse(this.responseText);
                    for(var newsId in responseJson){
                        if(context.localeCompare("") == 0) {
                            context = newsId;
                        } else {
                            context = context + "," + newsId;
                        }

                        if(newsId.localeCompare("-1") == 0) {
                            // alert("No more news to load.");
                            continue;
                        }

                        const newsItemJson = JSON.parse(responseJson[newsId]);

                        //const sizeAsClassDiv = document.createElement("div");
                        //sizeAsClassDiv.setAttribute("class", "column size-1of4");

                        const itemClassDiv = document.createElement("div");
                        itemClassDiv.setAttribute("class", "item");

                        const animateBoxDiv = document.createElement("div");
                        animateBoxDiv.setAttribute("class", "animate-box bounceIn animated");

                        const anchorTag = document.createElement("a");
                        anchorTag.setAttribute("class", "image-popup fh5co-board-img");
                        anchorTag.setAttribute("href", newsItemJson["url"]);
                        anchorTag.setAttribute("target", "_blank");
                        anchorTag.setAttribute("onclick", "loadNewsItem('" + newsId + "')");

                        const image = document.createElement("img");
                        // Add random number to eliminate caching of images
                        //image.setAttribute("src", "http://pipsum.com/300x" + Math.floor(200 + Math.random() * 100) + ".jpg?" + Math.random());

						image.setAttribute("src", newsItemJson["thumbnailUrl"]);
						image.setAttribute("alt", "Loading image...");

                        anchorTag.appendChild(image);
                        animateBoxDiv.appendChild(anchorTag);

                        const titleDiv = document.createElement("fh5co-desc");
                        titleDiv.setAttribute("class", "fh5co-desc");
                        titleDiv.appendChild(document.createTextNode(newsItemJson["head"]));

                        itemClassDiv.appendChild(animateBoxDiv);
                        itemClassDiv.appendChild(titleDiv);

                        //sizeAsClassDiv.appendChild(itemClassDiv);

                        //document.getElementById("fh5co-board").appendChild(sizeAsClassDiv);
                        document.getElementById("c" + columnNum%4).appendChild(itemClassDiv);
                        columnNum++;
                    }
                }
            };
            xhttp.open("POST", "newsfeed", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("context=" + context);

            return false;
        }

	</script>

	</head>
	<body onload="loadNews()" class>
		
	<div id="fh5co-offcanvass">
		<a href="#" class="fh5co-offcanvass-close js-fh5co-offcanvass-close">Menu <i class="icon-cross"></i> </a>
		<h1 class="fh5co-logo"><a class="navbar-brand" href="index"> </a></h1>
		<ul>
			<li class="active"><a href="newsfeed">Home</a></li>
			<li><a href="signout">Signout</a></li>
		</ul>
		<h3 class="fh5co-lead">Connect with us</h3>
		<p class="fh5co-social-icons">
			<a href="https://twitter.com/nishantbhar2002"><i class="icon-twitter"></i></a>
			<a href="https://www.facebook.com/nishantbhardwaj2002"><i class="icon-facebook"></i></a>
			<a href="https://www.instagram.com/nishantbhardwaj2002/"><i class="icon-instagram"></i></a>
		</p>
	</div>
	<header id="fh5co-header" role="banner">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<a href="#" class="fh5co-menu-btn js-fh5co-menu-btn">Menu <i class="icon-menu"></i></a>
                    <a class="navbar-brand" href="index.html">Reco</a>
                </div>
			</div>
		</div>
	</header>
	<!-- END .header -->
	
	
	<div id="fh5co-main">
		<div class="container">
            <div class="row">
                <div id="fh5co-board">
                    <!-- Stuff to be inserted here. -->
                    <div id="c0" class="column size-1of4"></div>
                    <div id="c1" class="column size-1of4"></div>
                    <div id="c2" class="column size-1of4"></div>
                    <div id="c3" class="column size-1of4"></div>
                </div>
            </div>
       </div>
	</div>

    <!--
	<footer id="fh5co-footer">
		<div class="container">
			<div class="row row-padded">
				<div class="col-md-12 text-center">
                    <input type="button" value="Load news" onclick="loadNews()">
                    <p class="fh5co-social-icons">
                        <a href="https://twitter.com/nishantbhar2002"><i class="icon-twitter"></i></a>
                        <a href="https://www.facebook.com/nishantbhardwaj2002"><i class="icon-facebook"></i></a>
                        <a href="https://www.instagram.com/nishantbhardwaj2002/"><i class="icon-instagram"></i></a>
                    </p>
				</div>
			</div>
		</div>
	</footer>
	-->

	<!-- jQuery -->
	<script src="resources/hydrogen/js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="resources/hydrogen/js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="resources/hydrogen/js/bootstrap.min.js"></script>
	<!-- Waypoints -->
	<script src="resources/hydrogen/js/jquery.waypoints.min.js"></script>
	<!-- Magnific Popup -->
	<script src="resources/hydrogen/js/jquery.magnific-popup.min.js"></script>
	<!-- Salvattore -->
	<script src="resources/hydrogen/js/salvattore.min.js"></script>
	<NewsFromSourceModelMain JS -->
	<script src="resources/hydrogen/js/main.js"></script>

	</body>
</html>
