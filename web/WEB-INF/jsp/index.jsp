<%--
  Created by IntelliJ IDEA.
  UserModel: nishantbhardwaj2002
  Date: 3/3/17
  Time: 1:14 AM
  To change this template use File | Settings | File Templates.
--%>
<html>
    <Head>
        <title>Newsfeed</title>
        <script>
            var context = "";

            function loadNewsItem(newsId) {

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {

                        var paraItem = document.getElementById("newsListItem" + newsId + "BodyPara");
                        if(paraItem) {
                            paraItem.parentNode.removeChild(paraItem);
                        }
                        paraItem = document.createElement("p");
                        paraItem.setAttribute("id", "newsListItem" + newsId + "BodyPara");
                        paraItem.appendChild(document.createTextNode(this.responseText));

                        document.getElementById("newsListItem" + newsId).appendChild(paraItem);
                    }
                };
                xhttp.open("POST", "newsItem", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("newsId=" + newsId);
            }

            function loadNews() {

                var xhttp = new XMLHttpRequest();
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
                                alert("Error : No more news to load.");
                                continue;
                            }

                            const listItem = document.createElement("li");
                            listItem.setAttribute("id", "newsListItem" + newsId);

                            const paraItem = document.createElement("p");
                            paraItem.setAttribute("id", "newsListItem" + newsId + "HeadPara");
                            paraItem.setAttribute("onClick", "loadNewsItem('" + newsId + "')");
                            paraItem.appendChild(document.createTextNode(responseJson[newsId]))

                            listItem.appendChild(paraItem);
                            document.getElementById("newsList").appendChild(listItem);
                        }
                    }
                };
                xhttp.open("POST", "newsfeed", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("context=" + context);
            }
        </script>
    </Head>
    <Body>
        <a href="signout">Signout</a>
        <br />
        Hi ${username}!!!
        <br />
        <ul id="newsList"></ul>
        <input type="button" value="Load" onclick="loadNews()"/>
    </Body>
</html>
