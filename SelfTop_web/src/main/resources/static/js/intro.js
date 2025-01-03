document.addEventListener("DOMContentLoaded", function() {
	fetch('/member_no') // 서버에서 세션 값 체크
	       .then(response => response.text())
	       .then(memberNo => {
	           if (memberNo) {
	               sessionStorage.setItem("loggedIn", "true"); 
	           } else {
	               sessionStorage.setItem("loggedIn", "false"); 
	           }
	           divselect(); 
	       })
	       .catch(error => console.error('Error fetching memberNo:', error));
		divselect();
});

function movemain(){
	location.href='/main'
}

function movelogin(){
	location.href='/loginform'
}

function movemypage(){
	location.href='/mypage'
	
}
function logout() {
    sessionStorage.removeItem("loggedIn");
    location.href = '/logout';
}
function divselect(){
	const isLoggedIn = sessionStorage.getItem("loggedIn") === "true";

	    if (isLoggedIn) {
	        document.getElementById("not_member").style.display = "none";
	        document.getElementById("member").style.display = "flex";
	    } else{
	        document.getElementById("member").style.display = "none";
	        document.getElementById("not_member").style.display = "flex";
	    }
	
}