document.addEventListener('DOMContentLoaded',()=>{
	// DOM 요소 가져오기
	const selectElement = document.getElementById('parts');
	const forms = document.querySelectorAll('.part-form');
	const submitContainer = document.getElementById('submit-container');
	const submitButton = document.getElementById('submit-button');

	// 선택 이벤트 처리
	selectElement.addEventListener('change', (event) => {
		const selectedValue = event.target.value;

		// 모든 폼 숨기기
		forms.forEach(form => form.classList.add('hidden'));

	    // 선택된 부품에 해당하는 폼만 표시
	    if (selectedValue) {
	          const activeForm = document.getElementById(`${selectedValue}-form`);
	          if (activeForm) {
	              activeForm.classList.remove('hidden');
	              submitButton.setAttribute('form', activeForm.id); // 제출 버튼과 해당 폼 연결
	              submitContainer.classList.remove('hidden'); // 제출 버튼 표시
	          }
	    } else {
	        // 선택이 없으면 버튼 숨기기
	        submitContainer.classList.add('hidden');
	        submitButton.removeAttribute('form');
	    }
	 });
	 
	 //etc textarea height자동 조절
	 const textarea = document.getElementById('etc-textarea');

	 textarea.addEventListener('input', function () {
	   this.style.height = 'auto'; // 기존 높이를 초기화
	   this.style.height = `${this.scrollHeight}px`; // 입력 내용에 따라 높이 조정
	 });
});

        