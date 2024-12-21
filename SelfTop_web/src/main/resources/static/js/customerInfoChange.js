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

function checkCurrentPassword() {
    const currentPassword = document.querySelector('#pw').value;

    // 비밀번호가 비어있는 경우, 오류 메시지를 지운다
    if (!currentPassword) {
        document.querySelector('#pw-error').textContent = '';
        return;
    }

    // 서버로 기존 비밀번호 검증 요청
    $.ajax({
        url: '/verifyPassword',  // 서버에 비밀번호 검증을 위한 엔드포인트
        method: 'POST',
        data: {
            currentPassword: currentPassword
        },
        success: function(response) {
            if (response.isValid) {
                // 비밀번호가 일치하면 오류 메시지 제거
                document.querySelector('#pw-error').textContent = '';
            } else {
                // 비밀번호가 일치하지 않으면 오류 메시지 표시
                document.querySelector('#pw-error').textContent = '기존 비밀번호가 일치하지 않습니다.';
            }
        },
        error: function() {
            document.querySelector('#pw-error').textContent = '서버 오류가 발생했습니다. 다시 시도해주세요.';
        }
    });
}

// 변경할 비밀번호 조건 검사
function validatePassword() {
	const password = document.getElementById("new_pw").value;
	const passwordError = document.getElementById("new_pw-error");
	const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,12}$/;

	if (!passwordPattern.test(password)) {
		passwordError.textContent = "영문 대소문자, 숫자, 특수기호 포함 8~12자로 입력해주세요.";
		passwordError.style.color = "red";
	} else {
		passwordError.textContent = "사용 가능한 비밀번호입니다.";
		passwordError.style.color = "green";
	}
}

// 변경할 비밀번호 확인 검증
function validateConfirmPassword() {
	const password = document.getElementById("new_pw").value; // 비밀번호 입력값
	const confirmPassword = document.getElementById("confirmPassword").value; // 재확인 비밀번호 입력값
	const confirmError = document.getElementById("confirm-error"); // 오류 메시지 표시할 영역

	// 비밀번호 일치 여부
	if (password !== confirmPassword) {
		confirmError.textContent = "변경할 비밀번호가 일치하지 않습니다.";
		confirmError.style.color = "red";
	} else {
		confirmError.textContent = "변경할 비밀번호가 일치합니다.";
		confirmError.style.color = "green";
	}
}

// 계정 정보 변경 함수
document.querySelector('.btn-change-account').addEventListener('click', function(event) {
    // 폼 제출을 막고
    event.preventDefault();

    // 계정 정보 변경 데이터를 준비
    const name = document.getElementById('name').textContent;
	const emailId = document.getElementById('email-id').textContent;
    const emailDomain = document.getElementById('email-domain').textContent;
    const phone = document.getElementById('phone').value;

    // 계정 정보 데이터
    const accountData = {
        name,
		emailId,
		emailDomain,
		phone
    };

    // 서버로 데이터 전송 (AJAX 또는 fetch 사용)
    // 예시로 콘솔에 출력
    console.log('계정 정보 변경:', accountData);

    // 계정 정보 변경 후 메시지
    alert('계정 정보가 변경되었습니다.');

    // 실제 서버 요청을 보내는 코드 추가 가능
    // fetch('/updateAccountInfo', {
    //     method: 'POST',
    //     body: JSON.stringify(accountData),
    //     headers: {
    //         'Content-Type': 'application/json'
    //     }
    // }).then(response => response.json())
    //   .then(data => {
    //       alert('계정 정보가 변경되었습니다.');
    //   }).catch(error => {
    //       console.error('Error:', error);
    //   });
	location.href = '/mypage';
});

// 비밀번호 변경 함수
document.querySelector('.btn-change-pw').addEventListener('click', function(event) {
    // 폼 제출을 막고
    event.preventDefault();

    // 비밀번호 변경 데이터를 준비
    const currentPassword = document.getElementById('pw').value;
    const newPassword = document.getElementById('new_pw').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    // 비밀번호 유효성 검사
    if (newPassword !== confirmPassword) {
        alert('새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.');
        return;
    }

    // 비밀번호 데이터
    const passwordData = {
        currentPassword,
        newPassword
    };

    // 서버로 데이터 전송 (AJAX 또는 fetch 사용)
    // 예시로 콘솔에 출력
    console.log('비밀번호 변경:', passwordData);

    // 비밀번호 변경 후 메시지
    alert('비밀번호가 변경되었습니다.');

    // 실제 서버 요청을 보내는 코드 추가 가능
    // fetch('/updatePassword', {
    //     method: 'POST',
    //     body: JSON.stringify(passwordData),
    //     headers: {
    //         'Content-Type': 'application/json'
    //     }
    // }).then(response => response.json())
    //   .then(data => {
    //       alert('비밀번호가 변경되었습니다.');
    //   }).catch(error => {
    //       console.error('Error:', error);
    //   });
	location.href = '/login/loginform';
});

// 탈퇴하기 버튼 클릭 시 확인 창 띄우기
document.querySelector('.btn-delete').addEventListener('click', function(event) {
    // 폼 제출을 막고
    event.preventDefault();

    // 탈퇴 확인 창 띄우기
    const isConfirmed = confirm('정말로 회원을 탈퇴하시겠습니까?');

    if (isConfirmed) {
        // 사용자가 '확인'을 클릭한 경우
        const email = document.getElementById('email').value;
        const password = document.getElementById('pw').value;

        // 탈퇴 데이터 준비
        const deleteData = {
            email,
            password
        };

        // 서버로 데이터 전송 (AJAX 또는 fetch 사용)
        // 예시로 콘솔에 출력
        console.log('회원 탈퇴:', deleteData);

        // 탈퇴 후 메시지
        alert('회원 탈퇴가 완료되었습니다.');

        // 여기서 실제 서버 요청을 보내는 코드 추가 가능 (예: fetch, AJAX)
        // fetch('/deleteAccount', {
        //     method: 'POST',
        //     body: JSON.stringify(deleteData),
        //     headers: {
        //         'Content-Type': 'application/json'
        //     }
        // }).then(response => response.json())
        //   .then(data => {
        //       alert('회원 탈퇴가 완료되었습니다.');
        //   }).catch(error => {
        //       console.error('Error:', error);
        //   });
		location.href = '/';
    } else {
        // 사용자가 '취소'를 클릭한 경우
        alert('회원 탈퇴가 취소되었습니다.');
    }
});


// 이벤트 리스너 추가
document.getElementById("new_pw").addEventListener("input", validatePassword);
document.getElementById("confirmPassword").addEventListener("input", confirmPassword);