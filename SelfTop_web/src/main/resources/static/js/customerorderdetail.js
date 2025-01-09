// 모달 제어 스크립트
document.addEventListener("DOMContentLoaded", () => {
const modal = document.getElementById('modal');
const openModal = document.getElementById("cancelorder");
const closeModalBtn = document.getElementById('closeModalBtn');
const submitModalBtn = document.getElementById('submitModalBtn');

const cancelordernum = openModal.getAttribute("data-ordernum");
const cancelusername = openModal.getAttribute("data-userid");
const cancelmodalusername = document.querySelector(".username");
openModal.addEventListener('click', () => {
	cancelmodalusername.innerHTML = cancelusername;
    modal.style.display = 'block';
});

closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
});

submitModalBtn.addEventListener('click',()=>{
	const reason = document.querySelector(".reason");
	const cancelreason = reason.value;
	fetch('/cancelorder', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ordernum :cancelordernum,cancelreason : cancelreason } )
		})
		.then((response) => {
			if (!response.ok) {
				// HTTP 상태 코드가 200~299가 아닐 경우 오류 처리
				return response.text().then((message) => {
					throw new Error(message);
				});
			}
			return response.text();
		})
		.then((result) => {
			alert("취소처리가 완료되었습니다");
			modal.style.display = 'none';
			location.reload(true);
		})
		.catch((error) => {
			console.error('처리 중 오류가 발생하였습니다:', error.message);
			alert('취소 요청에 실패하였습니다: ' + error.message);
		});
	
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});
});
