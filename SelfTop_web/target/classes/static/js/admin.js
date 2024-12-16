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
	 const textareas = document.querySelectorAll('.etc-textarea');

	 textareas.forEach(textarea => {
	   textarea.addEventListener('input', function () {
	     this.style.height = 'auto'; // 기존 높이를 초기화
	     this.style.height = `${this.scrollHeight}px`; // 입력 내용에 따라 높이 조정
	   });
	 });
	 
	 // 각 form의 submit 버튼 클릭 시 AJAX 요청 처리
	    forms.forEach(form => {
	        form.addEventListener('submit', (event) => {
	            event.preventDefault(); // 기본 form 제출 방지

	            // FormData 객체 생성
	            const formData = new FormData(form);

	            // AJAX 요청
	            fetch(form.action, {
	                method: 'POST',
	                body: formData
	            })
	                .then(response => response.json())
	                .then(data => {
	                    if (data.success) {
	                        alert('제품이 성공적으로 추가되었습니다.');
	                        window.location.href = '/admin/main'; // 성공 시 리다이렉션
	                    } else {
	                        alert('제품 추가에 실패했습니다: ' + data.message);
	                    }
	                })
	                .catch(error => {
	                    console.error('Error:', error);
	                    alert('서버와의 연결 중 문제가 발생했습니다.');
	                });
	        });
	    });
	 
});

        