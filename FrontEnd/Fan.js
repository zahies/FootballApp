var fanName = JSON.parse(localStorage.getItem("profiles")).username;
var url ="http://132.72.65.125:8080/footballapp/commissioner";

$(document).ready(function() {
    checknotes(fanName);
    interval = setInterval(function (){
            checknotes(fanName);}
        ,80*1000)

});

function byEmail() {
    const request = {
        username: fanName,
        mail: $('#emails').val(),
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url+"/setViaMail", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if ( xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'You will now receive notifications for the requested email: '+$('#emails').val(),
                icon: 'success',
                confirmButtonText: 'OK'
            })
        }
        else{
            Swal.fire({
                title: 'Error!',
                text: 'some thing went wrong',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        }
    }
    xhr.send(json);


}


function switchdivs(newdiv) {
    var mainFrameTwo = document.getElementById(newdiv);
    var notifypage = document.getElementById("notify");
    var repage = document.getElementById("FanHomePage");
    var contact = document.getElementById("contactus");
    var email = document.getElementById("byEmail")

    if (newdiv == 'byEmail') {
        mainFrameTwo.style.display = 'block';
        notifypage.style.display = 'none';
        contact.style.display = 'none';
        repage.style.display = 'none';

    }

    if (newdiv == 'FanHomePage') {
        mainFrameTwo.style.display = 'block';
        notifypage.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';

    }

    if (newdiv == "notify") {
        mainFrameTwo.style.display = 'block';
        repage.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';
    }
    if (newdiv == "contactus") {
        mainFrameTwo.style.display = 'block';
        repage.style.display = 'none';
        notifypage.style.display = 'none';
        email.style.display = 'none';
    }
}