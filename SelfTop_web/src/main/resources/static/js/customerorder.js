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

function sendordernum(element) {
	var orderNum = element.getAttribute('data-order-num');
	var price = element.getAttribute('data-price');
	var orderStatus = element.getAttribute('data-order-status');
	var orderDate = element.getAttribute('data-order-date');

	document.getElementById('order_num').value = orderNum;
	document.getElementById('orderprice').value = price;
	document.getElementById('orderstatus').value = orderStatus;
	document.getElementById('orderdate').value = orderDate;
	document.getElementById('product_code').value = product_code;
	// 폼 제출
	document.getElementById('orderForm').submit();

}