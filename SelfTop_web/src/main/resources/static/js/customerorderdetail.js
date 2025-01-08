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

