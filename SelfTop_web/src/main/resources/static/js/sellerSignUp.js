// ID 중복 확인 (버튼 클릭 시 유효성 검사 및 서버 요청)
function checkDuplicateID() {
    const id = document.getElementById("id").value;
    const idError = document.getElementById("id-error");

    // 빈 입력 처리
    if (id === "") {
        idError.textContent = "ID를 입력해주세요.";
        idError.style.color = "red";
        return;
    }

    // 유효성 검사
    const idPattern = /^[a-z0-9]+$/; // 영문 소문자와 숫자만 허용
    if (!idPattern.test(id)) {
        idError.textContent = "영문 소문자와 숫자만 가능합니다.";
        idError.style.color = "red";
        return;
    }

    // 서버에 중복 확인 요청 (jQuery 사용)
    $.ajax({
        url: `/check-duplicate-id?id=${id}`,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.isDuplicate) {
                idError.textContent = "이미 사용 중인 ID입니다.";
                idError.style.color = "red";
            } else {
                idError.textContent = "사용 가능한 ID입니다.";
                idError.style.color = "green";
            }
        },
        error: function (error) {
            idError.textContent = "중복 확인 중 문제가 발생했습니다.";
            idError.style.color = "red";
            console.error(error);
        }
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

// 사업자등록번호 입력 시 숫자만 입력받기
document.getElementById("business_license").addEventListener("input", function(e) {
    var value = e.target.value;
    // 숫자 이외의 문자 제거
    e.target.value = value.replace(/[^0-9]/g, "");
});

// 사업자등록번호 정보 조회 함수
function corp_chk() {
    var reg_num = document.getElementById("business_license").value;

    if(!reg_num) {
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
            if(result.match_cnt == "1") {
                alert("사업자등록번호가 유효합니다.");
            } else {
                alert("유효하지 않은 사업자등록번호입니다.");
            }
        },
		error: function(result) {
			console.log("error");
		    console.log(result.responseText); //responseText의 에러메세지 확인
		}
    });
}

// 이벤트 리스너 추가
document.getElementById("checkDuplicate").addEventListener("click", checkDuplicateID);
document.getElementById("pw").addEventListener("input", validatePassword);
document.getElementById("confirmPassword").addEventListener("input", confirmPassword);
document.getElementById("checkBusinessInfo").addEventListener("click", corp_chk);