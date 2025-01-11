let quoteInfo = [];
let quoteDetails = [];

function loadquotedetail(quoteNo) {
	// AJAX 요청 보내기
	$.ajax({
		url: '/quotedetail',
		type: 'GET',
		data: { quote_no: quoteNo },
		success: function(response) {
			//클릭 처리
			
			document.querySelectorAll(".quotename").forEach((cell) => {
				cell.style.fontWeight = "normal";
			});
			 const clickedElement = document.querySelector(`[data-quote-no="${quoteNo}"]`);
			     if (clickedElement) {
			       clickedElement.style.fontWeight = "bold";
				   
			     }

			$('.orderproduct').each(function() {
				const productsDiv = $(this).find('.thumbnail');
				const productNameDiv = $(this).find('.productname');
				const priceAmountDiv = $(this).find('.priceamount');

				productsDiv.empty();
				productNameDiv.empty();
				priceAmountDiv.empty();
			});
			const productList = response.products;
			const assemblecheck =response.assemblecheck;
			
			const assemblesection=document.querySelector("#assemblecheck");
			
			const assembleimg =assemblesection.querySelector(".thumbnail img")
			assembleimg.src="https://i.namu.wiki/i/EuI1RmTmtF97hexALOiu-KCVgc4KskRdV_J25r6TL4XMUDKlNuUUXk9BcFoWAJdAEauBwc2vGjBfF0F6ba3sGw.webp";
			assembleimg.style.display='block';
			
			const assemblecontent = assemblesection.querySelector(".productname");
			const ascontent = `<p>조립여부</p>`;
			assemblecontent.innerHTML=ascontent.trim();
			
			const assembleamount =assemblesection.querySelector(".priceamount");
			if(assemblecheck == 'Y'){
			assembleamount.innerHTML =`<p>20000 원</p>
							<div class="detailamountcontrol">
							<button type="button" class="amount-decrease">−</button>
							<input type="number" class="amount-input" value="1" 
							data-price="20000" min="0" max="1" />
							 <button type="button" class="amount-increase">+</button>
							 </div>`;
			}
			
			if(assemblecheck == 'N'){
						assembleamount.innerHTML =`<p>20000 원</p>
										<div class="detailamountcontrol">
										<button type="button" class="amount-decrease">−</button>
										<input type="number" class="amount-input" value="0" 
										data-price="20000" min="0" max="1" />
										 <button type="button" class="amount-increase">+</button>
										 </div>`;
						}
			
							 
			// 서버로부터 받은 데이터에서 카테고리별로 업데이트
			productList.forEach(function(detail) {
				// 카테고리 이름을 기준으로 해당 카테고리의 div를 선택
				const categoryDiv = $('.category p').filter(function() {
					return $(this).text() === detail.category; // 카테고리 이름으로 필터링
				}).closest('.orderproduct');  // 해당 카테고리의 .orderproduct div 찾기

				const imagediv = categoryDiv.find('.thumbnail');
				const namediv = categoryDiv.find('.productname');
				const priceamountdiv = categoryDiv.find('.priceamount');
				

				// 기존 카테고리 섹션 내용 초기화
				imagediv.empty();  // 기존 내용 삭제
				namediv.empty();
				priceamountdiv.empty();
				const amountandprice = detail.price * detail.amount;
				const imagehtml = `<img src="${detail.thumbnail}">`;
				const namehtml = `<p>${detail.product_name}</p>`;
				const priceamounthtml = `<p>${amountandprice.toLocaleString()}  원</p>
				<div class="detailamountcontrol">
				<button type="button" class="amount-decrease">−</button>
				<input type="number" class="amount-input" value="${detail.amount}" 
				data-quote-no="${detail.quote_no}" 
				data-productcode="${detail.product_code}" 
				data-sellerno="${detail.seller_no}" 
				data-price="${detail.price}" min="0" />
				 <button type="button" class="amount-increase">+</button>
				 </div>`;


										                   
				imagediv.append(imagehtml);  // 해당 카테고리 영역에 제품 추가
				namediv.append(namehtml);  // 해당 카테고리 영역에 제품 추가
				priceamountdiv.append(priceamounthtml);
				
			});
		},
		error: function() {
			alert("에러가 발생했습니다.");
		}
	});
}

