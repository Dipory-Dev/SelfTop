function Search(){
	var startdate = $("#start-date").val();
	var enddate = $("#end-date").val();
	var keyword = $("#keyword").val();
	$.ajax({
		url: "/seller/datesearch",
		type: "GET",
		data: {
			startdate: startdate,
			enddate: enddate,
			keyword:keyword
			}
	}).done(function (fragment) {
		$("#product_view").replaceWith(fragment);
		        }).fail(function(xhr, status, error) {
		        	console.error("오류:", error);
		        	alert("검색 오류");
		        	});
	}
function Reset(){
	 $("#start-date").val('');
	 $("#end-date").val('');
	 $("#keyword").val('');

	 Search();
	}
	
function movestock(){
	 $.ajax({
	        url: "/seller/stockmenu", 
	        type: "GET",
	        success: function(response) {
	            $("#tablesection").html(response);
	        },
	        error: function(xhr, status, error) {
	 
	            alert("오류 발생: " + error);
	        }
	    });
	}

function moveorder(){
		 $.ajax({
		        url: "/seller/ordermenu", 
		        type: "GET",
		        success: function(response) {
		            $("#tablesection").html(response);
		        },
		        error: function(xhr, status, error) {
		 
		            alert("오류 발생: " + error);
		        }
		    });
		}	
		
function logout() {
    sessionStorage.removeItem("loggedIn");
    location.href = '/login/logout';
}

function movemain(){
	location.href='/intropage.html'
	
}

document.addEventListener('click', function (event) {
	  if (event.target.classList.contains('info-button')) {
	    const info = event.target.getAttribute('data-info');
	    CustomerPopup(info);
	  }
	});


	function CustomerPopup(info) {
	  alert("구매자 정보: " + info);
	}

