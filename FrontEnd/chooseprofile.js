var myprofile = {};
let attributes = JSON.parse(localStorage.getItem("profiles"));
myprofile.username = attributes.username;
myprofile.commissioner = attributes.commissioner;
myprofile.referee = attributes.referee;
myprofile.teamowner = attributes.teamowner;
myprofile.fan = attributes.fan;


function checkCommissionerprofile() {
    if (myprofile.commissioner == true) {
        window.location.href = 'Commissioner.html';
    } else {
        Swal.fire({
            title: 'Error!',
            text: 'You are not a commissioner!',
            icon: 'error',
            confirmButtonText: 'OK'
        })
    }
}

function checkRefereeprofile() {
    if (myprofile.referee == true) {
        window.location.href = 'Referee.html';
    } else {
        Swal.fire({
            title: 'Error!',
            text: 'You are not a Referee!',
            icon: 'error',
            confirmButtonText: 'OK'
        })
    }
}

function checkFanprofile() {
    if (myprofile.fan == true) {
        window.location.href = 'Fan.html';
    }
    Swal.fire({
        title: 'Error!',
        text: 'You dont have a fan page!',
        icon: 'error',
        confirmButtonText: 'OK'
    })
}


function checkTeamOwnerprofile() {
    if (myprofile.teamowner == true) {
        window.location.href = 'TeamOwner.html';
    } else {
        Swal.fire({
            title: 'Error!',
            text: 'You are Not a Team Owner!',
            icon: 'error',
            confirmButtonText: 'OK'
        })
    }
}



