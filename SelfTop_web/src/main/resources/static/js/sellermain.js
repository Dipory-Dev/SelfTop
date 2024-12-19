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
function stockselect() {
	const selectcheckbox = document.querySelectorAll(".stockitemcheck:checked"); 
	const stock = []; 

	selectcheckbox.forEach((checkbox) => {
		const productcode = checkbox.value; 
		const priceInput = document.querySelector(`.stockprice[data-id='${productcode}']`);
		const amountInput = document.querySelector(`.stockamount[data-id='${productcode}']`); 
		stock.push({
			productcode: productcode,
			price: priceInput.value,
			amount: amountInput.value
			
		});
		
	});
	return stock; 
}


function updatestock(data){
	console.log(data);
	fetch("/seller/updatestock",{
		method:"POST",
		headers:{
			"Content-Type":"application/json"
		},
		body:JSON.stringify(data)
	})
	.then(response => {
		if (response.ok) {
			alert("제품 정보가 성공적으로 수정되었습니다.");
			location.reload(); 
		} else {
			alert("제품 수정에 실패했습니다.");
		}
	})
	.catch(error => {
		console.error("Error:", error);
		alert("오류가 발생했습니다.");
		});
}
	
function savestockchange() {
	const stockdata = stockselect(); 
	 if (stockdata.length === 0) {
		alert("수정할 항목을 선택하세요."); 
		return;
	}
	updatestock(stockdata); 
}	

function logout() {
    sessionStorage.removeItem("loggedIn");
    location.href = '/logout';
}

function movemain(){
	location.href='/intropage.html'
	
}
function stockinsertpopup(){

	window.open="/seller/stockPopup";
	
	
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

