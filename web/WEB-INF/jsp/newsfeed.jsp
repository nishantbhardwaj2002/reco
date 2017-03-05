<%@ page import="reco.model.UserModel" %>
<%--
  Created by IntelliJ IDEA.
  UserModel: nishantbhardwaj2002
  Date: 3/3/17
  Time: 1:14 AM
  To change this template use File | Settings | File Templates.
--%>
<html>
    <head>
        <title>Newsfeed</title>
        <script>
            var context = "";

            function loadNewsItem(id) {

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        const responseJson = JSON.parse(this.responseText);

                        var paraItem = document.getElementById("newsListItem" + id + "BodyPara");
                        if(paraItem) {
                            paraItem.parentNode.removeChild(paraItem);
                        }
                        paraItem = document.createElement("p");
                        paraItem.setAttribute("id", "newsListItem" + id + "BodyPara");
                        paraItem.appendChild(document.createTextNode(responseJson[id]));

                        document.getElementById("newsListItem" + id).appendChild(paraItem);
                    }
                };
                xhttp.open("POST", "newsItemService", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("id=" + id);
            }

            function loadNews() {

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        if(this.responseText == "") {
                            return;
                        }
                        // Server should make sure that news don't repeat.
                        const responseJson = JSON.parse(this.responseText);
                        for(var id in responseJson){
                            context = id;

                            const listItem = document.createElement("li");
                            listItem.setAttribute("id", "newsListItem" + id);

                            const paraItem = document.createElement("p");
                            paraItem.setAttribute("id", "newsListItem" + id + "HeadPara");
                            paraItem.setAttribute("onClick", "loadNewsItem(" + id + ")");
                            paraItem.appendChild(document.createTextNode(responseJson[id]))

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
    </head>
    <body>
        Hi ${username}!!!
        <br />
        <a href="signout">Signout</a>
        <br />
        <ul id="newsList"></ul>
        <input type="button" value="Load" onclick="loadNews()"/>
    </body>
</html>
