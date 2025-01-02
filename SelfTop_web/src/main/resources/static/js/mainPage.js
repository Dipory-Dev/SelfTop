//견적 담을 장바구니
let currentCart = {};

document.addEventListener("DOMContentLoaded", () => {
    const toggleButton = document.getElementById("toggle-button");
    const sidePanel = document.querySelector(".side-panel");
    const components = document.querySelectorAll('.component');
    const selectedPartDiv = document.querySelector('.selected-part');
    const currentCartButton = document.getElementById("current-cart");
    const savedQuoteSelect = document.querySelector('.saved-quote');
    const contentBox = document.querySelector('.content-box');
    const radioButtons = document.querySelectorAll('input[name="assembly"]');
    const assemblyPrice = 20000; // 조립 신청 시 추가금액
    let isAssemblyRequested = false; // 현재 조립 신청 상태

    radioButtons.forEach(radio => {
        radio.addEventListener('change', () => {
            if (radio.value === 'requested' && radio.checked) {
                // 조립 신청이 선택되었을 때
                if (!isAssemblyRequested) {
                    isAssemblyRequested = true;
                    addAssemblyPrice();
                }
            } else if (radio.value === 'not_requested' && radio.checked) {
                // 조립 미신청이 선택되었을 때
                if (isAssemblyRequested) {
                    isAssemblyRequested = false;
                    removeAssemblyPrice();
                }
            }
        });
    });

    toggleButton.addEventListener("click", () => {
        sidePanel.classList.toggle("active");
    });

    components.forEach(component => {
        component.addEventListener('click', function() {
            if (!this.classList.contains('active')) {
                components.forEach(comp => comp.classList.remove('active'));
                this.classList.add('active');
                fetchProducts(this.dataset.component);

                //사이드 패널 닫기
                if(toggleButton){
                    toggleButton.click();
                }

            }
        });
    });

    // 사이드 패널에서 카테고리를 골랐을 때 동작
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

        let htmlContent = `<div style="display: flex; flex-direction: column; width: 1300px; max-height: 600px; overflow-y: auto;">`;

        products.forEach(product => {
            htmlContent += `
                <div style="display: flex; align-items: center; justify-content: space-between; border: 1px solid #ccc; padding: 10px; margin-bottom: 5px; background-color: #f9f9f9;">
                    <div style="display: flex; align-items: center;">
                        <img src="${product.thumbnail}" alt="${product.product_name} 이미지" style="width: 100px; height: 100px; margin-right: 10px;">
                        <div style="flex-grow: 1; min-width: 0;">
                            <div style="font-weight: bold; border-bottom: 1px solid black; padding-bottom: 5px; width: 1000px;">${product.product_name}</div>
                            <div style="color: #666; font-size: 0.9em; width: 1000px;">${product.etc}</div>
                        </div>
                    </div>
                    <div style="text-align: right; margin-left: 10px; font-weight: bold; color: #333;">
                            ${product.price ? `${product.price}원` : '품절'}
                        <div><span class="stars">★★★★★</span></div>
                        <div style="display: flex; justify-content: flex-end; gap: 10px; margin-top: 5px;">
                            <button class="btn add-to-cart" data-product-name="${product.product_name}" data-product-price="${product.price}">담기</button>
                            <button class="btn buy-now">바로구매</button>
                        </div>
                    </div>
                </div>
            `;
        });

        htmlContent += `</div>`;
        contentBox.innerHTML = htmlContent;

        // "담기" 버튼 이벤트 추가
        document.querySelectorAll('.add-to-cart').forEach(button => {
            button.addEventListener('click', () => {
                const productName = button.getAttribute('data-product-name'); // 상품명 가져오기
                const productPrice = button.getAttribute('data-product-price'); // 가격 가져오기
                addToCart(productName, productPrice);
            });
        });
    }

    // 담기 버튼 클릭 시 장바구니에 상품 이름 및 수량 는 함수
    function addToCart(productName, productPrice) {
        const activeComponent = document.querySelector('.component.active');
    
        if (activeComponent) {
            const productDetail = activeComponent.querySelector('#product-detail');
            const productPriceDiv = activeComponent.querySelector('#product-price');
            const quantityControls = createQuantityControls(productPrice);
    
            if (productDetail) {
                productDetail.innerHTML = `<p>${productName}</p>`; // 제품 이름 출력
            }
            if (productPriceDiv) {
                productPriceDiv.innerHTML = ''; // 기존 가격 제거
                productPriceDiv.appendChild(quantityControls); // 수량 조절 추가
            }
    
            // 장바구니에 추가
            const componentName = activeComponent.dataset.component;
            currentCart[componentName] = {
                name: productName,
                price: parseInt(productPrice),
                quantity: 1, // 기본 수량 :1
            };
    
            updateTotalPrice(); // 총합 업데이트
        }
    }

    // 총합 업데이트 함수
    function updateTotalPrice() {
        let total = 0;

        Object.values(currentCart).forEach(item => {
            total += item.price * item.quantity;
        });

        const totalPriceElement = document.querySelector('.total-price');
        if (totalPriceElement) {
            totalPriceElement.textContent = `${total.toLocaleString()}원`;
        }
    }

    function createQuantityControls(productPrice) {
        const container = document.createElement('div');
        container.classList.add('product-quantity'); // 수량 컨트롤을 포함하는 컨테이너
        
        const decreaseButton = document.createElement('button');
        decreaseButton.textContent = '-';
        decreaseButton.classList.add('quantity-decrease');
        
        const quantityInput = document.createElement('input');
        quantityInput.type = 'number';
        quantityInput.value = 1;
        quantityInput.min = 1;
        quantityInput.classList.add('quantity-input');
        
        const increaseButton = document.createElement('button');
        increaseButton.textContent = '+';
        increaseButton.classList.add('quantity-increase');
        
        const priceDisplay = document.createElement('div');
        priceDisplay.textContent = `${productPrice}원`;
        priceDisplay.classList.add('price-display');
        
        container.appendChild(decreaseButton);
        container.appendChild(quantityInput);
        container.appendChild(increaseButton);
        
        // 수량 조절, 가격 표시를 함께 묶는 컨테이너
        const controlsWrapper = document.createElement('div');
        controlsWrapper.classList.add('quantity-controls-wrapper');
        controlsWrapper.appendChild(container);
        controlsWrapper.appendChild(priceDisplay);
    
        // 이벤트 리스너 추가
        decreaseButton.addEventListener('click', (event) => {
            event.stopPropagation(); // 이벤트 전파 방지
            let quantity = parseInt(quantityInput.value);
            if (quantity > 1) {
                quantity -= 1;
                quantityInput.value = quantity;
                updatePriceDisplay(quantity, productPrice, priceDisplay);
                updateCartQuantity(quantity);
                updateTotalPrice(); // 총합 즉각 업데이트
            }
        });
        
        increaseButton.addEventListener('click', (event) => {
            event.stopPropagation(); // 이벤트 전파 방지
            let quantity = parseInt(quantityInput.value);
            quantity += 1;
            quantityInput.value = quantity;
            updatePriceDisplay(quantity, productPrice, priceDisplay);
            updateCartQuantity(quantity);
            updateTotalPrice(); // 총합 즉각 업데이트
        });
        
        quantityInput.addEventListener('input', (event) => {
            event.stopPropagation(); // 이벤트 전파 방지
            let quantity = parseInt(quantityInput.value);
            if (isNaN(quantity) || quantity < 1) {
                quantity = 1;
            }
            quantityInput.value = quantity;
            updatePriceDisplay(quantity, productPrice, priceDisplay);
            updateCartQuantity(quantity);
            updateTotalPrice(); // 총합 즉각 업데이트
        });
        
        return controlsWrapper;
    }

    // 장바구니에 수량 업데이트
    function updateCartQuantity(quantity) {
        const activeComponent = document.querySelector('.component.active');
        if (activeComponent) {
            const componentName = activeComponent.dataset.component;
            if (currentCart[componentName]) {
                currentCart[componentName].quantity = quantity;
            }
        }
    }

    // 가격 업데이트 함수
    function updatePriceDisplay(quantity, unitPrice, priceDisplay) {
        const totalPrice = quantity * parseInt(unitPrice);
        priceDisplay.textContent = `${totalPrice.toLocaleString()}원`;
    }

    window.removeSelectedPart = function(component) {
        const comp = document.querySelector(`.component[data-component="${component}"]`);
        if (comp) {
            comp.classList.remove('active');
        }
        selectedPartDiv.innerHTML = '';
        delete currentCart[component]; // 컴포넌트 제거
    };

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
        console.log(currentCart);
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

     // 조립 신청 금액 추가 함수
     function addAssemblyPrice() {
        const totalPriceElement = document.querySelector('.total-price');
        let currentTotal = parseInt(totalPriceElement.textContent.replace(/[^0-9]/g, '')) || 0;
        currentTotal += assemblyPrice;
        totalPriceElement.textContent = `${currentTotal.toLocaleString()}원`;
    }

    // 조립 신청 금액 제거 함수
    function removeAssemblyPrice() {
        const totalPriceElement = document.querySelector('.total-price');
        let currentTotal = parseInt(totalPriceElement.textContent.replace(/[^0-9]/g, '')) || 0;
        currentTotal -= assemblyPrice;
        totalPriceElement.textContent = `${currentTotal.toLocaleString()}원`;
    }

    //호환성체크 모달 열기
    openModal.addEventListener('click', () => {
        modal.style.display = 'block';
    
       // 모달 열리면 사이드 패널 닫히는 동작
        if (toggleButton) {
            toggleButton.click();
        }
    
    });

});