document.addEventListener("DOMContentLoaded", () => {
	const parentContainer = document.body; // 이벤트 위임을 적용할 상위 컨테이너
	// DOM 요소 참조
	const openModalButton = document.getElementById('showcomparison');
	const closeModalButton = document.getElementById('close-modal');
	const compareButton = document.getElementById('compare-button');
	const tableContainer = document.getElementById('table-container');
	//견적비교 모달 선언부
	const modal = document.getElementById('compare-modal');
	const deleteButton = document.getElementById('deletebutton');
	const changeButton = document.getElementById('changeamount');
	//호환성체크 모달 선언부
	const compatibilitymodal = document.getElementById('modal');
	const openModal = document.getElementById('openModalBtn');
	const closeModalBtn = document.getElementById('closeModalBtn');
	const firstmodalpage = compatibilitymodal.innerHTML;

	function resetModal() {
		compatibilitymodal.innerHTML = firstmodalpage;

		// 모달 초기화 후 필요한 리스너 재등록
		const closeModalBtn = document.getElementById('closeModalBtn'); // 새롭게 생성된 닫기 버튼 재참조
		closeModalBtn.addEventListener('click', () => {
			compatibilitymodal.style.display = 'none';
			resetModal();
		});
	}
	
	//가격 보여주기처리
	const pricerow = document.querySelectorAll(".quoteprice");
	const quotedetailpricerow = document.querySelectorAll(".quoteprice");
	pricerow.forEach(value => {
		const pricevalue = parseInt(value.dataset.price, 10);
		const convertprice = pricevalue.toLocaleString();

		// 금액 업데이트

		value.textContent = convertprice + "원";


	});

	
	//호환성 체크
	openModal.addEventListener('click', () => {
		const compatibilityitem = document.querySelectorAll('#detailview .orderproduct');
		const data = Array.from(compatibilityitem).map((item) => {
			const category = item.querySelector('.category p').textContent.trim();
			if (!["테스트"].includes(category)) {
				const productElement = item.querySelector('.amount-input');
				if (productElement) {
					const productcode = productElement.dataset.productcode;
					return { category: category, productcode: productcode };
				} else {
					return ' ';
				}

			}
			return ' ';
		}).filter((item) => item !== ' ');;
		if (data.length === 0) {
			alert("견적을 선택해주세요");
			return;
		}
		fetch('/compatibility', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data)
		})
			.then((response) => response.json())
			.then((result) => {
				compatibilityviewchange(result);
			})
			.catch((error) => {
				console.error('Error during submission:', error);
			});
		compatibilitymodal.style.display = 'block';
	});
	

	closeModalBtn.addEventListener('click', () => {
	    compatibilitymodal.style.display = 'none';
		resetModal();
		
	});

	


	//견적삭제

	deleteButton.addEventListener('click', () => {

		const selectquote = document.querySelectorAll('input[name="selectquote_no"]:checked');
		if (selectquote.length === 0) {
			alert("견적을 선택해주세요");
			return;
		}
		const quotevalue = Array.from(selectquote).map(checkbox => checkbox.value);
		deletequote(quotevalue);

	});

	//견적수량변경
	changeButton.addEventListener('click', () => {
		const updatedData = [];

		document.querySelectorAll('.amount-input').forEach(function(input) {
			const quoteNo = input.dataset.quoteNo;
			const amount = input.value;
			const productcode = input.dataset.productcode
			updatedData.push({ quoteNo, amount, productcode });
		});
		if (updatedData.length > 0) {
			fetch('/updatequotedetailamount', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(updatedData), // JSON 형식으로 전송
			})
				.then(response => {
					if (!response.ok) {
						
						return Promise.reject('서버 오류 발생');
					}
					return response.text(); // 응답이 JSON이 아닌 텍스트일 경우
				})
				.then(data => {
					// 성공적인 응답 처리
					alert(data); // 서버에서 받은 응답 메시지를 보여줌
					location.reload(true);
				})
				.catch(error => {
					// 오류가 발생한 경우 처리
					console.error('에러:', error);
					alert('에러가 발생했습니다.');
				});
		}


	});

	  
	  
	// 모달 열기
	openModalButton.addEventListener('click', () => {
		const checkElements = document.querySelectorAll(".check");
		checkElements.forEach((element) => {
		    element.remove(); 
		});
		const selectquote = document.querySelectorAll('input[name="selectquote_no"]:checked');
		if(selectquote.length === 0){
			alert("견적을 선택해주세요");
			return;
		}
		const quotenovalue = Array.from(selectquote).map(checkbox => checkbox.value);
		const checkquotename = Array.from(selectquote).map(checkbox => checkbox.closest('tr').querySelector('.quotename').textContent
		);
		const addcheckbox = document.querySelector("#compare-button");
		quotenovalue.forEach((quotenovalue, index) => {
			const quotename = checkquotename[index];
		
		const checkdiv = document.createElement('div');
		checkdiv.classList.add("check");
		
		const checkbox=document.createElement("input");
		checkbox.type="checkbox";
		checkbox.id="quote"+quotenovalue;
		checkbox.name="modalcheckbox";
		checkbox.value=quotenovalue;
		
		const label = document.createElement("label");
		label.setAttribute("for", 'quote' + quotenovalue);
		label.textContent = quotename;
		
		checkdiv.appendChild(checkbox);
		checkdiv.appendChild(label);
		
		addcheckbox.insertAdjacentElement('beforebegin',checkdiv);
		
		});
	    modal.style.display = 'block';
	});

	// 모달 닫기
	closeModalButton.addEventListener('click', () => {
	    modal.style.display = 'none';
	    tableContainer.innerHTML = ''; // 테이블 초기화
	});

	// 모달 외부 클릭 시 닫기
	window.addEventListener('click', (event) => {
	    // 클릭된 대상이 모달의 배경인지 확인
	    if (event.target === modal) {
	        modal.style.display = 'none';
	        tableContainer.innerHTML = ''; // 테이블 초기화
	    }
	});
	
	// 호환성모달 외부 클릭 시 닫기
		window.addEventListener('click', (event) => {
		    if (event.target === compatibilitymodal) {
		        compatibilitymodal.style.display = 'none';
				resetModal();
		    }
		});

	// 비교하기 버튼 클릭 이벤트
	compareButton.addEventListener('click', () => {
	    const checkedQuotes = Array.from(document.querySelectorAll('input[name="modalcheckbox"]:checked'));
		const checkQuotesvalue = Array.from(checkedQuotes).map(checkbox => checkbox.value);
	    if (checkedQuotes.length < 2 || checkedQuotes.length > 3) {
	        alert('최소 2개 이상, 최대 3개까지 선택하세요.');
	        return;
	    }

		fetch("/comparison", {
					method: "POST",
					headers: {
						"Content-Type": "application/json"
					},
					body: JSON.stringify({ values: checkQuotesvalue }),
		}).then((response) => response.json())
			.then((data) => {
				const transdata =transformData(organizeDataByQuoteName(data))
				tableContainer.innerHTML = ''; // 초기화

				   checkedQuotes.forEach(quote => {
				       const index = transdata.findIndex(item => item.cartName === quote.value); // 체크된 인덱스
				       const tableHTML =  createTable(transdata[index]); // 테이블 생성
				       tableContainer.innerHTML += tableHTML; // 추가
			})
			.catch((error) => {
				console.error("에러 발생:", error);
			});

	    });
	});


	parentContainer.addEventListener("click", (event) => {
		const target = event.target;

		// 감소 버튼 클릭 이벤트 처리
		if (target.classList.contains("quantity-decrease")) {
			const quantityInput = target.parentElement.querySelector(".quantity-input");
			let currentValue = parseInt(quantityInput.value, 10);
			if (currentValue > parseInt(quantityInput.min, 10)) {
				const originalPrice = parseInt(quantityInput.getAttribute('data-price'), 10);
				const priceElement = event.target.closest('tr').querySelector('.quoteprice');
				quantityInput.value = currentValue - 1;
				const totalPrice = originalPrice * parseInt(quantityInput.value, 10);
				priceElement.textContent = totalPrice.toLocaleString() + " 원";
			}
		}

		// 증가 버튼 클릭 이벤트 처리
		if (target.classList.contains("quantity-increase")) {
			const quantityInput = target.parentElement.querySelector(".quantity-input");
			let currentValue = parseInt(quantityInput.value, 10);
			const originalPrice = parseInt(quantityInput.getAttribute('data-price'), 10);
			const priceElement =  event.target.closest('tr').querySelector('.quoteprice');
			quantityInput.value = currentValue + 1;
			const totalPrice = originalPrice * parseInt(quantityInput.value, 10);

			priceElement.textContent = totalPrice.toLocaleString() + " 원";
		  
			
		}
		// 감소 버튼 클릭 이벤트 처리
		if (target.classList.contains("amount-decrease")) {
			const quantityInput = target.parentElement.querySelector(".amount-input");
			let currentValue = parseInt(quantityInput.value, 10);
			if (currentValue > parseInt(quantityInput.min, 10)) {
				const priceElement = target.closest('.priceamount').querySelector('p');
				const originalPrice = parseInt(quantityInput.getAttribute('data-price'), 10);
				quantityInput.value = currentValue - 1;				
				totalPrice = originalPrice * parseInt(quantityInput.value, 10);
				
				
				priceElement.textContent = totalPrice.toLocaleString() + " 원";
			}
		}

		// 증가 버튼 클릭 이벤트 처리
		if (target.classList.contains("amount-increase")) {
			const quantityInput = target.parentElement.querySelector(".amount-input");
			let currentValue = parseInt(quantityInput.value, 10);
			const priceElement = target.closest('.priceamount').querySelector('p');
			const originalPrice = parseInt(quantityInput.getAttribute('data-price'), 10);
	
			quantityInput.value = currentValue + 1;
			const totalPrice = originalPrice * parseInt(quantityInput.value, 10); 
			    
			priceElement.textContent = totalPrice.toLocaleString() + " 원";
		}
	});

	parentContainer.addEventListener("input", (event) => {
		if (event.target.classList.contains("quantity-input")) {
			const quantityInput = event.target;
			let value = parseInt(quantityInput.value, 10);
			const originalPrice = parseInt(quantityInput.getAttribute('data-price'), 10);
			const priceElement = event.target.closest('tr').querySelector('.quoteprice');
			
			if (isNaN(value) || value < parseInt(quantityInput.min, 10)) {
				quantityInput.value = quantityInput.min;
				totalprice = originalPrice;
			} else {
				totalPrice = originalPrice * value;
			}
			priceElement.textContent = totalPrice.toLocaleString() + " 원";
		}
		
		if (event.target.classList.contains("amount-input")) {
			const quantityInput = event.target;
			let value = parseInt(quantityInput.value, 10);
			const originalPrice = parseInt(quantityInput.getAttribute('data-price'), 10);
			const priceElement = event.target.closest('.priceamount').querySelector('p');
			if (isNaN(value) || value < parseInt(quantityInput.min, 10)) {
				quantityInput.value = quantityInput.min;
				totalprice=originalPrice;
			}
			else{
				totalPrice = originalPrice * value;
			}
			priceElement.textContent = totalPrice.toLocaleString() + " 원";
		}
	});
});

