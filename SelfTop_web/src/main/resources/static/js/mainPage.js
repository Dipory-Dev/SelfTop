document.addEventListener("DOMContentLoaded", () => {
    const toggleButton = document.getElementById("toggle-button");
    const sidePanel = document.querySelector(".side-panel");
    const components = document.querySelectorAll('.component');
    const selectedPartDiv = document.querySelector('.selected-part');
    const currentCartButton = document.getElementById("current-cart");
    const savedQuoteSelect = document.querySelector('.saved-quote');
    const contentBox = document.querySelector('.content-box');
	
	const topBoxSmall = document.querySelector('.top-box.small');
	const topBoxLarge = document.querySelector('.top-box.large');
	topBoxLarge.addEventListener('change', filterProducts);

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
	
	
	
	/* content-box에 아이템 보여주는 기능 */
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
						<div><span class="stars">★★★★★</span> <!-- 별점 --></div>
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
