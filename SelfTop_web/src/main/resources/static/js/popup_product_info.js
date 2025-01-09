$(document).ready(function() {
    
    fetchReviewCount(productCode);
});

document.addEventListener("DOMContentLoaded", function() {
    const reviewSection = document.getElementById("reviewSection");
    const reviewList = reviewSection.querySelectorAll(".review-body");
    const buttons = document.querySelectorAll(".var button");
    const productDetailButton = document.querySelector(".product-detail");
    const images = document.querySelectorAll(".review-img");

    images.forEach(image => {
        image.addEventListener("click", function () {
            
            const popupOverlay = document.createElement("div");
            popupOverlay.classList.add("popup-overlay");

            const popupImage = document.createElement("img");
            popupImage.src = this.src;
            popupImage.classList.add("popup-image");

            const closeButton = document.createElement("button");
            closeButton.classList.add("popup-close");
            closeButton.innerText = "×";

            
            closeButton.addEventListener("click", function () {
                document.body.removeChild(popupOverlay);
            });

            popupOverlay.addEventListener("click", function (e) {
                if (e.target === popupOverlay) {
                    document.body.removeChild(popupOverlay);
                }
            });

            
            popupOverlay.appendChild(popupImage);
            popupOverlay.appendChild(closeButton);
            document.body.appendChild(popupOverlay);
        });
    });

    // 페이지 로드 시 기본 활성화
    if (productDetailButton) {
        productDetailButton.classList.add("active");
    }

        buttons.forEach(button => {
            button.addEventListener("click", () => {
                buttons.forEach(btn => btn.classList.remove("active"));
                button.classList.add("active");
            });
        });

    if (reviewList.length > 0) {
        // 리뷰가 있을 경우
        const reviewHeight = reviewList.length * 100; 
        reviewSection.style.height = reviewHeight + "px";
    } else {
        // 리뷰가 없을 경우
        reviewSection.style.height = "auto";
    }
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