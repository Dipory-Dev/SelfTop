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
				document.getElementsByClassName("menuselect")[0].style.fontWeight = "normal";
				document.getElementById("stockmenu").style.fontWeight = "bold";
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
		// 모달 제어 스크립트
	    const modal = document.getElementById('modal');
		
		fetch("/seller/informorder",{
				method:"POST",
				headers:{
					"Content-Type":"application/json"
				},
				body:JSON.stringify(info)
			})
			.then(response => {
				if (response.ok) {
					return response.json();
					
				} else {
					alert("에러가 발생하였습니다.")
				}
			})
			.then(data => {
				
				console.log();
				console.log(document.getElementById('ordernum'));
				console.log(document.querySelector('#ordernum'));
				document.getElementById('ordernum').textContent = data[0].order_no;
				document.getElementById('name').textContent = data[0].name;
				document.getElementById('address').textContent = data[0].address;
			    document.getElementById('phone').textContent = data[0].phone;
			    document.getElementById('request').textContent = data[0].request;
				document.getElementById('reason').textContent = data[0].reason;
				modal.style.display = 'block';
			})
			.catch(error => {
				console.error("Error:", error);
				alert("오류가 발생했습니다.");
				});
		
		
	 /*  closeModalBtn.addEventListener('click', () => {
				console.log("닫기버튼");
		   	     modal.style.display = 'none';
		   	 });*/
	  }
});
document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('modal');
    const closeModalBtn = document.getElementById('closeModalBtn');

    // 닫기 버튼 클릭 이벤트
    closeModalBtn.addEventListener('click', () => {
        console.log("닫기 버튼 클릭");
        modal.style.display = 'none';
    });

    // 모달 외부 클릭 이벤트
    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});


