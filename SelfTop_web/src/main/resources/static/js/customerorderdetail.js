// 모달 제어 스크립트
document.addEventListener("DOMContentLoaded", () => {
const cancelmodal = document.getElementById('cancelmodal');
const cancelopenModal = document.getElementById("cancelorder");
const cancelcloseModalBtn = document.getElementById('cancelcloseModalBtn');
const cancelsubmitModalBtn = document.getElementById('cancelsubmitModalBtn');

const cancelordernum = cancelopenModal.getAttribute("data-ordernum");
const cancelusername = cancelopenModal.getAttribute("data-userid");
const cancelmodalusername = document.querySelector(".username");
const deliveryinfo = document.getElementById("deliveryinfo");

const pricerow= document.querySelectorAll(".pricevalue");

pricerow.forEach(value=>{
	const pricevalue =parseInt(value.dataset.price,10);
	const convertprice =pricevalue.toLocaleString();
	console.log(pricevalue);

	// 금액 업데이트
	
	value.textContent=convertprice + " 원 ";
	
	
});


cancelopenModal.addEventListener('click', () => {
	if (["취소요청","취소완료","취소거절"].includes(deliveryinfo.textContent)){
		alert("이미 취소처리가 진행중인 주문입니다");
		return;
	}
	cancelmodalusername.innerHTML = cancelusername;
    cancelmodal.style.display = 'block';
});

cancelcloseModalBtn.addEventListener('click', () => {
    cancelmodal.style.display = 'none';
});

cancelsubmitModalBtn.addEventListener('click',()=>{
	const reason = document.querySelector(".reason");
	const cancelreason = reason.value;
	if(!cancelreason){
		alert("내용을 입력해주세요.");
	}else{
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
}
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
    if (event.target === cancelmodal) {
        cancelmodal.style.display = 'none';
    }
});
});
// 모달 제어 스크립트
const modal = document.getElementById('modal');
const closeModalBtn = document.getElementById('closeModalBtn');
const openModalBtns = document.querySelectorAll('.openModalBtn'); // 모든 버튼 가져오기

openModalBtns.forEach(button => {
	const textarea = modal.querySelector('textarea[name="content"]');
	const rating = modal.querySelector('input[name="rating"]');
	const reviewcondition =modal.querySelector('input[name="reviewcondition"');
	const star = modal.querySelector('.star_rating');
    button.addEventListener('click', function() { // this로 클릭한 버튼 참조
		const reviewedit = this.getAttribute('data-reviewedit');	
		const product_code_str = parseInt(this.getAttribute('data-product-code'),10);
		if (reviewedit === "true"){
			const reviewlist =JSON.parse(this.getAttribute('data-reviewlist'));
			const selectreview = reviewlist.find(reviewlist => reviewlist.product_code === product_code_str);
			rating.value=selectreview.rating;
			star.querySelectorAll('.star').forEach((starrating,index)=> {
				if (index <= (rating.value-1)) {
					$(starrating).addClass('on');
				} else {
					$(starrating).removeClass('on');
				}
			});
			textarea.value=selectreview.content;
			reviewcondition.value=reviewedit;
			
		}else{
			reviewcondition.value=reviewedit;
			star.querySelectorAll('.star').forEach((starrating,index)=> {
							if (index <= (rating.value-1)) {
								$(starrating).addClass('on');
							} else {
								$(starrating).removeClass('on');
							}
						});
		}
		
		 modal.style.display = 'block';
        // 클릭한 버튼에서 data-product-code 값을 가져오기
       
        // string 값을 int로 변환
        const product_code = parseInt(product_code_str, 10);  // 10진수로 변환

        // 모달 내의 hidden input에 product_code 값 전달
        const productCodeInput = modal.querySelector('input[name="product_code"]');
        productCodeInput.value = product_code;
    });
});

closeModalBtn.addEventListener('click', () => {
	const textarea = modal.querySelector('textarea[name="content"]');
	const rating = modal.querySelector('input[name="rating"]');
	textarea.value = '';
    modal.style.display = 'none';
	rating.value='5';
	
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
	const textarea = modal.querySelector('textarea[name="content"]');
    if (event.target === modal) {
		const rating = modal.querySelector('input[name="rating"]');
        modal.style.display = 'none';
		textarea.value = '';
		rating.value='5';
    }
});

// 이미지 업로드 스크립트
const imageInput = document.getElementById('imageInput');
const uploadButton = document.getElementById('submitModalBtn'); // 업로드 버튼 요소 가져오기

let selectedFile; // 이미지 선택 시 파일 객체를 저장할 변수

imageInput.addEventListener('change', function (event) {
    selectedFile = event.target.files[0];
});

// 업로드 버튼에 클릭 이벤트 핸들러 등록
// uploadButton.addEventListener('click', function () {
//     const formData = new FormData(); // FormData 객체 생성
//     formData.append('file', selectedFile); // 선택된 파일을 FormData에 추가
//     formData.append('rating', document.querySelector('input[name="rating"]').value); // 별점
//     formData.append('product_code', document.querySelector('input[name="product_code"]').value); // 상품 코드
//     formData.append('content', document.querySelector('textarea[name="content"]').value); // 후기 내용
//
//     // AJAX를 이용하여 서버로 파일 전송
//     fetch('/reviewreg', { // 서버에 맞는 URL로 수정
//         method: 'POST',
//         body: formData
//     }).then(response => {
//         if (response.ok) {
//             alert('업로드 성공!');
//             modal.style.display = 'none';
//         } else {
//             alert('업로드 실패. 다시 시도해주세요.');
//         }
//     }).catch(error => {
//         console.error('Error:', error);
//         alert('업로드 중 문제가 발생했습니다.');
//     });
// });

// 별점 기능
$('.star_rating > .star').click(function() {
	const clickedIndex = $(this).index();  // 클릭된 별의 인덱스를 얻음

	// 현재 상태에서 클릭된 별보다 더 높은 별들에 'on' 클래스를 추가하고 그 이하의 별들은 제거
	$(this).parent().children('span').each(function(index) {
		if (index <= clickedIndex) {
			$(this).addClass('on');
		} else {
			$(this).removeClass('on');
		}
	});
	
    // 선택된 별점의 개수 구하기
    let s = $(".form-rate .star_rating .on").length;

    // rating input의 value를 선택된 별점 개수로 변경
    $("input[name='rating']").val(s);  // rating 값을 실제 input에 반영
});
window.onload = function() {
    var deliveryCells = document.querySelectorAll('.productorder_table th.same-delivery');
    for (let i = 1; i < deliveryCells.length; i++) {
        deliveryCells[i].textContent = ''; // 첫 번째를 제외한 모든 셀의 텍스트를 제거
    }
}

// 브라우저 히스토리에서 한 단계 뒤로 이동
function goBack() {
    window.history.back();
}


