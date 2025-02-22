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
		const pricerow = document.querySelectorAll(".productprice");
			pricerow.forEach(value => {
				const pricevalue = parseInt(value.dataset.price, 10);
				const convertprice = pricevalue.toLocaleString();

				// 금액 업데이트

				value.textContent = convertprice + " 원 ";
				});
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
				const modelNameInput = document.getElementById('model-name-input');
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
					changeprice();
					
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

function changeprice(){
	const pricerow = document.querySelectorAll(".productprice");
	pricerow.forEach(value => {
		const pricevalue = parseInt(value.dataset.price, 10);
		const convertprice = pricevalue.toLocaleString();

		// 금액 업데이트

		value.textContent = convertprice + " 원 ";
		});
};


document.addEventListener("DOMContentLoaded", () => {
	changeprice();
	let cancelcheck;


});


	
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
	location.href='/main'
	
}
function stockinsertpopup(){

	window.open="/seller/stockPopup";
	
	
}
function stockinsertpopup(){

	window.open="/seller/stockPopup";
	
	
}

//배송 시작 함수
function startDelivery(orderNo) {
    fetch(`/seller/startDelivery`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ orderNo: orderNo })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('배송을 시작했습니다');
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

document.addEventListener('click', function(event) {
	
	//배송 시작 버튼 기능
	if (event.target.classList.contains('delivery_go')) {
        const orderRow = event.target.closest('tr');
        const orderNo = orderRow.querySelector('td:first-child').textContent;
        const orderStatus = orderRow.querySelector('td:nth-child(8)').textContent;

        if (orderStatus === '배송중') {
            alert('이미 배송을 시작했습니다.');
            return;
        }else if(orderStatus === '배송완료'){
			alert('이미 배송을 완료했습니다.')
			return;
		}else if(orderStatus === '취소요청'){
			alert('취소 요청중 입니다.')
			return;
		}else if(orderStatus === '취소거절'){
			alert('취소가 거절된 상태입니다.')
			return;
		}else if(orderStatus === '취소완료'){
			alert('취소가 완료된 상태입니다.')
			return;
		}

        startDelivery(orderNo);
    }
	
	if (event.target.id === 'registerModalBtn') {
	        const modelNameInput = document.getElementById('model-name-input');
	        const selectedOption = Array.from(document.getElementById('model-names').options).find(option => option.value === modelNameInput.value);
	        const productCode = selectedOption ? selectedOption.getAttribute('data-product-code') : null;

	        if (!productCode) {
	            alert('제품 모델을 선택해주세요.');
	            return;
	        }

	        const quantity = document.getElementById('quantity').value;
	        if (quantity <= 0) {
	            alert('유효한 수량을 입력해주세요.');
	            return;
	        }

	        const price = document.getElementById('price').value;
	        if (price <= 0) {
	            alert('유효한 가격을 입력해주세요.');
	            return;
	        }

	        const data = {
	            product_code: productCode,
	            stock: quantity,
	            price: price,
	            reg_date: new Date().toISOString()
	        };

	        fetch('/seller/registerProductStatus', {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json',
	            },
	            body: JSON.stringify(data)
	        })
	            .then(response => {
	                if (response.ok) {
	                    return response.json();
	                } else {
	                    return response.text().then(text => { throw new Error(text || '제품 등록에 실패했습니다.'); });
	                }
	            })
	            .then(result => {
	                alert('제품 등록이 성공적으로 완료되었습니다.');
	                const modal = document.getElementById('stockmodal');
	                modal.style.display = 'none'; // 성공 시 모달 닫기
	                location.reload(); // 페이지 새로고침
	            })
	            .catch(error => {
	                alert("이미 존재하는 제품입니다.");
	            });
	    }


	if (event.target.classList.contains('info-button')) {
		const info = event.target.getAttribute('data-info');
		// 모달 제어 스크립트
		const modal = document.getElementById('modal');
		const orderRow = event.target.closest('tr');
		const orderStatus = orderRow.querySelector('td:nth-child(8)').textContent;
		cancelcheck=orderStatus;
		fetch("/seller/informorder", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(info)
		})
			.then(response => {
				if (response.ok) {
					return response.json();
				} else {
					alert("에러가 발생하였습니다.")
				}
			})
			.then(data => {

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
		closeModalBtn.addEventListener('click', () => {
			modal.style.display = 'none';

		});
	}
	
	if(event.target.id ==='cancelorder'){
		let cancelconfirm;
		const ordernum =document.getElementById('ordernum').textContent;
		if (cancelcheck === '배송중') {
			alert('취소 요청을 한 주문이 아닙니다.');
			return;
		} else if (cancelcheck === '배송완료') {
			alert('취소 요청을 한 주문이 아닙니다.')
			return;
		} else if (cancelcheck === '취소거절') {
			alert('이미 취소거절한 주문입니다.')
			return;
		} else if (cancelcheck === '취소완료') {
			alert('이미 취소가 완료된 주문입니다.')
			return;
		}
		if(event.target.classList.contains('btn-approve') && event.target.classList.contains('btn') ){
			cancelconfirm =true;
		}
		if(event.target.classList.contains('btn-reject') && event.target.classList.contains('btn') ){
			cancelconfirm=false;
		}
		fetch("/seller/cancelordercheck", {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({cancelordercheck :cancelconfirm,ordernum : ordernum } )
				})
				.then((response) => {
					if (!response.ok) {
						// HTTP 상태 코드가 200~299가 아닐 경우 오류 처리
						return response.text().then((message) => {
							throw new Error(message);
						});
					}
					return response.text();
				})
				.then((result) => {
					if(cancelconfirm){
						alert("취소요청을 승인하였습니다");
					}
					if(!cancelconfirm){
						alert("취소요청을 거절하였습니다.");
					}
					modal.style.display = 'none';
					location.reload(true);
				})
				.catch((error) => {
					console.error('처리 중 오류가 발생하였습니다:', error.message);
					alert('취소 요청에 실패하였습니다: ' + error.message);
				});
		
		
		
	}

	if (event.target.id === 'openModalBtn') {
		const modal = document.getElementById('stockmodal');
		const openModalBtn = document.getElementById('openModalBtn');
		const closeModalBtn = document.getElementById('closeModalBtn');

		modal.style.display = 'block';


		closeModalBtn.addEventListener('click', () => {
			modal.style.display = 'none';
		});

		// 모달 외부 클릭 시 닫기
		window.addEventListener('click', (event) => {
			if (event.target === modal) {
				modal.style.display = 'none';
			}
		});
		const categorySelect = document.getElementById('category');
		const modelNameInput = document.getElementById('model-name-input');

		categorySelect.addEventListener('change', function() {
			fetch('/seller/' + this.value + 'Products')
				.then(response => response.json())
				.then(data => {
					data.sort((a, b) => a.product_name.localeCompare(b.product_name));  // 이름 기준으로 정렬
					updateModelNames(data);
				})
				.catch(error => console.error('Error fetching ' + this.value + ' products:', error));
		});
		modelNameInput.addEventListener('input', function() {
			const selectedOption = Array.from(document.getElementById('model-names').options).find(option => option.value === this.value);
			const etcInfo = selectedOption ? selectedOption.getAttribute('data-etc') : '상세 정보가 없습니다.';
			document.querySelector('.detail').innerHTML = `<p>${etcInfo}</p>`;  // ETC 정보 출력
		})
	}
});

