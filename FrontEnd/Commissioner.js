var url = "http://132.72.65.125:8080/footballapp/commissioner";
var commUserName = JSON.parse(localStorage.getItem("profiles")).username;

var interval;

$('#wellcomecomm').text("Welcome back " + commUserName + " to your Commissioner page ")



$(document).ready(function ()
{
    getTeamReq();
    checknotes(commUserName);
    interval = setInterval(function () {
            checknotes(commUserName);
        },
        60*1000)

});


function leaguechoosen() {
    $('#leagueTable').children().each(function () {
        this.remove();
    });
    seeLeagues($('#leagueID3').val());

};

function seeLeagues(leagueID) {
    let myurl = url + '/leagues/' + leagueID;

    const request = new XMLHttpRequest();
    request.open('GET', myurl, true);

    request.onload = function () {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText);
            for (const argumentsKey in data) {
                let node = document.createElement("tr");
                let td1 = document.createElement("td");
                let td2 = document.createElement("td");
                let td3 = document.createElement("td");
                td1.innerHTML = argumentsKey;
                td2.innerHTML = data[argumentsKey][0];
                td3.innerHTML = data[argumentsKey][1];
                node.appendChild(td1);
                node.appendChild(td2);
                node.appendChild(td3);
                $("#leagueTable").append(node);
            }
        }
    }
    request.send();


}