function createTable(data) {
    const  { cartName, quoteName, components } = data;
    const { CPU, 메인보드, RAM, 그래픽카드, SSD, HDD, 파워, 케이스 , 쿨러 } = components;
	const totalPrice =
	       (CPU ? CPU.price : 0) +
	       (메인보드 ? 메인보드.price : 0) +
	       (RAM ? RAM.price : 0) +
	       (그래픽카드 ? 그래픽카드.price : 0) +
	       (SSD ? SSD.price : 0) +
	       (HDD ? HDD.price : 0) +
	       (파워 ? 파워.price : 0) +
	       (케이스 ? 케이스.price : 0) +
	       (쿨러 ? 쿨러.price : 0);

    return `
        <table>
            <thead>
                <tr>
                    <th colspan="3">${quoteName}</th>
                </tr>
                <tr>
                    <th>부품</th>
                    <th>이름</th>
                    <th>가격 (원)</th>
                </tr>
            </thead>
			<tbody class= "comparisontable">
			 <tr><td>CPU</td><td>${CPU?.name || '-'}</td><td>${CPU?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>메인보드</td><td>${메인보드?.name || '-'}</td><td>${메인보드?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>RAM</td><td>${RAM?.name || '-'}</td><td>${RAM?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>그래픽카드</td><td>${그래픽카드?.name || '-'}</td><td>${그래픽카드?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>SSD</td><td>${SSD?.name || '-'}</td><td>${SSD?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>HDD</td><td>${HDD?.name || '-'}</td><td>${HDD?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>파워</td><td>${파워?.name || '-'}</td><td>${파워?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>케이스</td><td>${케이스?.name || '-'}</td><td>${케이스?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr><td>쿨러</td><td>${쿨러?.name || '-'}</td><td>${쿨러?.price?.toLocaleString() || '-'}원</td></tr>
			 <tr class="total-row">
			 	 <td colspan="2" class="total-cell">총액</td>
			     <td>${totalPrice.toLocaleString()} 원</td>
			     </tr>
			</tbody>
        </table>
    `;
}

