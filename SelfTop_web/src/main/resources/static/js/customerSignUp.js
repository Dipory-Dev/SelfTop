document.addEventListener("DOMContentLoaded", () => {
	let isIdDuplicate = false; // ID가 중복인 경우 -> true
	let pwValid = false; // 비밀번호 유효성 검사 상태
	let pwValid1 = false; // 비밀번호 확인 상태

	const signupButton = document.getElementById('btn-signup');
	
	// ID 중복 확인 (버튼 클릭 시만 유효성 검사)
	function checkDuplicateID() {
		const id = document.getElementById("id").value;
		const idError = document.getElementById("id-error");
	
		if (id === "") {
			idError.textContent = "ID를 입력해주세요.";
			idError.style.color = "red";
			isIdDuplicate = true; // 중복 상태로 설정
			updateSignupButtonState();
			return;
		}
	
		const idPattern = /^[a-z0-9]+$/;
		if (!idPattern.test(id)) {
			idError.textContent = "영문 소문자와 숫자만 가능합니다.";
			idError.style.color = "red";
			isIdDuplicate = true; // 중복 상태로 설정
			updateSignupButtonState();
			return;
		}
	
		fetch(`idchk?id=${id}`)
			.then(response => response.json())
			.then(data => {
				if (data === true) {
					idError.textContent = "사용 가능한 ID입니다.";
					idError.style.color = "green";
					isIdDuplicate = false; // 중복 아님
				} else {
					idError.textContent = "이미 사용 중인 ID입니다.";
					idError.style.color = "red";
					isIdDuplicate = true; // 중복
				}
				updateSignupButtonState();
			})
			.catch(error => {
				idError.textContent = "중복 확인 중 문제가 발생했습니다.";
				idError.style.color = "red";
				console.error(error);
				isIdDuplicate = true; // 중복 상태로 설정
				updateSignupButtonState();
			});
	}
	
	// 비밀번호 조건 검사
	function validatePassword() {
		const password = document.getElementById("pw").value;
		const passwordError = document.getElementById("pw-error");
		const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,12}$/;
	
		if (!passwordPattern.test(password)) {
			passwordError.textContent = "영문 대소문자, 숫자, 특수기호 포함 8~12자로 입력해주세요.";
			passwordError.style.color = "red";
			pwValid = false;
			updateSignupButtonState();
		} else {
			passwordError.textContent = "사용 가능한 비밀번호입니다.";
			passwordError.style.color = "green";
			pwValid = true;
			updateSignupButtonState();
		}
	}
	
	// 비밀번호 확인 검증
	function validateConfirmPassword() {
		const password = document.getElementById("pw").value;
		const confirmPassword = document.getElementById("confirmPassword").value;
		const confirmError = document.getElementById("confirm-error");
	
		if (password !== confirmPassword) {
			confirmError.textContent = "비밀번호가 일치하지 않습니다.";
			confirmError.style.color = "red";
			pwValid1 = false;
			updateSignupButtonState();
		} else {
			confirmError.textContent = "비밀번호가 일치합니다.";
			confirmError.style.color = "green";
			pwValid1 = true;
			updateSignupButtonState();
		}
	}
	
	// Signup 버튼 활성화 상태 업데이트
	function updateSignupButtonState() {
		if (isIdDuplicate === true || pwValid === false || pwValid1 === false) {
			signupButton.disabled = true;
		} else {
			signupButton.disabled = false;
		}
	}
	
	// 이벤트 리스너 추가
	document.getElementById("checkDuplicate").addEventListener("click", checkDuplicateID);
	document.getElementById("pw").addEventListener("input", validatePassword);
	document.getElementById("confirmPassword").addEventListener("input", validateConfirmPassword);
	
	// 초기 상태
	updateSignupButtonState();
});