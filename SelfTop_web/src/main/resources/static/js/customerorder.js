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

document.addEventListener('click', function(e) {
    if (e.target.classList.contains('confirm_button')) {
        const orderNum = e.target.getAttribute('data-order-num');
        confirmOrder(orderNum);
    }
});

function confirmOrder(orderNum) {
    fetch(`/confirmOrderStatus`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ order_no: orderNum })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('배송이 완료되었습니다.');
            location.reload(); // 페이지를 새로고침하여 변경된 상태를 보여줌
        } else {
            alert('오류 발생: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    });
}