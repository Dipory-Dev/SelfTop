// 모달 제어 스크립트
const modal = document.getElementById('modal');
const closeModalBtn = document.getElementById('closeModalBtn');
const openModalBtns = document.querySelectorAll('.openModalBtn'); // 모든 버튼 가져오기

openModalBtns.forEach(button => {
    button.addEventListener('click', function() { // this로 클릭한 버튼 참조
        modal.style.display = 'block';

        // 클릭한 버튼에서 data-product-code 값을 가져오기
        const product_code_str = this.getAttribute('data-product-code');

        // string 값을 int로 변환
        const product_code = parseInt(product_code_str, 10);  // 10진수로 변환

        // 모달 내의 hidden input에 product_code 값 전달
        const productCodeInput = modal.querySelector('input[name="product_code"]');
        productCodeInput.value = product_code;
    });
});

closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
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
    let b = $(this).hasClass("on");

    $(this).parent().children('span').removeClass('on');
    $(this).addClass('on').prevAll('span').addClass('on');

    if (b) {
        $(this).removeClass("on");
    }

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

