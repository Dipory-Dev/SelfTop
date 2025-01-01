document.addEventListener("DOMContentLoaded", () => {
    const toggleButton = document.getElementById("toggle-button");
    const sidePanel = document.querySelector(".side-panel");
    const components = document.querySelectorAll('.component');
    const selectedPartDiv = document.querySelector('.selected-part');
    const currentCartButton = document.getElementById("current-cart");
    const savedQuoteSelect = document.querySelector('.saved-quote');
    const contentBox = document.querySelector('.content-box');

    let currentCart = {};

    toggleButton.addEventListener("click", () => {
        sidePanel.classList.toggle("active");
    });

    components.forEach(component => {
        component.addEventListener('click', function() {
            if (!this.classList.contains('active')) {
                components.forEach(comp => comp.classList.remove('active'));
                this.classList.add('active');
                fetchProducts(this.dataset.component);
            }
        });
    });

	//사이드 패널에서 카테고리를 골랐을때 동작
	function fetchProducts(component) {
	    fetch(`/products/${component}`)
	        .then(response => {
	            if (!response.ok) {
	                throw new Error(`Failed to load ${component} products: ${response.statusText}`);
	            }
	            return response.json();
	        })
	        .then(products => {
	            displayProducts(products, component);
	        })
	        .catch(error => {
	            console.error('Error loading products:', error);
	            contentBox.innerHTML = `<p>Error loading products: ${error.message}</p>`;
	        });
	}
	
	// 제품 정보를 콘텐츠 박스에 동적으로 표시하는 함수
	function displayProducts(products, component) {
	    if (!products || products.length === 0) {
	        contentBox.innerHTML = `<p>No products found for ${component.toUpperCase()}.</p>`;
	        return;
	    }

	    // 스타일과 테두리 추가, 스크롤 가능 설정
	    let htmlContent = `<div style="display: flex; flex-direction: column; width: 1300px; max-height: 500px; overflow-y: auto;">`;

		products.forEach(product => {
            htmlContent += `
                <div class="product-box" style="display: flex; align-items: center; justify-content: space-between; border: 1px solid #ccc; padding: 10px; margin-bottom: 5px; background-color: #f9f9f9;">
                    <div style="display: flex; align-items: center;">
                        <img src="${product.thumbnail}" alt="${product.product_name} 이미지" style="width: 100px; height: 100px; margin-right: 10px;">
                        <div style="flex-grow: 1; min-width: 0;">
                            <div class="product-info" style="font-weight: bold; border-bottom: 1px solid black; padding-bottom: 5px; width: 1000px;">${product.product_name}</div>
                            <div style="color: #666; font-size: 0.9em; width: 1000px;">${product.etc}</div>
                        </div>
                    </div>
                    <div class="product-price" style="text-align: right; margin-left: 10px; font-weight: bold; color: #333;">
                        ${product.price ? `${product.price}원` : '품절'}
                        <div><span class="stars">★★★★★</span></div>
                        <div style="display: flex; justify-content: flex-end; gap: 10px; margin-top: 5px;">
                            <button class="btn add-to-cart">담기</button>
                            <button class="btn buy-now">바로구매</button>
                        </div>
                    </div>
                </div>
            `;
        });

	    htmlContent += `</div>`;
	    contentBox.innerHTML = htmlContent;
		
		// 바로구매 버튼 클릭 이벤트
		contentBox.querySelectorAll('.buy-now').forEach(button => {
            button.addEventListener('click', (event) => {
                const productDiv = event.target.closest('.product-box');
                const productName = productDiv.querySelector('.product-info').textContent.trim();
                const productPrice = productDiv.querySelector('.product-price').childNodes[0].textContent.trim().replace('원', '');
                const productThumbnail = productDiv.querySelector('img').src;

                const productInfo = {
					thumbnail: productThumbnail,
					category: component,
                    name: productName,
                    price: productPrice,
                    quantity: 1
                };
                localStorage.setItem('selectedProduct', JSON.stringify(productInfo));

                location.href = '/pay';
            });
        });
	}
	
    window.removeSelectedPart = function(component) {
        const comp = document.querySelector(`.component[data-component="${component}"]`);
        if (comp) {
            comp.classList.remove('active');
        }
        selectedPartDiv.innerHTML = '';
        delete currentCart[component]; // 컴포넌트 제거
    }

    savedQuoteSelect.addEventListener('change', () => {
        const selectedOption = savedQuoteSelect.value;
        // 선택된 견적 로드
        if (selectedOption) {
            loadQuote(savedQuotes[selectedOption]); // 예제에서 savedQuotes를 정의하고 사용해야 함
        }
    });

    currentCartButton.addEventListener('click', () => {
        loadCurrentCart();
    });

    function loadCurrentCart() {
        // 현재 카트의 내용을 로드
        selectedPartDiv.innerHTML = `현재 견적 카트:<br>`;
        Object.keys(currentCart).forEach(component => {
            selectedPartDiv.innerHTML += `<p>${component}</p>`;
        });
    }

    function loadQuote(quote) {
        // 견적 로드
        selectedPartDiv.innerHTML = `저장된 견적:<br>`;
        quote.components.forEach(component => {
            selectedPartDiv.innerHTML += `<p>${component}</p>`;
        });
    }
	
});
