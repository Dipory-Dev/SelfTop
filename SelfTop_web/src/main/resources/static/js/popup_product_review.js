 // 모달 제어 스크립트
 const modal = document.getElementById('modal');
 const openModal = document.getElementById('openModalBtn');
 const closeModalBtn = document.getElementById('closeModalBtn');
 
 openModal.addEventListener('click', () => {
     modal.style.display = 'block';
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


const imageInput = document.getElementById('imageInput');
const imageContainer = document.getElementById('imageContainer');
const uploadButton = document.getElementById('submitModalBtn'); // 업로드 버튼 요소 가져오기

let selectedFile; // 이미지 선택 시 파일 객체를 저장할 변수

imageInput.addEventListener('change', function (event) {
  selectedFile = event.target.files[0];
});

// 업로드 버튼에 클릭 이벤트 핸들러 등록
uploadButton.addEventListener('click', function () {

  const formData = new FormData(); // FormData 객체 생성
  formData.append('file', selectedFile); // 선택된 파일을 FormData에 추가
  
  // AJAX를 이용하여 서버로 파일 전송
  fetch('/popup_product_review', { //추후에 매핑 후 url 수정 필요!
    method: 'POST',
    body: formData,
  })
    .then(response => response.json())
    .then(data => {
      // 서버로부터 받은 응답을 처리하는 코드
      console.log(data);
    })
    .catch(error => {

      console.error(error);
    });
});
 

//별점 기능
$('.star_rating > .star').click(function() {
    
    let b = $(this).hasClass("on");

    $(this).parent().children('span').removeClass('on');
    $(this).addClass('on').prevAll('span').addClass('on');

    if(b){
        $(this).removeClass("on");
    }

    let s = $(".form-rate .star_rating .on ").length;

    document.reviewForm.score.value = s;
    
})


