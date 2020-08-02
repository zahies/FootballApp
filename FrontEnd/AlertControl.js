var notification = 0;

function checknotes(name) {

    let myurl = 'http://132.72.65.125:8080/footballapp/alerts/myalerts/' + name ;
    const request = new XMLHttpRequest();
    request.open('GET', myurl, true);
    request.onload = function () {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText)
            let news = data.num;
            notification += parseInt(news);
            $('#note').text(notification);

            let option;
            for (let i = 0; i < parseInt(news); i++) {
                var node = document.createElement("P");
                let x = data.notedata[i];
                var textnode = document.createTextNode(x);
                node.style.background = 'blue';
                node.appendChild(textnode);
                document.getElementById("notify").insertBefore(node, document.getElementById("notify").firstChild);

            }
        }
    }
    request.send();
}

function removealertsSign() {

    let i = 0;
    $('#note').text('empty');
    $('#notify').children().each(function () {
        if (i >= notification) {
            this.style.background = 'white'
        }
        i++;
    });
    notification = 0;
}

function logout() {
    window.location.href = "index.html"


}