

var urlip = "http://132.72.65.125:8080/";
function submitRegistration() {
    $('#first_form').validate({
        rules: {
            userName: {
                pattern: '^[a-zA-Z]*$',
                required: true
            },
            password: {
                pattern: '^(?=.*\\d)(?=.*[a-zA-Z]).{6,200}$',
                required: true,

            },
            name: {
                pattern: '^[a-zA-Z]*$',
                required: true

            },
            name: {
                pattern: '^[a-zA-Z]*$',
                required: true

            },
            id:{
                required: true
            }
        },

    });

    event.preventDefault();
    const form = $("#first_form");
    if (form.valid()) {
        var user_name = $('#userName').val();
        var password = $('#password').val();
        window.alert("registered ")
        localStorage.setItem(user_name, password);
        switchdivs("WelcomePage");
    }

}

function logingGame() {
    const request = {
        username: $('#user').val(),
        password: $('#pass').val()
    };
    let json = JSON.stringify(request);
    let xhr = new XMLHttpRequest();
    var profiles ={};
    xhr.open("POST",
        urlip+"footballapp/guest/login", true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.onload = function () {
        if (xhr.readyState == 4 && xhr.status == "200") {
            let jsonans = JSON.parse(this.responseText);
            profiles.username = jsonans.username;
            if(jsonans.Commissioner == "true"){
                profiles.commissioner = true;
            }
            if(jsonans.TeamOwner=="true"){
                profiles.teamowner=true;
            }
            if(jsonans.Referee=="true"){
                profiles.referee=true;
            }
             if(jsonans.Fan=="true"){
             profiles.fan=true;
             }

            localStorage.setItem("profiles",JSON.stringify(profiles));
            window.location.href = 'chooseprofile.html';
        }
        else{
            Swal.fire({
                title: 'Error!',
                text: 'Invalid username or password!',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        }
    }
    xhr.send(json);

}


function switchdivs(newdiv) {
    var mainFrameTwo = document.getElementById(newdiv);
    var oldframe1 = document.getElementById('WelcomePage');
    var oldframe2 = document.getElementById('log-in');
    var oldframe5 = document.getElementById('registerpage');

    if (newdiv == "WelcomePage") {
        mainFrameTwo.style.display = 'block';
        oldframe5.style.display = 'none';
        oldframe2.style.display = 'none';

    }
    if (newdiv == "log-in") {
        mainFrameTwo.style.display = 'block';
        oldframe1.style.display = 'none';
        oldframe5.style.display = 'none';


    }
    if (newdiv == "registerpage") {
        mainFrameTwo.style.display = 'block';
        oldframe1.style.display = 'none';
        oldframe2.style.display = 'none';

    }

}

