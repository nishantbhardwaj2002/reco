<html>

<head>

    <meta charset="utf-8" />

    <link rel="stylesheet" href="http://localhost:8080/resources/newsfeedStyle.css" />

    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <title>Reco</title>

    <script>

        var context = "";

        function loadNewsItem(newsId) {

            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {

                    pTagForStory = document.getElementById("pTagForStory" + newsId);
                    pTagForStory.innerHTML = this.responseText;
                }
            };
            xhttp.open("POST", "newsItem", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("newsId=" + newsId);

            return false;
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
                            alert("No more news to load.");
                            continue;
                        }

                        const articleTag = document.createElement("article");

                        const aTagForImage = document.createElement("a");
                        aTagForImage.setAttribute("href", "#");
                        const imgTag = document.createElement("img");
                        imgTag.setAttribute("src", "http://pleasancefarm.co.uk/wp-content/uploads/2013/09/front-slide-280x164.jpg");
                        imgTag.setAttribute("alt", "news-image-not-loaded");
                        aTagForImage.appendChild(imgTag);

                        const h1TagForTitle = document.createElement("h1");
                        h1TagForTitle.appendChild(document.createTextNode(responseJson[newsId]));

                        const pTagForStory = document.createElement("p");
                        pTagForStory.setAttribute("id", "pTagForStory" + newsId)

                        const aTagForReadMore = document.createElement("a");
                        aTagForReadMore.setAttribute("href", "#");
                        aTagForReadMore.setAttribute("class", "readmore");
                        aTagForReadMore.setAttribute("onclick", "loadNewsItem('" + newsId + "')");
                        aTagForReadMore.appendChild(document.createTextNode("read-more"));

                        articleTag.appendChild(aTagForImage);
                        articleTag.appendChild(h1TagForTitle);
                        articleTag.appendChild(pTagForStory);
                        articleTag.appendChild(aTagForReadMore);

                        document.getElementById("newsSection").appendChild(articleTag);
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

<!--[if IE 6 ]><body class="ie6 old_ie"><![endif]-->
<!--[if IE 7 ]><body class="ie7 old_ie"><![endif]-->
<!--[if IE 8 ]><body class="ie8"><![endif]-->
<!--[if IE 9 ]><body class="ie9"><![endif]-->
<!--[if !IE]><!--><body><!--<![endif]-->

<header>
    <h1><a href="/index">Reco</a></h1>
    <nav>
        <ul>
            <li><a href="/signout">Signout</a></li>
        </ul>
    </nav>
</header>

<div id="adbanner">
    <div id="ad">
        <a href="#"><p>Advertise Here</p></a>
    </div>
</div>

<div id="secwrapper">
    <section id = "newsSection">
        <article id="featured">
            <a href="#"><img src="http://localhost:8080/resources/images/1.jpg" alt=""/></a>
            <img src="http://localhost:8080/resources/images/featured.png" alt="" id="featuredico"/>
            <h1>Varius Gravida Mi Volutpat</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tempor, tortor at vulputate blandit, magna risus posuere turpis, nec cursus ipsum arcu nec felis. Mauris sed lectus dui. Suspendisse enim elit, tempor ac ullamcorper et, eleifend quis sem. Sed euismod sagittis ligula, a imperdiet sapien molestie nec. Curabitur ut eros a justo fermentum vulputate ac sit amet metus.</p>
            <a href="#" class="readmore">Read more</a>
        </article>
    </section>
</div>

<footer>
    <a href= "#" onclick="loadNews()">Load more news?</a>
</footer>

</body>

</html>
		
		
			
    
    