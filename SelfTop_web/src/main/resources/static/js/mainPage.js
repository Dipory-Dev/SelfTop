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
    const sortButtons = document.querySelectorAll('.sortBtn');
    let selectedSort = 'byname';

	const topBoxSmall = document.querySelector('.top-box.small');
	const topBoxLarge = document.querySelector('.top-box.large');
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


    //사이드 패널에서 카테고리를 골랐을때 동작
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

                fetchProducts(this.dataset.component, selectedSort);
                if (this.dataset.component === "CPU") {
                    displayCpuDetails();
                    fetchCpuAttributes();
                }else if(this.dataset.component === "RAM"){
                    displayRamDetails();
                    fetchRamAttributes();
                }else if(this.dataset.component === "SSD"){
                    displaySsdDetails();
                    fetchSsdAttributes();
                }else if(this.dataset.component === "파워"){
                    displayPowerDetails();
                    fetchPowerAttributes()
                }else if(this.dataset.component === "쿨러"){
                    displayCoolerDetails();
                    fetchCoolerAttributes()
                }else if(this.dataset.component === "메인보드"){
                    displayMainBoardDetails();
                    fetchMainBoardAttributes();
                }else if(this.dataset.component === "그래픽카드"){
                    displayGpuDetails();
                    fetchGpuAttributes();
                }else if(this.dataset.component === "HDD"){
                    displayHddDetails();
                    fetchHddAttributes()
                }else if(this.dataset.component === "케이스"){
                    displayCaseDetails();
                    fetchCaseAttributes()
                }
            }
        });
    });

    /* 필터링 - 부품 별로 하나씩 */
    //CPU의 필터를 html에 보여주는 기능
    function displayCpuDetails() {
        let detailsHtml = `
			<div><strong>Socket</strong></div>
		    <div><strong>DDR</strong></div>
		    <div><strong>Generation</strong></div>
		    <div><strong>Spec</strong></div>
		    <div><strong>Inner VGA</strong></div>
		    <div><strong>Package Type</strong></div>
		    <div><strong>Cooler Status</strong></div>
		    <div><strong>Core</strong></div>
		    <div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchCpuAttributes() {
        // Fetch API를 사용하여 서버로부터 CPU 속성 데이터를 가져옴
        fetch(`/api/cpu/attributes`)
            .then(response => response.json())
            .then(data => {
                displayCpuAttributes(data);
            })
            .catch(error => console.error('Error fetching CPU attributes:', error));
    }
    function displayCpuAttributes(data) {
        // Display 순서를 정의
        const order = ["Socket", "DDR", "Generation", "Spec", "Inner VGA", "Package Type", "Cooler Status", "Core", "Company"];
        let attributesHtml = '';
        // 정의된 순서대로 데이터를 표시
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    //RAM의 필터를 html에 보여주는 기능
    function displayRamDetails() {
        let detailsHtml = `
			<div><strong>DDR</strong></div>
		    <div><strong>Storage</strong></div>
		    <div><strong>Device</strong></div>
		    <div><strong>Heatsync</strong></div>
		    <div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchRamAttributes() {
        fetch(`/api/ram/attributes`)
            .then(response => response.json())
            .then(data => {
                displayRamAttributes(data);
            })
            .catch(error => console.error('Error fetching RAM attributes:', error));
    }
    function displayRamAttributes(data) {
        const order = ["DDR", "Storage", "Device", "HeatSync", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    //SSD의 필터를 html에 보여주는 기능
    function displaySsdDetails() {
        let detailsHtml = `
			<div><strong>Storage</strong></div>
		    <div><strong>Type</strong></div>
		    <div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchSsdAttributes() {
        fetch(`/api/ssd/attributes`)
            .then(response => response.json())
            .then(data => {
                displaySsdAttributes(data);
            })
            .catch(error => console.error('Error fetching SSD attributes:', error));
    }
    function displaySsdAttributes(data) {
        const order = ["Storage", "Type", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    //파워의 필터를 html에 보여주는 기능
    function displayPowerDetails() {
        let detailsHtml = `
			<div><strong>Supply</strong></div>
		    <div><strong>Plus80</strong></div>
		    <div><strong>Formfactor</strong></div>
			<div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchPowerAttributes() {
        fetch(`/api/power/attributes`)
            .then(response => response.json())
            .then(data => {
                displayPowerAttributes(data);
            })
            .catch(error => console.error('Error fetching POWER attributes:', error));
    }
    function displayPowerAttributes(data) {
        const order = ["Supply", "Plus80", "Formfactor", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    //쿨러의 필터를 html에 보여주는 기능
    function displayCoolerDetails() {
        let detailsHtml = `
			<div><strong>Cooler Type</strong></div>
		    <div><strong>Socket</strong></div>
			<div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchCoolerAttributes() {
        fetch(`/api/cooler/attributes`)
            .then(response => response.json())
            .then(data => {
                displayCoolerAttributes(data);
            })
            .catch(error => console.error('Error fetching Cooler attributes:', error));
    }
    function displayCoolerAttributes(data) {
        const order = ["Cooler Type", "Socket", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    //메인보드의 필터를 html에 보여주는 기능
    function displayMainBoardDetails() {
        let detailsHtml = `
			<div><strong>Socket</strong></div>
		    <div><strong>Formfactor</strong></div>
			<div><strong>Memory Slot</strong></div>
			<div><strong>DDR</strong></div>
			<div><strong>Max Storage</strong></div>
			<div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchMainBoardAttributes() {
        fetch(`/api/mainboard/attributes`)
            .then(response => response.json())
            .then(data => {
                displayMainBoardAttributes(data);
            })
            .catch(error => console.error('Error fetching MAINBOARD attributes:', error));
    }
    function displayMainBoardAttributes(data) {
        const order = ["Socket", "Formfactor", "Memory Slot", "DDR", "Max Storage", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    //그래픽카드의 필터를 html에 보여주는 기능
    function displayGpuDetails() {
        let detailsHtml = `
			<div><strong>Series</strong></div>
		    <div><strong>Storage</strong></div>
			<div><strong>Length</strong></div>
			<div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchGpuAttributes() {
        fetch(`/api/gpu/attributes`)
            .then(response => response.json())
            .then(data => {
                displayGpuAttributes(data);
            })
            .catch(error => console.error('Error fetching GPU attributes:', error));
    }
    function displayGpuAttributes(data) {
        const order = ["Series", "Storage", "Length", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    // 제품 정보를 콘텐츠 박스에 동적으로 표시하는 함수
    function displayProducts(products, component) {
        if (!products || products.length === 0) {
            contentBox.innerHTML = `<p>No products found for ${component.toUpperCase()}.</p>`;
            return;
        }
    }
    //HDD의 필터를 html에 보여주는 기능
    function displayHddDetails() {
        let detailsHtml = `
			<div><strong>Device</strong></div>
		    <div><strong>Storage</strong></div>
			<div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchHddAttributes() {
        fetch(`/api/hdd/attributes`)
            .then(response => response.json())
            .then(data => {
                displayHddAttributes(data);
            })
            .catch(error => console.error('Error fetching HDD attributes:', error));
    }
    function displayHddAttributes(data) {
        const order = ["Device", "Storage", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    //케이스의 필터를 html에 보여주는 기능
    function displayCaseDetails() {
        let detailsHtml = `
			<div><strong>Power Status</strong></div>
		    <div><strong>Formfactor</strong></div>
			<div><strong>Tower Size</strong></div>
			<div><strong>VGA Length</strong></div>
			<div><strong>Power Size</strong></div>
			<div><strong>Company</strong></div>
		`;
        topBoxSmall.innerHTML = detailsHtml;
    }
    function fetchCaseAttributes() {
        fetch(`/api/case/attributes`)
            .then(response => response.json())
            .then(data => {
                displayCaseAttributes(data);
            })
            .catch(error => console.error('Error fetching CASE attributes:', error));
    }
    function displayCaseAttributes(data) {
        const order = ["Power Status", "Formfactor", "Tower Size", "VGA Length", "Power Size", "Company"];
        let attributesHtml = '';
        order.forEach(key => {
            if(data[key]) {
                const values = data[key];
                attributesHtml += `<div> ${values.map(value =>
                    `<label><input type="checkbox" name="${key}" value="${value}">${value}</label>`
                ).join(' ')}</div>`;
            }
        });
        topBoxLarge.innerHTML = attributesHtml;
    }

    /* filter에 따라 content-box에 보여주는 아이템을 변화하는 기능 */


    // 정렬 목록 클릭 이벤트
    sortButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            sortButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
            selectedSort = this.dataset.sort;

            const activeComponent = document.querySelector('.component.active');
            if (activeComponent) {
                fetchProducts(activeComponent.dataset.component, selectedSort);
            }
        });
    });

	// 제품 목록 출력
	function fetchProducts(component, sort) {
	    fetch(`/products/${component}?sort=${sort}`)
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
