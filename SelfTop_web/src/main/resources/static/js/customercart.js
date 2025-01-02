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
			 console.log("클릭된 quoteNo:", quoteNo);
			console.log(clickedElement);
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

			// 서버로부터 받은 데이터에서 카테고리별로 업데이트
			response.forEach(function(detail) {
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
				const imagehtml = `<img src="${detail.thumbnail}">`;
				const namehtml = `<p>${detail.product_name}</p>`;
				const priceamounthtml = `<p>${detail.price} 원</p>
				<div class="quantity-control">
				<button type="button" class="quantity-decrease">−</button>
				<input type="number" class="quantity-input" value="${detail.amount}" min="0" />
				 <button type="button" class="quantity-increase">+</button>
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
  // 수량 조절 버튼 및 입력 필드를 선택
  const quantityControls = document.querySelectorAll(".quantity-control");
  
  

  quantityControls.forEach((control) => {
    const decreaseButton = control.querySelector(".quantity-decrease");
    const increaseButton = control.querySelector(".quantity-increase");
    const quantityInput = control.querySelector(".quantity-input");

    // 감소 버튼 클릭 이벤트
    decreaseButton.addEventListener("click", () => {
      let currentValue = parseInt(quantityInput.value, 10);
      if (currentValue > parseInt(quantityInput.min, 10)) {
        quantityInput.value = currentValue - 1;
      }
    });

    // 증가 버튼 클릭 이벤트
    increaseButton.addEventListener("click", () => {
      let currentValue = parseInt(quantityInput.value, 10);
      quantityInput.value = currentValue + 1;
    });

    // 입력 필드에서 값 변경 이벤트
    quantityInput.addEventListener("input", () => {
      let value = parseInt(quantityInput.value, 10);
      if (isNaN(value) || value < parseInt(quantityInput.min, 10)) {
        quantityInput.value = quantityInput.min;
      }
    });
  });
});