// x 버튼 클릭 시 삭제, 총 가격 반영 함수
function deleteCompo(event) {
    const component = event.target.closest('.component');
    if (!component) return;

    // 제품 상세 정보삭제
    const productDetail = component.querySelector('#product-detail');
    if (productDetail) {
        productDetail.innerHTML = '';
    }

    // 수량버튼, 가격삭제
    const productPriceDiv = component.querySelector('#product-price');
    if (productPriceDiv) {
        productPriceDiv.innerHTML = '';
    }

    // 'active' 클래스 제거
    component.classList.remove('active');

    // currentCart에서 해당 컴포넌트 제거
    const componentName = component.dataset.component;
    if (currentCart[componentName]) {

        // 총합에서 해당 컴포넌트 가격 삭감
        const item = currentCart[componentName];
        const itemTotalPrice = item.price * item.quantity;

        // 총합 업데이트
        const totalPriceElement = document.querySelector('.total-price');
        let currentTotal = parseInt(totalPriceElement.textContent.replace(/[^0-9]/g, '')) || 0;
        currentTotal -= itemTotalPrice;

        // 총합 표시 업데이트
        totalPriceElement.textContent = `${currentTotal.toLocaleString()}원`;

        // 장바구니에서 컴포넌트 제거
        delete currentCart[componentName];
    }
}