function organizeDataByQuoteName(data) {
    const organizedData = {};

    // rawData를 순회
    Object.entries(data).forEach(([quoteName, items]) => {
        // 각 quoteName별로 정리
        organizedData[quoteName] = items.map(item => ({
            category: item.category,
            productName: item.product_name,
            totalPrice: item.price, // 총 가격 계산
			selectquotename :item.quote_name,
        }));
    });

    return organizedData;
}

function transformData(data) {
    const tableData = [];

    // rawData의 각 견적에 대해 처리
    Object.entries(data).forEach(([quotenum, items]) => {
        const components = {
            CPU: null,
            메인보드: null,
            RAM: null,
            그래픽카드: null,
            SSD: null,
            HDD: null,
            파워: null,
            케이스: null,
            쿨러: null
        };

        // 부품들을 순회하며 components에 채움
        items.forEach(item => {
            const category = item.category; // 'Category'를 소문자로 변환
            if (components.hasOwnProperty(category)) {
                components[category] = {
                    name: item.productName,
                    price: item.totalPrice
                };
            }
        });
        // 견적 데이터를 tableData 배열에 추가
        tableData.push({
            cartName: quotenum,  // 견적 이름
			quoteName: items[0].selectquotename,
            components: components // 부품 데이터
        });
    });

    return tableData;
}

