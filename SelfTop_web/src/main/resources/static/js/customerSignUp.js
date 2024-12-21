// ID 중복 확인 (버튼 클릭 시만 유효성 검사)
function checkDuplicateID() {
	const id = document.getElementById("id").value;
	const idError = document.getElementById("id-error");

	// 빈 입력 처리
	if (id === "") {
		idError.textContent = "ID를 입력해주세요.";
		idError.style.color = "red";
		hasCheckedDuplicateID = false; // 중복 체크 상태 초기화
		alert("ID를 입력해주세요."); // Alert 추가
		return;
	}

	// 유효성 검사
	const idPattern = /^[a-z0-9]+$/; // 영문 소문자와 숫자만 허용
	if (!idPattern.test(id)) {
		idError.textContent = "영문 소문자와 숫자만 가능합니다.";
		idError.style.color = "red";
		isIdValid = false;
		hasCheckedDuplicateID = false; // 중복 체크 상태 초기화
		return;
	}
	else{
		isIdValid = true;
		hasCheckedDuplicateID = false;
	}

	// 서버에 중복 확인 요청
	fetch(`idchk?id=${id}`)
		.then(response => response.json())
		.then(data => {
			if (data === true) {
				idError.textContent = "사용 가능한 ID입니다.";
				idError.style.color = "green";
				isIdValid1 = true;
				hasCheckedDuplicateID = true;
			} else {
				idError.textContent = "이미 사용 중인 ID입니다.";
				idError.style.color = "red";
				isIdValid1=false;
				hasCheckedDuplicateID = true;
			}
		})
		.catch(error => {
			idError.textContent = "중복 확인 중 문제가 발생했습니다.";
			idError.style.color = "red";
			console.error(error);
			isIdValid1 = false;
			hasCheckedDuplicateID = true;
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
	} else {
		passwordError.textContent = "사용 가능한 비밀번호입니다.";
		passwordError.style.color = "green";
	}
}

// 비밀번호 확인 검증
function validateConfirmPassword() {
	const password = document.getElementById("pw").value; // 비밀번호 입력값
	const confirmPassword = document.getElementById("confirmPassword").value; // 재확인 비밀번호 입력값
	const confirmError = document.getElementById("confirm-error"); // 오류 메시지 표시할 영역

	// 비밀번호 일치 여부
	if (password !== confirmPassword) {
		confirmError.textContent = "비밀번호가 일치하지 않습니다.";
		confirmError.style.color = "red";
	} else {
		confirmError.textContent = "비밀번호가 일치합니다.";
		confirmError.style.color = "green";
	}
}

// 이메일 도메인 선택 시 자동으로 입력되도록 하는 함수
function updateEmailDomain() {
    var domainSelect = document.getElementById('email-domain-select');
    var domainInput = document.getElementById('email-domain');
    
    // 선택한 도메인이 '직접 입력'일 경우
    if (domainSelect.value === 'direct') {
        domainInput.readOnly = false;
        domainInput.value = '';
    } else {
        domainInput.readOnly = true;
        domainInput.value = domainSelect.value;
    }
}

// 페이지 로드 시, 기본적으로 이메일 도메인 입력란을 비활성화 상태로 설정
window.onload = function() {
    document.getElementById('email-domain').readOnly = true;
};


// 이벤트 리스너 추가
document.getElementById("checkDuplicate").addEventListener("click", checkDuplicateID);
document.getElementById("pw").addEventListener("input", validatePassword);
document.getElementById("confirmPassword").addEventListener("input", confirmPassword);