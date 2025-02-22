//견적 담을 장바구니
let currentCart = {};
//필터링 된 상태에서 정렬기능 구현을 위해 현재 정렬된 정보를 담기
let currentFilters = {};
// 검색어를 저장할 변수
let currentSearchTerm = '';

let cartDetails = [];

document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById('search-input');
    const searchButton = document.querySelector('.search-button');
    const toggleButton = document.getElementById("toggle-button");
    const sidePanel = document.querySelector(".side-panel");
    const components = document.querySelectorAll('.component');
    const selectedPartDiv = document.querySelector('.selected-part');
    const currentCartButton = document.getElementById("current-cart");
    const savedQuoteSelect = document.querySelector('.saved-quote');
    const saveQuoteButton = document.getElementById("save-quote");
    const quoteNameInput = document.getElementById("quote-name");
    const contentBox = document.querySelector('.content-box');
    const sortButtons = document.querySelectorAll('.sortBtn');
    let selectedSort = 'bypopular';

    const categoryCountElement = document.getElementById('category-count');
    const topBoxSmall = document.querySelector('.top-box.small');
    const topBoxLarge = document.querySelector('.top-box.large');
    const radioButtons = document.querySelectorAll('input[name="assembly"]');
    const assemblyPrice = 20000; // 조립 신청 시 추가금액
    let isAssemblyRequested = false; // 현재 조립 신청 상태


     // mainPage처음 들어왔을때 CPU가 자동으로 선택되도록 설정
     /*const cpuComponent = document.querySelector('.component[data-component="CPU"]');
     if (cpuComponent) {
         cpuComponent.classList.add('active'); // 'active' 클래스를 추가하여 선택 표시
         fetchProducts('CPU'); // CPU 제품 목록을 가져오는 함수 호출
         displayCpuDetails(); // CPU 세부 정보 표시
         fetchCpuAttributes(); // CPU 속성 정보를 가져오는 함수 호출
     }*/

     //intro에서 검색한 데이터를 처리하고 보여주는 함수
     const urlParams = new URLSearchParams(window.location.search);
     const category = urlParams.get('category');
     const search = urlParams.get('search');

     // 검색어와 카테고리가 URL에 존재하는 경우
     if (category && search) {
         searchInput.value = search; // 검색어 표시
         const activeComponent = document.querySelector(`.component[data-component="${category}"]`);

        // 사이드 패널 토글 버튼 동작 수정
        document.body.addEventListener('click', function (event) {
            const target = event.target;

        if (target.matches('button, a, input[type="submit"], input[type="button"], input[type="checkbox"], input[type="radio"]')) {
            // 페이지 상태 초기화 함수
            window.history.replaceState(null, null, '/main'); // URL 리셋
            const searchInput = document.getElementById('search-input');
            if (searchInput) {
                searchInput.value = ''; // 검색 입력 상자 초기화
            }
        }

    });
         // 해당 카테고리를 활성화하고 제품 목록을 불러옴
         if (activeComponent) {
             activateComponent(activeComponent);
             fetchProducts(category, "bypopular", search);
         }

     } else {
         // 기본적으로 CPU 컴포넌트 활성화
         activateDefaultComponent();
     }

    

     // 기본 컴포넌트 활성화 함수
     function activateDefaultComponent() {
         const cpuComponent = document.querySelector('.component[data-component="CPU"]');
         if (cpuComponent) {
             activateComponent(cpuComponent);
             fetchProducts('CPU', 'bypopular', '');
             displayCpuDetails();
             fetchCpuAttributes();
         }
     }

    // 컴포넌트 활성화 함수
    function activateComponent(component) {
      components.forEach(comp => comp.classList.remove('active'));
      component.classList.add('active');
      displayComponentDetails(component.dataset.component);
  }

    // 컴포넌트에 따른 상세 정보 표시 함수
    function displayComponentDetails(componentName) {
        switch (componentName) {
            case 'CPU':
                displayCpuDetails();
                fetchCpuAttributes();
                break;
            case 'RAM':
                displayRamDetails();
                fetchRamAttributes();
                break;
            case '그래픽카드':
                displayGpuDetails();
                fetchGpuAttributes();
                break;
            case 'SSD':
                displaySsdDetails();
                fetchSsdAttributes();
                break;
            case '메인보드':
                displayMainBoardDetails();
                fetchMainBoardAttributes();
                break;
            case '쿨러':
                displayCoolerDetails();
                fetchCoolerAttributes();
                break;
            case '파워':
                displayPowerDetails();
                fetchPowerAttributes();
                break;
            case 'HDD':
                displayHddDetails();
                fetchHddAttributes();
                break;
            case '케이스':
                displayCaseDetails();
                fetchCaseAttributes();
                break;
            default:
                console.log('No details available for this component');
                break;
        }
    }

     /* 검색 기능 */
     searchButton.addEventListener('click', () => {
         const searchTerm = searchInput.value.trim().toLowerCase();
         currentSearchTerm = searchTerm;  // 검색어 저장
         const activeComponent = document.querySelector('.component.active');
         if (activeComponent) {
             const componentCategory = activeComponent.dataset.component;
             if (Object.keys(currentFilters).length > 0 || searchTerm) {
                 fetchFilteredProducts(componentCategory, currentFilters, selectedSort, searchTerm);
             } else {
                 fetchProducts(componentCategory, selectedSort, searchTerm);
             }
         }
     });

     // placeholder 업데이트 함수
     function updatePlaceholder(componentName) {
         searchInput.placeholder = `현재 카테고리: ${componentName}   카테고리를 선택 후 검색해주세요! `;
     }

     searchInput.addEventListener('keydown', (event) => {
         if (event.key === 'Enter') {
             searchProducts();
             event.preventDefault();
         }
     });

     // 조립 신청 여부 체크
     radioButtons.forEach(radio => {
        radio.addEventListener('change', () => {
            if (radio.value === 'requested' && radio.checked) {
                if (!isAssemblyRequested) {
                    isAssemblyRequested = true;

                    // 장바구니에 조립 신청 여부 추가
                    if (!currentCart['assembly_price']) {
                        currentCart['assembly_price'] = assemblyPrice;
                    }

                    // 각 장바구니 항목에 조립 신청 여부 추가
                    cartDetails.forEach(cartItem => {
                        cartItem.assembly = '조립 신청';
                    });
                }
            } else if (radio.value === 'not_requested' && radio.checked) {
                if (isAssemblyRequested) {
                    isAssemblyRequested = false;

                    // 장바구니에서 조립 신청 금액 제거
                    delete currentCart['assembly_price'];

                    // 각 장바구니 항목의 조립 신청 여부 제거
                    cartDetails.forEach(cartItem => {
                        cartItem.assembly = '조립 미신청';
                    });
                }
            }

            // 총 가격 즉시 업데이트
            updateTotalPrice();
        });
    });

     saveQuoteButton.addEventListener("click", () => {
        const quoteName = quoteNameInput.value.trim();

        // 현재 카트 데이터 구성
        currentCart = {
            quoteName: quoteName,
            assemblyStatus: isAssemblyRequested ? '조립 신청' : '조립 미신청',
            items: cartDetails, // 장바구니의 상세 항목들
            totalPrice: parseInt(document.querySelector('.total-price').textContent.replace(/[^0-9]/g, '')) || 0
        };

        const jsonCart = JSON.stringify(currentCart);
        console.log("전송할 JSON 데이터:", jsonCart);

        // local
        //fetch('http://localhost:8080/api/items', {
        // server
        fetch('http://15.168.89.127:8999/api/items', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: jsonCart,
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status} - ${response.statusText}`);
            }
            return response.json();
        })
        .then((data) => {
            alert(data.msg);
            window.location.href = data.url;
        })
        .catch((error) => {
            console.error('저장 오류:', error);
            alert('견적 저장 중 오류가 발생했습니다. 상세: ' + error.message);
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
                 updatePlaceholder(this.dataset.component);

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
             <div><strong>Inner_VGA</strong></div>
             <div><strong>Package_Type</strong></div>
             <div><strong>Cooler_Status</strong></div>
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
         const order = ["Socket", "DDR", "Generation", "Spec", "Inner_VGA", "Package_Type", "Cooler_Status", "Core", "Company"];
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
             <div><strong>Cooler_Type</strong></div>
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
         const order = ["Cooler_Type", "Socket", "Company"];
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
             <div><strong>Memory_Slot</strong></div>
             <div><strong>DDR</strong></div>
             <div><strong>Max_Storage</strong></div>
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
         const order = ["Socket", "Formfactor", "Memory_Slot", "DDR", "Max_Storage", "Company"];
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
             <div><strong>Power_Status</strong></div>
             <div><strong>Formfactor</strong></div>
             <div><strong>Tower_Size</strong></div>
             <div><strong>VGA_Length</strong></div>
             <div><strong>Power_Size</strong></div>
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
         const order = ["Power_Status", "Formfactor", "Tower_Size", "VGA_Length", "Power_Size", "Company"];
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
     // 기존 코드에서 체크박스에 대한 이벤트 리스너 등록
     topBoxLarge.addEventListener('change', function(event) {
         if (event.target.type === 'checkbox') {
             const activeComponent = document.querySelector('.component.active');
             if (activeComponent) {
                 filterProducts(activeComponent.dataset.component);
             }
         }
     });

     // 필터링된 제품을 불러오는 함수
     function filterProducts(component) {
         const filters = {};
         document.querySelectorAll('.top-box.large input[type="checkbox"]:checked').forEach(checkbox => {
             const key = checkbox.name;
             const value = checkbox.value;
             if (!filters[key]) {
                 filters[key] = [];
             }
             filters[key].push(value);
         });

         currentFilters = filters;
         fetchFilteredProducts(component, filters, selectedSort, currentSearchTerm);
     }

     // 서버에 필터링 요청을 보내는 함수
     function fetchFilteredProducts(component, filters, sort, search) {
         const query = new URLSearchParams({ sort, search }).toString();
         const url = `/api/products/filter/${component}?${query}`;

         console.log('Sending filters to server:', JSON.stringify(filters), 'with sort:', sort, 'and search:', search); // 필터 및 정렬 데이터 로깅
         fetch(url, {
                 method: 'POST',
                 headers: {
                     'Content-Type': 'application/json'
                 },
                 body: JSON.stringify(filters)
         })
         .then(response => {
             if (!response.ok) {
                 console.error('Server responded with:', response.status, response.statusText);
                 contentBox.innerHTML = `<p>Error loading products: ${response.statusText}</p>`; // 에러 메시지 업데이트
                 categoryCountElement.textContent = `${component.toUpperCase()}: 0개`; // 제품이 없는 경우에도 카운트를 0으로 설정
                 return Promise.reject(response.statusText);
             }
             return response.json();
         })
         .then(products => {
             if (products.length === 0) {
                 contentBox.innerHTML = `<p>조건에 맞는 ${component.toUpperCase()} 아이템을 찾을 수 없습니다.</p>`;
                 categoryCountElement.textContent = `${component.toUpperCase()}: 0개`; // 제품이 없는 경우에도 카운트를 0으로 설정
             } else {
                 displayProducts(products, component);
             }
         })
         .catch(error => {
             console.error('Error fetching filtered products:', error);
             contentBox.innerHTML = `<p>조건에 맞는 ${component.toUpperCase()} 아이템을 찾을 수 없습니다.</p>`;
             categoryCountElement.textContent = `${component.toUpperCase()}: 0개`; // 오류 발생 시에도 카운트를 0으로 설정
         });
     }

     /* 제품 정렬 기능 */
     // 정렬 목록 클릭 이벤트
     sortButtons.forEach(button => {
         button.addEventListener('click', (event) => {
             event.preventDefault();
             sortButtons.forEach(btn => btn.classList.remove('active'));
             button.classList.add('active');
             selectedSort = button.dataset.sort;
             const activeComponent = document.querySelector('.component.active');
             if (activeComponent) {
                 if (Object.keys(currentFilters).length > 0 || currentSearchTerm) {
                     fetchFilteredProducts(activeComponent.dataset.component, currentFilters, selectedSort, currentSearchTerm);
                 } else {
                     fetchProducts(activeComponent.dataset.component, selectedSort, currentSearchTerm);
                 }
             }
         });
     });


     // 제품 목록 출력
     function fetchProducts(category, sort, search = '') {
         let url = `/products/${category}?sort=${sort}&search=${encodeURIComponent(search)}`;
         fetch(url)
             .then(response => response.json())
             .then(products => {
                 displayProducts(products, category);
             })
             .catch(error => {
                 console.error('Error loading products:', error);
                 //contentBox.innerHTML = `<p>Error loading products: ${error.message}</p>`;
				 contentBox.innerHTML = `<p>조건에 맞는 ${category} 아이템을 찾을 수 없습니다.</p>`;
             });
     }

     // 제품 정보를 콘텐츠 박스에 동적으로 표시하는 함수
     function displayProducts(products, component) {
         if (!products || products.length === 0) {
             contentBox.innerHTML = `<p>No products found for ${component.toUpperCase()}.</p>`;
             categoryCountElement.textContent = `${component.toUpperCase()}: 0개`;
             return;
         }

         let htmlContent = `<div style="display: flex; flex-direction: column; width: 1300px; max-height: 600px; overflow-y: auto;">`;

         products.forEach(product => {
             htmlContent += `
                 <div class="product-box" style="display: flex; align-items: center; justify-content: space-between; border: 1px solid #ccc; padding: 10px; margin-bottom: 5px; background-color: #f9f9f9;">
                     <div style="display: flex; align-items: center;">
                         <img src="${product.thumbnail}" alt="${product.product_name} 이미지" style="width: 100px; height: 100px; margin-right: 10px;">
                         <div style="flex-grow: 1; min-width: 0;">
                             <div class="product-info" style="font-weight: bold; border-bottom: 1px solid black; padding-bottom: 5px; width: 1000px;">
                                 <a class="a-product-name"style="cursor: pointer" onclick="showPopup(${product.product_code}, '${component}')">
                                     ${product.product_name}
                                 </a>
                             </div>
                             <input type="hidden" class="product-stock" value="${product.stock}">
                             <div style="color: #666; font-size: 0.9em; width: 1000px;">${product.etc}</div>
                         </div>
                     </div>
                     <div class="product-price" style="text-align: right; margin-left: 10px; font-weight: bold; color: #333;">
                         ${product.price ? `${product.price.toLocaleString()}원` : '품절'}
                         <div>
                             <span style="color:rgb(245, 166, 35);" class="stars">★</span>
                             <span class="stars">${(product.avg_rating).toFixed(1)}</span>
                         </div>
                         <div style="display: flex; justify-content: flex-end; gap: 10px; margin-top: 5px;">
                             <button class="btn add-to-cart" data-seller-no="${product.seller_no}" data-product-code="${product.product_code}" data-product-name="${product.product_name}" data-product-price="${product.price}" 
                              data-product-thumbnail="${product.thumbnail}" data-product-stock="${product.stock}">담기</button>
                             <button class="btn buy-now">바로구매</button>
                         </div>
                     </div>
                 </div>
             `;
         });

         htmlContent += `</div>`;
         contentBox.innerHTML = htmlContent;
         categoryCountElement.textContent = `${component.toUpperCase()}: ${products.length}개`; // 제품 개수 업데이트

         // 바로구매 버튼 클릭 이벤트
         contentBox.querySelectorAll('.buy-now').forEach(button => {
             button.addEventListener('click', (event) => {
                 const productDiv = event.target.closest('.product-box');
                 const productName = productDiv.querySelector('.product-info a').textContent.trim();
                 const productPrice = productDiv.querySelector('.product-price').childNodes[0].textContent.trim().replace('원','').replace(',','');
                 const productThumbnail = productDiv.querySelector('img').src;
                 const productStock = productDiv.querySelector('.product-stock').value.trim();
                 const productCode = button.closest('.product-box').querySelector('.add-to-cart').getAttribute('data-product-code');
                 const sellerNo = button.closest('.product-box').querySelector('.add-to-cart').getAttribute('data-seller-no');

                 const productInfo = {
                     thumbnail: productThumbnail,
                     category: component,
                     name: productName,
                     price: productPrice,
                     quantity: 1,
                     stock: productStock,
                     product_code: productCode,
                     seller_no: sellerNo,
                     assembly: '조립 미신청',
                 };
				 localStorage.clear();
                 localStorage.setItem('selectedProduct', JSON.stringify(productInfo));

                 if(productPrice=="품절") {
                     alert("재고가 없어 결제할 수 없습니다!");
                 }else{
                     location.href = '/pay';
                 }
             });
         });

        

         // "담기" 버튼 이벤트 추가
         document.querySelectorAll('.add-to-cart').forEach(button => {
             button.addEventListener('click', () => {
                 const productName = button.getAttribute('data-product-name'); // 상품명 가져오기
                 const productPrice = button.getAttribute('data-product-price'); // 가격 가져오기
                 const productCode = button.getAttribute('data-product-code');
                 const sellerNo = button.getAttribute('data-seller-no');
                 const productThumbnail = button.getAttribute('data-product-thumbnail');
                 const productStock = button.getAttribute('data-product-stock');

                 if(productPrice==0){
                     alert("품절된 상품은 담을 수 없습니다.");
                     return;
                 }

                 addToCartforButton(productName, productPrice, productCode, sellerNo, productThumbnail, productStock);
             });
         });
     }

     // popup
     window.showPopup = function (product_code, category) {
         window.open(`/productDetail?product_code=${product_code}&category=${category}`, "제품상세이미지팝업", "width=1000, height=1500, left=100, top=50, scrollbars=1");
     };


     //담기버튼 눌렀을때 담기는 함수
     function addToCartforButton(productName, productPrice, productCode, sellerNo, productThumbnail, productStock){
        const activeComponent = document.querySelector('.component.active');

        if (activeComponent) {
            const productDetail = activeComponent.querySelector('#product-detail');
            const productPriceDiv = activeComponent.querySelector('#product-price');
            const quantityControls = createQuantityControls(productPrice, productCode);

            if (productDetail) {
                productDetail.innerHTML = `<p class="cartproductcode" data-productcode="${productCode}">${productName}</p>`;
            }

            if (productPriceDiv) {
                productPriceDiv.innerHTML = '';
                productPriceDiv.appendChild(quantityControls);
            }

            const componentName = activeComponent.dataset.component;
            currentCart[componentName] = {
                product_code: productCode,
                name: productName,
                price: parseInt(productPrice, 10),
                seller_no: sellerNo,
                quantity: 1,
            };

            const existingCartItem = cartDetails.find(item => item.product_code === productCode);
            if (!existingCartItem) {
                cartDetails.push({
                    thumbnail: productThumbnail,
                    category: componentName,
                    name: productName,
                    price: parseInt(productPrice, 10),
                    stock: productStock,
                    quantity: 1,
                    product_code: productCode,
                    seller_no: sellerNo,
                    assembly: isAssemblyRequested ? '조립 신청' : '조립 미신청',
                });
            }

            updateTotalPrice();


            toggleSidePanel();//사이드 패널 열기
        }
     }

     // 견적 리스트 선택 시 부품 불러오는 곳
     function fetchQuoteDetail(quote_no) {
        if (quote_no === "none") {
            alert("견적을 선택해주세요.");
            return;
        }
    
        // 사이드 패널 상태 확인
        const sidePanel = document.querySelector('.side-panel');
        const wasSidePanelActive = sidePanel?.classList.contains('active'); // 기존 활성화 상태 저장
		
		cartDetails = [];
        // 기존 사이드 패널 정보 비우기
		cartDetails = [];
        resetQuote();
    
        fetch(`/quote?quote_no=${quote_no}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
    
                // 컴포넌트 업데이트
                updateComponents(data);
                
                // 조립 상태를 추적
                let overallAssemblyStatus = 'N';
    
                // 기존 활성화된 컴포넌트 비활성화
                const deactivateActiveComponent = () => {
                    const activeComponent = document.querySelector('.component.active');
                    if (activeComponent) {
                        activeComponent.classList.remove('active');
                    }
                };
    
                toggleButton.click('active');
    
                
    
                for (const category in data) {
                    const categoryData = data[category];
                    const component = document.querySelector(`.component[data-component='${category}']`);
    
                    if (component) {
                        deactivateActiveComponent();
                        component.classList.add('active');
    
                        addToCart(
                            categoryData.product_name,
                            categoryData.price,
                            categoryData.product_code,
                            categoryData.seller_no,
                            categoryData.thumbnail,
                            categoryData.amount
                        );
    
                        // 조립 상태 확인
                        if (categoryData.assemblyStatus === 'Y') {
                            overallAssemblyStatus = 'Y';
                        }
                    }
                }
    
                // 조립 상태에 따른 라디오 버튼 업데이트
                const radioRequested = document.querySelector('input[value="requested"]');
                const radioNotRequested = document.querySelector('input[value="not_requested"]');

                if (overallAssemblyStatus === 'Y') {
                    radioRequested.checked = true;
                    isAssemblyRequested = true;

                    // 조립 신청 상태 장바구니 반영
                    if (!currentCart['assembly_price']) {
                        currentCart['assembly_price'] = assemblyPrice;
                    }
                    cartDetails.forEach(item => (item.assembly = '조립 신청'));
                } else {
                    radioNotRequested.checked = true;
                    isAssemblyRequested = false;

                    // 조립 미신청 상태 장바구니 반영
                    delete currentCart['assembly_price'];
                    cartDetails.forEach(item => (item.assembly = '조립 미신청'));
                }

                // 총 가격 즉각 업데이트
                updateTotalPrice();
    
                // CPU 컴포넌트 활성화
                const cpuComponent = document.querySelector('.component[data-component="CPU"]');
                if (cpuComponent) {
                    deactivateActiveComponent();
                    cpuComponent.classList.add('active');
                }
    
                // 사이드 패널 활성화 상태 유지
                if (sidePanel && wasSidePanelActive) {
                    sidePanel.classList.add('active');
                }
            })
            .catch(error => console.error('Error fetching quote details:', error));
    }

     function updateComponents(details) {
         // 데이터를 기반으로 화면에 부품 정보 출력
         console.log("Component details:", details);
     }

     // 조립 신청 여부에 따른 상태 업데이트 함수
    function updateAssemblyStatus() {
        if (isAssemblyRequested) {
            currentCart['assembly_price'] = assemblyPrice;
            cartDetails.forEach(item => item.assembly = '조립 신청');
        } else {
            delete currentCart['assembly_price'];
            cartDetails.forEach(item => item.assembly = '조립 미신청');
        }
        updateTotalPrice(); // 총 가격 업데이트
    }

     // 전역으로 노출
     window.fetchQuoteDetail = fetchQuoteDetail;

     //초기화 버튼 클릭 시 기능하는 함수
     function resetQuote(){

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

     // 저장된 견적 불러와서 카트에 담는 함수
     function addToCart(productName, productPrice, productCode, sellerNo, productThumbnail, initialQuantity = 1) {
        const activeComponent = document.querySelector('.component.active');
    
        if (activeComponent) {
            const productDetail = activeComponent.querySelector('#product-detail');
            const productPriceDiv = activeComponent.querySelector('#product-price');
            const quantityControls = createQuantityControls(productPrice, productCode, initialQuantity);
    
            if (productDetail) {
                productDetail.innerHTML = `<p class="cartproductcode" data-productcode="${productCode}">${productName}</p>`;
            }
    
            if (productPriceDiv) {
                productPriceDiv.innerHTML = '';
                productPriceDiv.appendChild(quantityControls);
            }
    
            const componentName = activeComponent.dataset.component;
            currentCart[componentName] = {
                product_code: productCode,
                name: productName,
                price: productPrice * initialQuantity, // 초기 수량 반영 가격 설정
                seller_no: sellerNo,
                quantity: initialQuantity, // 초기 수량 설정
            };
    
            const existingCartItem = cartDetails.find(item => item.product_code === productCode);
            if (!existingCartItem) {
                cartDetails.push({
                    thumbnail: productThumbnail,
                    category: componentName,
                    name: productName,
                    price: productPrice, // 초기 수량 반영 가격 설정
                    stock: initialQuantity, // 초기 수량 설정
                    quantity: initialQuantity,
                    product_code: productCode,
                    seller_no: sellerNo,
                    assembly: isAssemblyRequested ? '조립 신청' : '조립 미신청',
                });
            }
    
            updateTotalPrice();
            toggleSidePanel();
        }
    }

    // 총합 업데이트 함수
    function updateTotalPrice() {
        let total = 0;
    
        Object.values(currentCart).forEach(item => {
            const price = parseInt(item.price, 10) || 0;
            total += price; // 항목별 가격 합산
        });
    
        if (isAssemblyRequested) {
            total += assemblyPrice;
        }
        
        const totalPriceElement = document.querySelector('.total-price');
        if (totalPriceElement) {
            totalPriceElement.textContent = `${total.toLocaleString()}원`;
        }
    }

     // 수량 버튼 생성 및 총 가격 업데이트 함수
     function createQuantityControls(productPrice, productCode, initialQuantity = 1) {
        const container = document.createElement('div');
        container.classList.add('product-quantity');
    
        const decreaseButton = document.createElement('button');
        decreaseButton.textContent = '-';
        decreaseButton.classList.add('quantity-decrease', 'quantity-button');
    
        const quantityInput = document.createElement('input');
        quantityInput.type = 'number';
        quantityInput.value = initialQuantity; // 초기 수량 값 설정
        quantityInput.min = 1;
        quantityInput.classList.add('quantity-input', 'quantity-button');
    
        const increaseButton = document.createElement('button');
        increaseButton.textContent = '+';
        increaseButton.classList.add('quantity-increase', 'quantity-button');
    
        const priceDisplay = document.createElement('div');
        const initialTotalPrice = productPrice * initialQuantity;
        priceDisplay.textContent = `${initialTotalPrice.toLocaleString()}원`; // 초기 총 금액 표시
        priceDisplay.classList.add('price-display');
    
        container.appendChild(decreaseButton);
        container.appendChild(quantityInput);
        container.appendChild(increaseButton);
    
        const controlsWrapper = document.createElement('div');
        controlsWrapper.classList.add('quantity-controls-wrapper');
        controlsWrapper.appendChild(container);
        controlsWrapper.appendChild(priceDisplay);
    
        // Event listeners for quantity controls
        decreaseButton.addEventListener('click', (event) => {
            event.stopPropagation();
            let quantity = parseInt(quantityInput.value, 10) || 1;
            if (quantity > 1) {
                quantity--;
                quantityInput.value = quantity;
                updateCartAndPrice(quantity, productPrice, priceDisplay, productCode);
            }
        });
    
        increaseButton.addEventListener('click', (event) => {
            event.stopPropagation();
            let quantity = parseInt(quantityInput.value, 10) || 1;
            quantity++;
            quantityInput.value = quantity;
            updateCartAndPrice(quantity, productPrice, priceDisplay, productCode);
        });
    
        quantityInput.addEventListener('input', (event) => {
            event.stopPropagation();
            let quantity = parseInt(quantityInput.value, 10);
            if (isNaN(quantity) || quantity < 1) {
                quantity = 1;
            }
            quantityInput.value = quantity;
            updateCartAndPrice(quantity, productPrice, priceDisplay, productCode);
        });
    
        return controlsWrapper;
    }

     // 업데이트 함수
     function updateCartAndPrice(quantity, unitPrice, priceDisplay, productCode) {
        // Update `cartDetails`
        const cartItem = cartDetails.find(item => item.product_code === productCode);
        if (cartItem) {
            cartItem.quantity = quantity; // 수량 업데이트
            cartItem.price = unitPrice * quantity; // 해당 항목의 총 가격 업데이트
        }
    
        // Update `currentCart`
        Object.keys(currentCart).forEach(key => {
            if (currentCart[key].product_code === productCode) {
                currentCart[key].quantity = quantity;
                currentCart[key].price = unitPrice * quantity; // 수량 반영하여 가격 업데이트
            }
        });
    
        // Update UI price display
        const totalPrice = quantity * parseInt(unitPrice, 10);
        priceDisplay.textContent = `${totalPrice.toLocaleString()}원`;
    
        // Update total price
        updateTotalPrice();
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
         const totalPrice = quantity * parseInt(unitPrice, 10);
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
         const selectedOption = savedQuoteSelect.options[savedQuoteSelect.selectedIndex];
         if (selectedOption.value === "none") {
             quoteNameInput.value = "";
         } else {
             quoteNameInput.value = selectedOption.text;
         }
     });


     currentCartButton.addEventListener('click', () => {
         console.log(currentCart);

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

     //호환성체크 모달 열기
     openModal.addEventListener('click', () => {
         //modal.style.display = 'block';

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

     /*//모든 컴포넌트 초기화
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
     }*/

     //모두 초기화 시킨 후 CPU가 선택되있는 상태로 만듬
     window.location.reload();
 }

 // 구매하기 버튼 클릭 이벤트
 function goPayPage() {

    // 선택된 제품이 없을 경우 경고 메시지 표시
    if (cartDetails.length === 0) {
        alert("선택된 제품이 없습니다. 장바구니를 확인해주세요.");
        return;
    }
    
    // 라디오 버튼 상태 확인
    const radioButtons = document.querySelectorAll('input[name="assembly"]');
    let assemblyStatus = 'N';

    // 라디오 버튼 변경 시 즉각 반영
    radioButtons.forEach(radio => {
        radio.addEventListener('change', () => {
            if (radio.value === 'requested' && radio.checked) {
                isAssemblyRequested = true;
            } else if (radio.value === 'not_requested' && radio.checked) {
                isAssemblyRequested = false;
            }
            updateAssemblyStatus();
        });
    });

     // localStorage에 저장
	 localStorage.clear();
     localStorage.setItem('CartDetails', JSON.stringify(cartDetails));


    // 조립 신청 여부에 따라 assemblyStatus 반영
    cartDetails.forEach(item => {
        item.assemblyStatus = assemblyStatus;
    });

    // localStorage에 저장
    localStorage.setItem('CartDetails', JSON.stringify(cartDetails));

    // 결제 페이지로 이동
    window.location.href = '/pay';

    console.log("결제페이지에 넘길 데이터: ");
    console.log(cartDetails); // 확인용 로그
 }

 //토글 버튼 눌러서 사이드 패널 보여주기
 function toggleSidePanel() {
     const toggleButton = document.getElementById("toggle-button");
     if (toggleButton) {
         toggleButton.click(); // 토글 버튼의 클릭 이벤트를 프로그래매틱하게 실행
     }
 }

 /*-----호환성체크 모달----- */
 // 모달 제어 스크립트
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



 //호환성 체크
 openModal.addEventListener('click', () => {
     const compatibilityitem = document.querySelectorAll('.component');
     const data = Array.from(compatibilityitem).map((item) => {
         const category = item.querySelector('.component-header p').textContent.trim();
         if (!["테스트"].includes(category)) {
             const productElement = item.querySelector('.cartproductcode');
             if (productElement) {
                 const productcode = productElement.dataset.productcode;
                 if(category === "메모리"){
                     return {category : "RAM",productcode :productcode};
                 }else{
                     return { category: category, productcode: productcode };
                 }

             } else {
                 return ' ';
             }

         }
         return ' ';
     }).filter((item) => item !== ' ');;
     if (data.length === 0) {
         alert("부품을 담아주세요.");
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

 // 모달 외부 클릭 시 닫기
 window.addEventListener('click', (event) => {
     if (event.target === modal) {
         modal.style.display = 'none';
         resetModal();
     }
 });

 document.addEventListener("DOMContentLoaded", function () {
     // 모든 .compatibility 요소를 선택
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
 });

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
 /*-------------------------*/