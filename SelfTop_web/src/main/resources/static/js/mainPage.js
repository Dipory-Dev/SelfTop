document.addEventListener("DOMContentLoaded", () => {
    const toggleButton = document.getElementById("toggle-button");
    const sidePanel = document.querySelector(".side-panel");
    const components = document.querySelectorAll('.component');
    const selectedPartDiv = document.querySelector('.selected-part');
    const currentCartButton = document.getElementById("current-cart");
    const savedQuoteSelect = document.querySelector('.saved-quote');

    let currentCart = {};

    toggleButton.addEventListener("click", () => {
        sidePanel.classList.toggle("active");
    });

    components.forEach(component => {
        component.addEventListener('click', function() {
            if (!this.classList.contains('active')) {
                components.forEach(comp => comp.classList.remove('active'));
                this.classList.add('active');
                displayPartDetails(this.dataset.component);
            }
        });
    });

    function displayPartDetails(component) {
        selectedPartDiv.innerHTML = `<h3>${component} 세부 정보</h3>
            <p>선택된 부품: ${component}</p>
            <button onclick="removeSelectedPart()">부품 제거</button>`;
        currentCart[component] = component; // 현재 카트에 컴포넌트 추가
    }

    window.removeSelectedPart = function() {
        document.querySelectorAll('.component').forEach(comp => comp.classList.remove('active'));
        selectedPartDiv.innerHTML = '';
        delete currentCart[comp.dataset.component]; // 컴포넌트 제거
    }

    savedQuoteSelect.addEventListener('change', () => {
        const selectedOption = savedQuoteSelect.value;
        // 선택된 견적 로드
        if (selectedOption && savedQuotes[selectedOption]) {
            loadQuote(savedQuotes[selectedOption]);
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