//초기화 버튼 클릭 시 기능하는 함수
function resetCart(){

    //모든 컴포넌트 초기화
    const components = document.querySelectorAll('.component');
    components.forEach(component =>{
        
        //제품 이름 제거
        const productDetail = component.querySelector('#product-detail');
        if(productDetail){
            productDetail.innerHTML = '';
        }

        //수량버튼, 가격 제거
        const productPriceDiv = component.querySelector('#product-price');
        if(productPriceDiv){
            productPriceDiv.innerHTML='';
        }

        // 'active' 클래스 제거
        component.classList.remove('active');
    })

    // 장바구니 데이터 초기화
    currentCart = {};

    // 총합 초기화
    const totalPriceElement = document.querySelector('.total-price');
    if (totalPriceElement) {
        totalPriceElement.textContent = '0원';
    }

    // 선택된 부품 정보 초기화
    const selectedPartDiv = document.querySelector('.selected-part');
    if (selectedPartDiv) {
        selectedPartDiv.innerHTML = '현재 견적 카트:<br>';
    }
    
}

function goPayPage(){
    window.location.href = "/pay";
}

/*-----호환성체크 모달 코드----- */

// 모달 제어 스크립트
const modal = document.getElementById('modal');
const openModal = document.getElementById('openModalBtn');
const closeModalBtn = document.getElementById('closeModalBtn');

closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

/*-----------------------------------*/