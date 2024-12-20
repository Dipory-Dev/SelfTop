const signupButton = document.getElementById('btn-signup');
signupButton.disabled = true;

let isIdValid = false;
let isIdValid1 = false;
let isPwValid = false;
let isPwValid1 =false;
let isCrpValid = false;

let hasCheckedDuplicateID = false; // ID 중복 확인 버튼 클릭 여부

function updateSignupButtonState(){
	if(isIdValid && isIdValid1 && isPwValid && isPwValid1 && isCrpValid){
		signupButton.disabled = false;
	}
	else{
		signupButton.disabled = true;
	}
	  

}

// ID 중복 확인 (버튼 클릭 시만 유효성 검사)
function checkDuplicateID() {
	const id = document.getElementById("id").value;
	const idError = document.getElementById("id-error");

	
	// 빈 입력 처리
	if (id === "") {
		idError.textContent = "ID를 입력해주세요.";
		idError.style.color = "red";
		hasCheckedDuplicateID = false; // 중복 체크 상태 초기화
		
		return;
	}

	// 유효성 검사
	const idPattern = /^[a-z0-9]+$/; // 영문 소문자와 숫자만 허용
	if (!idPattern.test(id)) {
		idError.textContent = "영문 소문자와 숫자만 가능합니다.";
		idError.style.color = "red";
		isIdValid = false;
		hasCheckedDuplicateID = false; // 중복 체크 상태 초기화
		updateSignupButtonState();
		return;
	}
	else{
		isIdValid = true;
		updateSignupButtonState();
		
	}

	

	// 서버에 중복 확인 요청
	fetch(`idchk?id=${id}`)
		.then(response => response.json())
		.then(data => {
			if (data === true) {
				idError.textContent = "사용 가능한 ID입니다.";
				idError.style.color = "green";
				isIdValid1 = true;
			} else {
				idError.textContent = "이미 사용 중인 ID입니다.";
				idError.style.color = "red";
				isIdValid1=false;
			}
			hasCheckedDuplicateID = true; // 중복 확인 버튼을 눌렀음을 표시
			updateSignupButtonState();

		})
		.catch(error => {
			idError.textContent = "중복 확인 중 문제가 발생했습니다.";
			idError.style.color = "red";
			console.error(error);
			isIdValid1 = false;
			hasCheckedDuplicateID = true; // 버튼은 눌렸음
            updateSignupButtonState();
		});

		
}

// ID 입력 시 중복 상태 초기화
document.getElementById("id").addEventListener("input", function () {
    isIdValid = false; // ID 형식이 바뀌었으므로 유효성 초기화
    isIdValid1 = false; // 중복 확인 상태 초기화
    hasCheckedDuplicateID = false; // 중복 확인 버튼 상태 초기화
    updateSignupButtonState();
	
});

// 비밀번호 조건 검사
function validatePassword() {
	const password = document.getElementById("pw").value;
	const passwordError = document.getElementById("pw-error");
	const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,12}$/;

	if (!passwordPattern.test(password)) {
		passwordError.textContent = "영문 대소문자, 숫자, 특수기호 포함 8~12자로 입력해주세요.";
		passwordError.style.color = "red";
		isPwValid= false;
	} else {
		passwordError.textContent = "사용 가능한 비밀번호입니다.";
		passwordError.style.color = "green";
		isPwValid=true;
	}

	
	validateConfirmPassword();
	updateSignupButtonState();
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
		isPwValid1=false;
	} else {
		confirmError.textContent = "비밀번호가 일치합니다.";
		confirmError.style.color = "green";
		isPwValid1 =true;
	}
	
	updateSignupButtonState();
}



// 사업자등록번호 정보 조회 함수
function corp_chk() {
	var reg_num = document.getElementById("business_license").value;
	const confirmError = document.getElementById("business-error");
	
	if (!reg_num) {
		alert("사업자등록번호를 입력해주세요.");
		return false;
	}

	

	var data = { "b_no": [reg_num] };

	// API 호출하여 사업자등록번호 유효성 검사
	$.ajax({
		url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=P7t7mjzfLQo8EBOtv4VHqd1mcQED0dwuGmfOOrQ32dnxlfjZpbIG0KBUE5UoE9ywBHbeFirq2C59XQSJNvD0GA%3D%3D",  // 실제 서비스 키로 대체
		type: "POST",
		data: JSON.stringify(data),
		dataType: "json",  // 응답 형식을 JSON으로 설정
		traditional: true,
		contentType: "application/json; charset=UTF-8",
		accept: "application/json",
		success: function(result) {
			console.log(result); // 응답 결과 확인
			if (result.match_cnt == "1") {
				confirmError.textContent = "사업자등록번호가 유효합니다.";
				confirmError.style.color = "green";
				isCrpValid =true;
			} else {
				confirmError.textContent = "유효하지 않은 사업자등록번호입니다";
				confirmError.style.color = "red";
				isCrpValid = false;
			}
			updateSignupButtonState();
		},
		error: function(result) {
			console.log("error");
			console.log(result.responseText); //responseText의 에러메세지 확인
			isCrpValid = false;
			updateSignupButtonState();
		}
	});



}

// 사업자등록번호 입력 시 유효성 상태 초기화
document.getElementById("business_license").addEventListener("input", function (e) {
    var value = e.target.value;
    // 숫자 이외의 문자 제거
    e.target.value = value.replace(/[^0-9]/g, "");
    
    // 사업자등록번호가 수정된 경우 유효성 초기화
    isCrpValid = false; // 유효성 초기화
    const confirmError = document.getElementById("business-error");
    confirmError.textContent = ""; // 오류 메시지 초기화
    updateSignupButtonState(); // 상태 갱신
});


// 주소검색 팝업창 
function goPopup() {
	const popupUrl = '/seller/addressPopup';

	window.open(popupUrl, 'pop', "width=570,height=420, scrollbars=yes, resizable=yes");
}


function jusoCallBack(zipNo, roadAddrPart1, addrDetail) {

	document.getElementById("zipNo").value = zipNo;
	document.getElementById("address1").value = roadAddrPart1;
	document.getElementById("address2").value = addrDetail;
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
    document.getElementById('email-domain').disabled = true;
	
};


// 이벤트 리스너 추가
document.getElementById("checkDuplicate").addEventListener("click", checkDuplicateID);
document.getElementById("pw").addEventListener("input", validatePassword);
document.getElementById("confirmPassword").addEventListener("input", confirmPassword);
document.getElementById("checkBusinessInfo").addEventListener("click", corp_chk);
document.getElementById("business_license").addEventListener("input", function(e) { // 사업자등록번호 입력 시 숫자만 입력받기
	var value = e.target.value;
	// 숫자 이외의 문자 제거
	e.target.value = value.replace(/[^0-9]/g, "");
});