function updateModelNames(products) {
        const modelNameDatalist = document.getElementById('model-names');
        modelNameDatalist.innerHTML = '';  // 초기화
        if (products.length === 0) {
            modelNameDatalist.innerHTML = '<option>해당 카테고리의 제품이 없습니다.</option>';
        } else {
            products.forEach(product => {
                const option = document.createElement('option');
                option.value = product.product_name;  // 사용자에게 보여지는 이름
                option.setAttribute('data-product-code', product.product_code);  // 실제 제품 코드는 데이터 속성에 저장
                option.setAttribute('data-etc', product.etc);  // ETC 정보도 저장
                modelNameDatalist.appendChild(option);
            });
        }
		console.log('Datalist 갱신 완료');
		console.log(Array.from(modelNameDatalist.options).map(option => option.value));
    }



// 모달 외부 클릭 이벤트
window.addEventListener('click', (event) => {
	if (event.target === modal) {
		modal.style.display = 'none';
	}
}); 

//닫기버튼 항상이벤트에 등록


document.querySelectorAll('.btn-update').forEach(button => {
    button.addEventListener('click', function() {
        // 수정하려는 제품의 ID 가져오기
        const productCode = this.getAttribute('data-id');

        // 해당 제품의 가격 및 재고 입력 필드를 찾아 readonly 속성 제거
        const priceInput = document.querySelector(`input[data-id="${productCode}"].stockprice`);
        const stockInput = document.querySelector(`input[data-id="${productCode}"].stockamount`);

        // readonly 속성 제거하여 수정 가능하게 만듦
        priceInput.removeAttribute('readonly');
        stockInput.removeAttribute('readonly');

        // 수정 버튼 숨기고 저장 버튼 표시
        this.style.display = 'none';  // 수정 버튼 숨기기
        const saveButton = document.querySelector(`button[data-id="${productCode}"].btn-save`);
        saveButton.style.display = 'inline-block';  // 저장 버튼 표시
    });
});