function byEmail() {
    const request = {
        username: commUserName,
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


function getleagues(eleid) {
    let dropdown = document.getElementById(eleid);
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

function addScorePolicy() {
    const request = {
        username: commUserName,
        leagueID: $('#leagueID').val(),
        year: $('#year').val(),
        winval: $('#winval').val(),
        drawval: $('#drawval').val(),
        loseval: $('#loseval').val()
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url + "/addScorePolicy", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'Score Policy Added',
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

function defineBudget() {

    const request = {
        username: commUserName,
        description: $('#descri').val(),
        ruleAmount: $('#payment').val()
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url + "/addCommissionerRule", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'Budget Policy Added',
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

function acceptTeam(node) {
    const request = {
        username: commUserName,
        teamname: node.childNodes[1].innerText,
        apply: true
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url + "/applyRequest", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.status == "200") {
            node.remove();
            Swal.fire({
                title: 'Great!',
                text: 'You approved the new team!!',
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

function declineTeam(node) {
    const request = {
        username: commUserName,
        teamname: node.childNodes[1].innerText,
        apply: false
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url + "/applyRequest", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.status == "200") {
            node.remove();
            Swal.fire({
                title: 'Great!',
                text: 'You didnt approved the new team!!',
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

function getTeamReq() {
    let myurl = url + '/proveTeam/' + commUserName ;

    const request = new XMLHttpRequest();
    request.open('GET', myurl, true);

    request.onload = function () {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText);
            for (const argumentsKey in data) {
                let node = document.createElement("tr");
                let td1 = document.createElement("td");
                td1.innerHTML = data[argumentsKey].username;
                node.appendChild(td1);
                let td2 = document.createElement("td");
                td2.innerHTML = data[argumentsKey].teamname;
                node.appendChild(td2);

                let td3 = document.createElement("td");
                let buttom = document.createElement("button");
                buttom.innerHTML = "Accept"
                buttom.onclick = (function () {
                    return function () {
                        acceptTeam(node);
                    }
                })();
                td3.appendChild(buttom);
                node.appendChild(td3);

                let td4 = document.createElement("td");
                let buttomDec = document.createElement("button");
                buttomDec.innerHTML = "Decline"
                buttomDec.onclick = (function () {
                    return function () {
                        declineTeam(node);
                    }
                })();
                td4.appendChild(buttomDec);
                node.appendChild(td4);
                $("#teamTable").append(node);
            }
        }
    }
    request.send();

}

function proveTeamReq() {
    const request = {
        username: commUserName,
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url + "/proveReq", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'Team Policy Added',
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


function addTeamPolicy() {
    const request = {
        username: commUserName,
        leagueID: $('#leagueID2').val(),
        year: $('#year2').val(),
        numgames: $('#number').val()
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();

    xhr.open("POST",
        url + "/addTeamsPolicy", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.status == "200") {
            Swal.fire({
                title: 'Great!',
                text: 'Team Policy Added',
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

function switchdivs(newdiv) {
    var CommissionerPage = document.getElementById("CommissionerPage");
    var setscore = document.getElementById("setNewScorePolicyPage");
    var runplacing = document.getElementById("runPlacingAlgoPage");
    var definebudget = document.getElementById("defineBudgetControlPage");
    var notifications = document.getElementById("notify");
    var proveTeam = document.getElementById("proveTeams");
    var seeLeagues = document.getElementById("seechoosenleague");
    var contact = document.getElementById("contactus");
    var email = document.getElementById("byEmail")


    var mainFrameTwo = document.getElementById(newdiv);


    if(newdiv=='byEmail'){
        mainFrameTwo.style.display = 'block';
        setscore.style.display = 'none';
        CommissionerPage.style.display = 'none';
        definebudget.style.display = 'none';
        notifications.style.display = 'none';
        proveTeam.style.display = 'none';
        seeLeagues.style.display = 'none';
        setscore.style.display = 'none';
        runplacing.style.display = 'none';
        contact.style.display = 'none';

    }
    if(newdiv=='contactus'){
        mainFrameTwo.style.display = 'block';
        setscore.style.display = 'none';
        CommissionerPage.style.display = 'none';
        definebudget.style.display = 'none';
        notifications.style.display = 'none';
        proveTeam.style.display = 'none';
        seeLeagues.style.display = 'none';
        setscore.style.display = 'none';
        runplacing.style.display = 'none';
        email.style.display = 'none';
    }


    if (newdiv == "runPlacingAlgoPage") {
        getleagues('leagueID2');
        mainFrameTwo.style.display = 'block';
        setscore.style.display = 'none';
        CommissionerPage.style.display = 'none';
        definebudget.style.display = 'none';
        notifications.style.display = 'none';
        proveTeam.style.display = 'none';
        seeLeagues.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';


    }

    if (newdiv == "proveTeams") {

        mainFrameTwo.style.display = 'block';
        CommissionerPage.style.display = 'none';
        runplacing.style.display = 'none';
        definebudget.style.display = 'none';
        notifications.style.display = 'none';
        setscore.style.display = 'none';
        seeLeagues.style.display = 'none';
        contact.style.display = 'none';
        runplacing.style.display = 'none';
        email.style.display = 'none';
    }

    if (newdiv == "seechoosenleague") {
        getleagues('leagueID3');
        mainFrameTwo.style.display = 'block';
        CommissionerPage.style.display = 'none';
        runplacing.style.display = 'none';
        definebudget.style.display = 'none';
        notifications.style.display = 'none';
        setscore.style.display = 'none';
        proveTeam.style.display = 'none';
        contact.style.display = 'none';
        runplacing.style.display = 'none';
        email.style.display = 'none';
    }

    if (newdiv == "setNewScorePolicyPage") {
        getleagues('leagueID')
        mainFrameTwo.style.display = 'block';
        CommissionerPage.style.display = 'none';
        runplacing.style.display = 'none';
        definebudget.style.display = 'none';
        notifications.style.display = 'none';
        proveTeam.style.display = 'none';
        seeLeagues.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';


    }

    if (newdiv == "defineBudgetControlPage") {
        mainFrameTwo.style.display = 'block';
        CommissionerPage.style.display = 'none';
        setscore.style.display = 'none';
        runplacing.style.display = 'none';
        notifications.style.display = 'none';
        proveTeam.style.display = 'none';
        seeLeagues.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';

    }

    if (newdiv == "CommissionerPage") {
        mainFrameTwo.style.display = 'block';
        setscore.style.display = 'none';
        runplacing.style.display = 'none';
        definebudget.style.display = 'none';
        notifications.style.display = 'none';
        proveTeam.style.display = 'none';
        seeLeagues.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';
    }
    if (newdiv == "notify") {
        removealertsSign();
        mainFrameTwo.style.display = 'block';
        setscore.style.display = 'none';
        CommissionerPage.style.display = 'none';
        runplacing.style.display = 'none';
        definebudget.style.display = 'none';
        proveTeam.style.display = 'none';
        seeLeagues.style.display = 'none';
        contact.style.display = 'none';
        email.style.display = 'none';
    }

}