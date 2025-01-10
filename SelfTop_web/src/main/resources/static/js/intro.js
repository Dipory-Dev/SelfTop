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
		
	//검색기능 - 데이터를 mainPage로 넘김
	const form = document.querySelector(".search-container form");
	form.addEventListener("submit", function(event) {
    	event.preventDefault(); // 기본 이벤트를 방지
    	const category = document.querySelector(".category-select").value;
    	const search = document.querySelector(".search-box").value;
    	window.location.href = `/mainPage?category=${encodeURIComponent(category)}&search=${encodeURIComponent(search)}`;
	});
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

function movecart(){
	location.href='/cartpage'
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