
function submitTeamManager(){
    event.preventDefault();
    if((document.getElementById("Createnewteam").checked == true)){
        switchdivs("CreatenewteamPage");
    }

}
function switchdivs(newdiv) {
    var TeamManagerPage= document.getElementById("TeamManagerPage");
    var mainFrameTwo = document.getElementById(newdiv);

    if(newdiv== "CreatenewteamPage"){
        mainFrameTwo.style.display = 'block';
        TeamManagerPage.style.display='none';
    }

    if (newdiv == "TeamManagerPage") {
        mainFrameTwo.style.display = 'block';
        TeamManagerPage.style.display='none';
    }

}