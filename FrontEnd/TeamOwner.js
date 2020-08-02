var url = "http://132.72.65.125:8080/footballapp/teamowner";
var teamOwnerUN = JSON.parse(localStorage.getItem("profiles")).username;

$(document).ready(function () {
    seeTheTeam();
    checknotes(teamOwnerUN);
    interval = setInterval(function () {
            checknotes(teamOwnerUN);
        }
        , 60 * 1000)

});


function byEmail() {
    const request = {
        username: teamOwnerUN,
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
    var TeamOwnerPage = document.getElementById("TeamOwnerPage");
    var notific = document.getElementById("notify");
    var newteam = document.getElementById("CreatenewteamPage");
    var mainFrameTwo = document.getElementById(newdiv);
    var seeTeam = document.getElementById("Seemyteam");
    var contact = document.getElementById("contactus");
    var email = document.getElementById("byEmail")

    if(newdiv=='byEmail'){
        mainFrameTwo.style.display = 'block';
        TeamOwnerPage.style.display = 'none';
        notific.style.display = 'none';
        seeTeam.style.display = 'none';
        newteam.style.display = 'none';
        contact.style.display = 'none';
    }

    if(newdiv=='contactus'){
        mainFrameTwo.style.display = 'block';
        TeamOwnerPage.style.display = 'none';
        notific.style.display = 'none';
        seeTeam.style.display = 'none';
        newteam.style.display = 'none';
        email.style.display = 'none';

    }

    if (newdiv == "CreatenewteamPage") {
        leaguehaschoosen();
        mainFrameTwo.style.display = 'block';
        TeamOwnerPage.style.display = 'none';
        notific.style.display = 'none';
        seeTeam.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';

    }

    if (newdiv == "Seemyteam") {

        mainFrameTwo.style.display = 'block';
        TeamOwnerPage.style.display = 'none';
        notific.style.display = 'none';
        newteam.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';

    }

    if (newdiv == "TeamOwnerPage") {
        mainFrameTwo.style.display = 'block';
        notific.style.display = 'none';
        newteam.style.display = 'none';
        seeTeam.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';

    }
    if (newdiv == "notify") {
        mainFrameTwo.style.display = 'block';
        TeamOwnerPage.style.display = 'none';
        newteam.style.display = 'none';
        seeTeam.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';

    }

}

function seeTheTeam() {

    let myurl = url + '/myteam/' + teamOwnerUN ;

    const request = new XMLHttpRequest();
    request.open('GET', myurl, true);
    request.onload = function () {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText);
            let playerdata = data.players;
            for (const argumentsKey in playerdata) {
                let tdplayer = document.createElement("tr");
                tdplayer.innerHTML = playerdata[argumentsKey];
                $("#playerTable").append(tdplayer);
            }
            let coachdata = data.coaches;
            for (const argumentsKey in coachdata) {
                let tdcoach = document.createElement("tr");
                tdcoach.innerHTML = coachdata[argumentsKey];
                $("#assetTable").append(tdcoach);
            }
            let managersdata = data.managers;
            for (const argumentsKey in managersdata) {
                let tdmanager = document.createElement("tr");
                tdmanager.innerHTML = managersdata[argumentsKey];
                $("#assetTable").append(tdmanager);
            }
            let stadium = data.field;
            if(data.field!=undefined) {
                let tdfield = document.createElement("tr");
                tdfield.innerHTML = stadium;
                $("#assetTable").append(tdfield);
            }

        }
    }
    request.send();


}

function leaguehaschoosen() {
    let dropdown = document.getElementById('leagueselect');
    dropdown.length = 0;

    let defaultOption = document.createElement('option');
    defaultOption.text = 'Choose league';

    dropdown.add(defaultOption);
    dropdown.selectedIndex = 0;

    let myurl = url + '/leagues';

    const request = new XMLHttpRequest();
    request.open('GET', myurl, true);

    request.onload = function () {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText);
            let option;
            for (const argumentsKey in data) {
                option = document.createElement('option');
                option.text = data[argumentsKey];
                option.value = data[argumentsKey];
                dropdown.add(option);

            }
        }
    }
    request.send();
}

function addNewTeam() {
    const request = {
        username: teamOwnerUN,
        leagueID: $('#leagueselect').val(),
        year: $('#year').val(),
        team_name: $('#teamName').val(),
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url + "/signteam", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'Team Request Sent To commissioner',
                icon: 'success',
                confirmButtonText: 'OK'
            })
        } else {
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