function adjustButtonHeights() {
  // div2의 버튼 영역을 기준으로 설정
  const bottomButton = document.querySelector('.bottombutton');
  const detailButton = document.querySelector('.detailbottombutton');
  

  // div2 버튼 높이를 계산
  const detailButtonHeight = detailButton.clientHeight-5;

  // div1 버튼의 높이를 div2 버튼 높이에 맞춤
  bottomButton.style.height = `${detailButtonHeight}px`;
}

window.addEventListener('load', () => {
  adjustButtonHeights();
});

// 윈도우 크기 변경 시 높이를 다시 맞춤
window.addEventListener('resize', adjustButtonHeights);

function deletequote(quotedata){
	$.ajax({
			url: '/deletequote',
			type: 'post',
			contentType: 'application/json',  // 여기에서 contentType을 JSON으로 설정
			data: JSON.stringify({ values: quotedata }),
			success: function(response) {
				alert("견적이 삭제되었습니다.");
				refreshQuotelist()
			},
			error: function() {
				alert("에러가 발생했습니다.");
			}
		});
	}

function refreshQuotelist() {
	$.ajax({
		url: '/loadquotelist',
		type: 'GET',
		success: function(html) {
			const targetDiv = document.querySelector('#cart_view'); 
			targetDiv.innerHTML = html; 
		},
		error: function() {
			alert("div를 새로고침하는 중 오류가 발생했습니다.");
		}
	});
}	