document.querySelectorAll('.btn-save').forEach(button => {
    button.addEventListener('click', function() {
        // 저장하려는 제품의 ID 가져오기
        const productCode = this.getAttribute('data-id');

        // 해당 제품의 가격 및 재고 입력 필드에서 값 가져오기
        const priceInput = document.querySelector(`input[data-id="${productCode}"].stockprice`);
        const stockInput = document.querySelector(`input[data-id="${productCode}"].stockamount`);

        const updatedPrice = priceInput.value;
        const updatedStock = stockInput.value;

        // 값 저장 로직 (예: 서버에 AJAX로 요청 보내기)
        console.log(`저장된 값 - 가격: ${updatedPrice}, 재고: ${updatedStock}`);

        // 저장 후 수정 버튼과 저장 버튼 상태 복원
        priceInput.setAttribute('readonly', 'true');
        stockInput.setAttribute('readonly', 'true');

        // 저장 버튼 숨기고 수정 버튼 표시
        this.style.display = 'none';  // 저장 버튼 숨기기
        const updateButton = document.querySelector(`button[data-id="${productCode}"].btn-update`);
        updateButton.style.display = 'inline-block';  // 수정 버튼 표시
    });
});

document.querySelectorAll('.btn-update').forEach(button => {
    button.addEventListener('click', function() {
        // 수정하려는 제품의 ID 가져오기
        const productCode = this.getAttribute('data-id');

        // 해당 제품의 가격 및 재고 입력 필드를 찾아 readonly 속성 제거
        const priceInput = document.querySelector(`input[data-id="${productCode}"].stockprice`);
        const stockInput = document.querySelector(`input[data-id="${productCode}"].stockamount`);

        // readonly 속성 제거하여 수정 가능하게 만듦
        priceInput.removeAttribute('readonly');
        stockInput.removeAttribute('readonly');

        // 수정 버튼 숨기고 저장 버튼 표시
        this.style.display = 'none';  // 수정 버튼 숨기기
        const saveButton = document.querySelector(`button[data-id="${productCode}"].btn-save`);
        saveButton.style.display = 'inline-block';  // 저장 버튼 표시
    });
});

document.querySelectorAll('.btn-save').forEach(button => {
    button.addEventListener('click', function() {
        // 저장하려는 제품의 ID 가져오기
        const productCode = this.getAttribute('data-id');

        // 해당 제품의 가격 및 재고 입력 필드에서 값 가져오기
        const priceInput = document.querySelector(`input[data-id="${productCode}"].stockprice`);
        const stockInput = document.querySelector(`input[data-id="${productCode}"].stockamount`);
        const updatedPrice = priceInput.value;
        const updatedStock = stockInput.value;

        // 값 저장 로직 (예: 서버에 AJAX로 요청 보내기)
        console.log(`저장된 값 - 가격: ${updatedPrice}, 재고: ${updatedStock}`);

        // 저장 후 수정 버튼과 저장 버튼 상태 복원
        priceInput.setAttribute('readonly', 'true');
        stockInput.setAttribute('readonly', 'true');

        // 저장 버튼 숨기고 수정 버튼 표시
        this.style.display = 'none';  // 저장 버튼 숨기기
        const updateButton = document.querySelector(`button[data-id="${productCode}"].btn-update`);
        updateButton.style.display = 'inline-block';  // 수정 버튼 표시
    });
});
