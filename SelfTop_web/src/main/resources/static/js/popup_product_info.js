function moveDetail(){
        
}

function loadReviews(productCode) {
    // 상품 코드 유효성 검사
    if (!productCode || isNaN(productCode)) {
        alert("상품 코드가 유효하지 않습니다.");
        return;
    }

    $.ajax({
        url: `/product/reviews?productCode=${productCode}`, 
        type: 'GET',
        dataType: 'json', 
        success: function(reviews) {
            // HTML로 변환
            let html = '<div><h3>제품 후기</h3><ul>';
            reviews.forEach(review => {
                html += `<li>
                    <div>
                        <img src="${review.reviewImg}" alt="리뷰 이미지" style="width:100px; height:auto;" />
                    </div>
                    <p>리뷰 내용: ${review.content}</p>
                    <p>평점: ${"★".repeat(review.rating)}${"☆".repeat(5 - review.rating)}</p>
                    <p>작성자: ${review.member_no || "익명 사용자"}</p>
                </li>`;
            });
            html += '</ul></div>';
            
            $("#content").html(html);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error(`Ajax 요청 실패: ${textStatus}`, errorThrown);
            alert(`리뷰 데이터를 가져오는 데 실패했습니다. 상태 코드: ${jqXHR.status}`);
        }
    });
}