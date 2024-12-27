function Search(){
	var startdate = $("#start-date").val();
	var enddate = $("#end-date").val();
	$.ajax({
		url: "/ordersearch",
		type: "GET",
		data: {
			startdate: startdate,
			enddate: enddate
			}
	}).done(function (fragment) {
		$("#orderview").replaceWith(fragment);
		        }).fail(function(xhr, status, error) {
		        	console.error("오류:", error);
		        	alert("검색 오류");
		        	});
	}
function Reset(){
	 $("#start-date").val('');
	 $("#end-date").val('');

	 Search();
	}

function sendordernum(orderNum) {
	document.getElementById('order_num').value = orderNum;

	// 폼 제출
	document.getElementById('orderForm').submit();
	
}