const changePwButton = document.querySelector('.btn-change-pw');

// 초기 상태: 비활성화
changePwButton.disabled = true;

let isPwValid = false;
let isPwValid1 = false;

// 비밀번호 유효 체크 함수
function pwCheck() {
    
    if (isPwValid && isPwValid1) {
        changePwButton.disabled = false;
    } else {
        changePwButton.disabled = true;
    }
}


// 변경할 비밀번호 조건 검사
function validatePassword() {
    const password = document.getElementById("new_pw").value;
    const passwordError = document.getElementById("new_pw-error");
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,12}$/;

    if (!passwordPattern.test(password)) {
        passwordError.textContent = "영문 대소문자, 숫자, 특수기호 포함 8~12자로 입력해주세요.";
        passwordError.style.color = "red";
        isPwValid = false;
    } else {
        passwordError.textContent = "사용 가능한 비밀번호입니다.";
        passwordError.style.color = "green";
        isPwValid = true;
    }

    pwCheck(); 
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
        isPwValid1 = false;
    } else {
        confirmError.textContent = "변경할 비밀번호가 일치합니다.";
        confirmError.style.color = "green";
        isPwValid1 = true;
    }

    pwCheck(); 
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
function pwChange(event){
    event.preventDefault();

    // 초기 상태에서 click 이벤트 차단 조건 확인
    if (!isPwValid || !isPwValid1) {
        alert('비밀번호 형식을 준수하거나 비밀번호 확인을 완료해주세요.');
        return; // 추가 코드 실행 차단
    }

    // 비밀번호 일치 여부 확인
    const newPassword = document.getElementById('new_pw').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (newPassword !== confirmPassword) {
        alert('새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.');
        return; // 추가 코드 실행 차단
    }

    
    // 비밀번호 데이터 준비
    const currentPassword = document.getElementById('pw').value;
    const passwordData = {
        currentPassword,
        newPassword
    };

    // 서버로 데이터 전송 (예시로 콘솔에 출력)
    console.log('비밀번호 변경:', passwordData);

    // 비밀번호 변경 후 메시지
    alert('비밀번호가 변경되었습니다.');

    // 실제 서버 요청 코드 추가 가능
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

    // 페이지 이동
    window.location.href = 'seller/sellerSignUp.html';
    
    
}


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
document.getElementById("confirmPassword").addEventListener("input", validateConfirmPassword);