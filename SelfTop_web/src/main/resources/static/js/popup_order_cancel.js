 // 모달 제어 스크립트
 const modal = document.getElementById('modal');
 const openModal = document.getElementById('openModal');
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

