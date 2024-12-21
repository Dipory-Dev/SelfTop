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
	location.href='/'
	
}
function stockinsertpopup(){

	window.open="/seller/stockPopup";
	
	
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
		closeModalBtn.addEventListener('click', () => {
			modal.style.display = 'none';

		   	 });
	  }
});


// 모달 외부 클릭 이벤트
window.addEventListener('click', (event) => {
	if (event.target === modal) {
		modal.style.display = 'none';
	}
});

//닫기버튼 항상이벤트에 등록
document.addEventListener('click', (event) => {
    const modal = document.getElementById('modal');

    // 닫기 버튼 클릭 시 모달 닫기
    if (event.target.id === 'closeModalBtn') {
        modal.style.display = 'none';
    }

    // 모달 외부 클릭 시 모달 닫기
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

function initializeModalEvents() {
    const modal = document.getElementById('modal');
    const openModalBtn = document.getElementById('openModalBtn');
    const closeModalBtn = document.getElementById('closeModalBtn');

    if (openModalBtn) {
        openModalBtn.addEventListener('click', () => {
            modal.style.display = 'block';
        });
    }

    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', () => {
            modal.style.display = 'none';
        });
    }

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

// 카테고리 및 모델 선택 초기화 함수
function initializeCategoryAndModelSelection() {
    const categorySelect = document.getElementById('category');
    const modelNameSelect = document.getElementById('model-name');
    const detailDiv = document.querySelector('.detail');

    if (categorySelect) {
        categorySelect.addEventListener('change', function () {
            let url = '';
            if (this.value === "cpu") {
                url = '/seller/cpuProducts';
            } else if (this.value === "ram") {
                url = '/seller/ramProducts';
            }

            if (url) {
                fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        data.sort((a, b) => a.product_name.localeCompare(b.product_name));
                        updateModelNames(data);
                    })
                    .catch(error => console.error('Error fetching products:', error));
            }
        });
    }

    if (modelNameSelect) {
        modelNameSelect.addEventListener('change', function () {
            const selectedOption = modelNameSelect.options[modelNameSelect.selectedIndex];
            if (selectedOption.dataset.etc) {
                detailDiv.innerHTML = `<p>${selectedOption.dataset.etc}</p>`;
            } else {
                detailDiv.innerHTML = `<p>상세 정보가 없습니다.</p>`;
            }
        });
    }
}

// 모델명 업데이트 함수
function updateModelNames(products) {
    const modelNameSelect = document.getElementById('model-name');
    modelNameSelect.innerHTML = '';

    if (products.length === 0) {
        modelNameSelect.appendChild(new Option("해당 카테고리의 제품이 없습니다.", ""));
    } else {
        products.forEach(product => {
            const option = new Option(product.product_name, product.product_code);
            option.dataset.etc = product.etc;
            modelNameSelect.appendChild(option);
        });
    }
}

// 제품 등록 초기화
function initializeProductRegistration() {
    document.getElementById('registerModalBtn').addEventListener('click', function () {
        const productCode = document.getElementById('model-name').value;
        const quantity = document.getElementById('quantity').value;
        const price = document.getElementById('price').value;

        if (!productCode || productCode === "none") {
            alert('제품 모델을 선택해주세요.');
            return;
        }
        if (quantity <= 0) {
            alert('유효한 수량을 입력해주세요.');
            return;
        }
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
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) return response.json();
                return response.text().then(text => { throw new Error(text || '제품 등록에 실패했습니다.'); });
            })
            .then(() => {
                alert('제품 등록이 성공적으로 완료되었습니다.');
                document.getElementById('modal').style.display = 'none';
                location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message);
            });
    });
}

// 초기화 호출
document.addEventListener('DOMContentLoaded', function () {
    initializeModalEvents();
    initializeCategoryAndModelSelection();
    initializeProductRegistration();
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
