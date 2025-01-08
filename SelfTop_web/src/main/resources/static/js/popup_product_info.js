$(document).ready(function() {
    // 페이지 로드와 동시에 리뷰개수 표시
    fetchReviewCount(productCode);
});

// 리뷰 개수 가져오는 함수
function fetchReviewCount(productCode) {
    if (!productCode || isNaN(productCode)) {
        alert("상품 코드가 유효하지 않습니다.");
        return;
    }

    $.ajax({
        url: `/product/reviews?productCode=${productCode}`, 
        type: 'GET',
        dataType: 'json', 
        success: function(reviews) {
            const reviewCount = reviews.length;
            $("#reviewCountSpan").text(reviewCount);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error(`Ajax 요청 실패: ${textStatus}`, errorThrown);
            alert(`리뷰 개수를 가져오는 데 실패했습니다. 상태 코드: ${jqXHR.status}`);
        }
    });
}


// 제품 상세정보 복원 & 리뷰 섹션 숨김 함수
function showProductDetail() {
    $("#reviewSection").removeClass("show").hide();
    $("#productDetails").addClass("show").show();
}

// 리뷰 섹션 표시 & 제품 상세정보 숨김 함수
function showReviews() {
    $("#productDetails").removeClass("show").hide();
    $("#reviewSection").addClass("show").show();
}

// 리뷰 섹션 토글 함수
function toggleReviews() {
    $("#reviewSection").toggle();
}