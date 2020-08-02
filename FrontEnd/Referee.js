var url ="http://132.72.65.125:8080/footballapp/referee";
var refUsername = JSON.parse(localStorage.getItem("profiles")).username;


$(document).ready(function() {
    gameTochoose();
    checknotes(refUsername);
    interval = setInterval(function (){
        checknotes(refUsername);}
        ,60*1000)

});

 function gamehaschoosen () {
     $('#playerselect').prop('disabled', false);
        playertochoose($('#gameselect').val());

};
function playertochoose(gameid){
    let dropdown = document.getElementById('playerselect');
    dropdown.length = 0;

    let defaultOption = document.createElement('option');
    defaultOption.text = 'Choose player';

    dropdown.add(defaultOption);
    dropdown.selectedIndex = 0;

    let myurl = url +'/players/'+gameid;

    const request = new XMLHttpRequest();
    request.open('GET', myurl, true);
    request.onload = function() {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText);
            let option;
            for (const argumentsKey in data) {
                option = document.createElement('option');
                option.text = data[argumentsKey];
                option.value = argumentsKey;
                dropdown.add(option);
            }
        }
    }
    request.send();

}
function gameTochoose(){
    let dropdown = document.getElementById('gameselect');
    let dropdown2 = document.getElementById('gameselect2');
    dropdown.length = 0;

    let defaultOption = document.createElement('option');
    let defaultOption2 = document.createElement('option');
    defaultOption.text = 'Choose game';
    defaultOption2.text = 'Choose game';

    dropdown.add(defaultOption);
    dropdown2.add(defaultOption2);
    dropdown.selectedIndex = 0;
    dropdown2.selectedIndex = 0;

    let myurl = url +'/games/'+refUsername;

    const request = new XMLHttpRequest();
    request.open('GET', myurl, true);
    request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    request.onload = function() {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText);
            let option;
            let option2;
            for (const argumentsKey in data) {
                option = document.createElement('option');
                option2 = document.createElement('option');

                option.text = data[argumentsKey];
                option.value = argumentsKey;

                option2.text = data[argumentsKey];
                option2.value = argumentsKey;

                dropdown.add(option);
                dropdown2.add(option2);

            }
        }
    }
    request.send();
}
function addEvent(){
    const request = {
        username: refUsername,
        eventtype: $('#selectType').val(),
        minute: $('#minute').val(),
        gameID: $('#gameselect').val(),
        playerusername: $('#playerselect').val()
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url+"/addEvent", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if ( xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'Event added',
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


function byEmail() {
    const request = {
        username: refUsername,
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
function sendreport(){
    const request = {
        username: refUsername,
        gameID: $('#gameselect2').val()
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url+"/addreport", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if ( xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'Report added',
                icon: 'success',
                confirmButtonText: 'OK'
            })
        }
        else{
            Swal.fire({
                title: 'Error!',
                text: 'You are just the second referee',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        }
    }
    xhr.send(json);
}
function switchdivs(newdiv) {
    var RefereePage= document.getElementById("RefereePage");
    var mainFrameTwo = document.getElementById(newdiv);
    var notifypage = document.getElementById("notify");
    var repage = document.getElementById("createReportPage");
    var eventpa = document.getElementById("addEventPage");
    var contact = document.getElementById("contactus");
    var email = document.getElementById("byEmail")


    if(newdiv=='contactus'){
        mainFrameTwo.style.display = 'block';
        RefereePage.style.display='none';
        notifypage.style.display='none'
        repage.style.display='none'
        eventpa.style.display='none';
        email.style.display='none';

    }
    if(newdiv=='byEmail'){
        mainFrameTwo.style.display = 'block';
        RefereePage.style.display='none';
        notifypage.style.display='none'
        repage.style.display='none'
        eventpa.style.display='none';
        contact.style.display = 'none';

    }

    if(newdiv== "addEventPage"){
        mainFrameTwo.style.display = 'block';
        RefereePage.style.display='none';
        notifypage.style.display='none'
        repage.style.display='none'
        contact.style.display = 'none';
        email.style.display='none';

    }

    if(newdiv== "createReportPage"){
        mainFrameTwo.style.display = 'block';
        RefereePage.style.display='none';
        notifypage.style.display='none';
        eventpa.style.display='none';
        contact.style.display = 'none';
        email.style.display='none';

    }
    if(newdiv== "notify"){
        removealertsSign();
        mainFrameTwo.style.display = 'block';
        RefereePage.style.display='none';
        eventpa.style.display='none';
        repage.style.display='none';
        contact.style.display = 'none';
        email.style.display='none';

    }
    if(newdiv== "RefereePage"){
        mainFrameTwo.style.display = 'block';
        eventpa.style.display='none';
        notifypage.style.display='none';
        repage.style.display='none';
        contact.style.display = 'none';
        email.style.display='none';

    }



}