function compatibilityviewchange(data){
	const cpuram = document.querySelector('.CPUtoRAM');
	const cpuboard = document.querySelector('.CPUtoMainboard');
	const ramboard = document.querySelector('.MemorytoMainboard');
	const caseboard = document.querySelector('.CasetoMainboard');
	const casegpu = document.querySelector('.CasetoGPU');
	const casepower = document.querySelector('.CasetoPower');
	const powerconsumption = document.querySelector('.power-consumption');
	const powercurrent = document.querySelector('.power-current');
	const powerrecommended = document.querySelector('.power-recommended');
	const wattvalue=data.wattvalue;
	const powersize = data.powersize;
	//호환성  O X 구분
	const cpuramcircle = document.querySelector('#cpu-ram-compatibility');
	const cpuboardcircle = document.querySelector('#cpu-mainboard-compatibility');
	const ramboardcircle = document.querySelector('#ram-mainboard-compatibility');
	const caseboardcircle = document.querySelector('#mainboard-case-compatibility');
	const casegpucircle = document.querySelector('#graphicCard-case-compatibility');
	const casepowercircle = document.querySelector('#power-case-compatibility');

	
	if (data.cpuramcompatibility) {
		cpuram.parentElement.innerHTML = cpuram.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 가능합니다.");
		cpuramcircle.innerHTML="𐤏";
	} else if (data.cpuramcompatibility  === false) {
		cpuram.parentElement.innerHTML = cpuram.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 불가능합니다.");
		cpuramcircle.innerHTML="✕";
	} else {
		
	}
	
	if (data.cpuboardcompatibility) {
		cpuboard.parentElement.innerHTML = cpuboard.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 가능합니다.");
		cpuboardcircle.innerHTML="𐤏";
	} else if (data.cpuboardcompatibility  === false ) {
		cpuboard.parentElement.innerHTML = cpuboard.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 불가능합니다.");
		cpuboardcircle.innerHTML="✕";
	} else {
		
	}
	
	if (data.boardmemorycompatibility){
		ramboard.parentElement.innerHTML =ramboard.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 가능합니다.");
		ramboardcircle.innerHTML="𐤏";
	}else if(data.boardmemorycompatibility === false){
		ramboard.parentElement.innerHTML = ramboard.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 불가능합니다.");
		ramboardcircle.innerHTML="✕"
	}else{
		
	}
		

	if (data.boardcasecompatibility) {
		caseboard.parentElement.innerHTML =caseboard.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 가능합니다.");
		caseboardcircle.innerHTML="𐤏";
	} else if (data.boardcasecompatibility  === false) {
		caseboard.parentElement.innerHTML =caseboard.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 뷸가능합니다.");
		caseboardcircle.innerHTML="✕";
	} else {
		
	}

	if (data.casegpucompatibility) {
		casegpu.parentElement.innerHTML =casegpu.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 가능합니다.");
		casegpucircle.innerHTML="𐤏";
	} else if (data.casegpucompatibility  === false) {
		casegpu.parentElement.innerHTML =casegpu.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 불가능합니다.");
		casegpucircle.innerHTML="✕";
	} else {
		
	}
	if (data.casepowerompatibility) {
		casepower.parentElement.innerHTML =casepower.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 가능합니다.");
		casepowercircle.innerHTML="𐤏";
	} else if (data.casepowerompatibility === false) {
		casepower.parentElement.innerHTML =casepower.parentElement.innerHTML.replace("비교할 부품이 없습니다.", "호환이 불가능합니다.");
		casepowercircle.innerHTML="✕"
	} else {
		
	}
	const compatibilityElements = document.querySelectorAll(".compatibility");
	   // 각 요소의 내용을 검사
	   compatibilityElements.forEach((element) => {
	       const content = element.textContent.trim();
	       if (content === "✕") {
	           element.style.color = "red";
	       } else if (content === "𐤏") {
	           element.style.color = "blue";
	       }
	   });
	powerconsumption.innerHTML=wattvalue;
	powercurrent.innerHTML = powersize === 0 ? "없습니다." : powersize + "W";
	powerrecommended.innerHTML=wattvalue + 100 + "W";
	
}

// 결제하기 버튼 클릭 이벤트
function goPay() {
	
	// 각 제품 정보를 담는 객체 생성
    document.querySelectorAll(".orderproduct").forEach((product) => {
		const productCode = product.querySelector(".amount-input")?.dataset.productcode;
		//const sellerNo = product.querySelector(".amount-input")?.dataset.sellerNo;

        quoteInfo = {
            thumbnail: product.querySelector(".thumbnail img")?.src,
            category: product.querySelector(".category p")?.innerText,
            name: product.querySelector(".productname p")?.innerText,
			price: parseInt(product.querySelector(".priceamount p")?.innerText.replace(/[^0-9]/g, "")) /
			       parseInt(product.querySelector(".amount-input")?.value), // 총 가격을 수량으로 나눔
            quantity: parseInt(product.querySelector(".amount-input")?.value),
            product_code: productCode,
            seller_no: 0, // 판매자 정보 필요
            assembly: "조립 미신청", // 조립 여부는 기본값으로 설정
        };
		
		// 배열에 추가
		quoteDetails.push(quoteInfo);
	});
	
	// 선택된 제품이 없을 경우 경고 메시지 표시
    if (quoteDetails.length === 0) {
        alert("선택된 제품이 없습니다. 견적함을 확인해주세요.");
        return;
    }

    // localStorage에 저장
    localStorage.setItem('QuoteDetails', JSON.stringify(quoteDetails));

    // 결제 페이지로 이동 (주석 해제 시 활성화)
    window.location.href = '/pay'; // 결제 페이지 URL로 이동

    console.log(quoteDetails); // 가져온 데이터 확인
}
