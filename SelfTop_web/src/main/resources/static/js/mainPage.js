document.addEventListener("DOMContentLoaded", () => {
    const toggleButton = document.getElementById("toggle-button");
    const sidePanel = document.querySelector(".side-panel");

    toggleButton.addEventListener("click", () => {
        sidePanel.classList.toggle("active");
    });
	
	const components = document.querySelectorAll('.component');
	    const selectedPartDiv = document.querySelector('.selected-part');

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
	        // 예시로 'component' 이름을 이용한 정보 표시
	        selectedPartDiv.innerHTML = `<h3>${component} 세부 정보</h3>
	            <p>선택된 부품: ${component}</p>
	            <button onclick="removeSelectedPart()">부품 제거</button>`;
	    }

	    window.removeSelectedPart = function() {
	        document.querySelectorAll('.component').forEach(comp => comp.classList.remove('active'));
	        selectedPartDiv.innerHTML = '';
	    }
	
});