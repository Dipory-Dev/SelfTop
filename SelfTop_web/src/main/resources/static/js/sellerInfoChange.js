function checkCurrentPassword() {
    const currentPassword = document.querySelector('#pw').value; // 사용자가 입력한 비밀번호
	
    // 비밀번호가 비어있는 경우
    if (!currentPassword) {
        document.querySelector('#pw-error').textContent = '비밀번호를 입력해주세요.';
        return;
    }

    // 비밀번호 검증 요청
	fetch(`idchk?id=${id}`)
			.then(response => response.json())
			.then(data => {
				if (data === true) {
					idError.textContent = "사용 가능한 ID입니다.";
					idError.style.color = "green";
				} else {
					idError.textContent = "이미 사용 중인 ID입니다.";
					idError.style.color = "red";
				}
			})
			.catch(error => {
				idError.textContent = "중복 확인 중 문제가 발생했습니다.";
				idError.style.color = "red";
				console.error(error);
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

// 계정 정보 변경 함수
document.querySelector('.btn-change-account').addEventListener('click', function(event) {
    // 폼 제출을 막고
    event.preventDefault();

    // 계정 정보 변경 데이터를 준비
    const ceoName = document.getElementById('ceo_name').textContent;
    const companyName = document.getElementById('company_name').textContent;
    const phone = document.getElementById('phone').value;
    const zipNo = document.getElementById('zipNo').value;
    const address1 = document.getElementById('address1').value;
    const address2 = document.getElementById('address2').value;

    // 계정 정보 데이터
    const accountData = {
        ceoName,
        companyName,
        phone,
        zipNo,
        address1,
        address2
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
	window.location.href = 'seller/sellerSignUp.html';
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
	window.location.href = 'seller/sellerSignUp.html';
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
		window.location.href = 'seller/sellerSignUp.html';
    } else {
        // 사용자가 '취소'를 클릭한 경우
        alert('회원 탈퇴가 취소되었습니다.');
    }
});


// 이벤트 리스너 추가
document.getElementById("new_pw").addEventListener("input", validatePassword);
document.getElementById("confirmPassword").addEventListener("input", confirmPassword);