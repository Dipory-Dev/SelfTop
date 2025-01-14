$(document).ready(function() {
    
    fetchReviewCount(productCode);
});

document.addEventListener("DOMContentLoaded", function() {
    const reviewSection = document.getElementById("reviewSection");
    const reviewList = reviewSection.querySelectorAll(".review-body");
    const buttons = document.querySelectorAll(".var button");
    const productDetailButton = document.querySelector(".product-detail");
    const images = document.querySelectorAll(".review-img");
    const reviewTableBody = reviewSection.querySelector("tbody");
    const reviews = Array.from(reviewTableBody.querySelectorAll("tr"));
    const sortByDateButton = document.querySelector(".sort-bydate");
    const sortByRateButton = document.querySelector(".sort-byrate");
    const reviewSortVar = document.querySelector(".review-sort-var");
    const defaultImageSrc = "/img/review-default-image.png"; // 리뷰에 이미지가 없을 시- 기본이미지


    // 리뷰가 존재하는 경우만 정렬 바 표시
    if (reviews.length > 0) {
        reviewSortVar.style.display = "flex"; 
    } else {
        reviewSortVar.style.display = "none"; 
    }
    
    // 정렬 버튼 관리
    function setActiveButton(button) {
        document.querySelectorAll(".review-sort-var button").forEach(btn => {
            btn.classList.remove("active");
        });
        
        button.classList.add("active");
    }
    
     // 리뷰 렌더링 함수
     function renderSortedReviews(sortedReviews) {
        reviewTableBody.innerHTML = ""; 
        sortedReviews.forEach((review) => {
            if (review instanceof HTMLElement) {
                reviewTableBody.appendChild(review); // 정렬된 리뷰 추가
            }
        });
    }
    
    // 최신순 정렬
    if (sortByDateButton) {
        sortByDateButton.addEventListener("click", () => {
            setActiveButton(sortByDateButton); 
            const sortedByDate = reviews.slice().sort((a, b) => {
                const datePartA = a.querySelector(".date-part");
                const datePartB = b.querySelector(".date-part");

                const dateA = datePartA && datePartA.textContent.trim() !== "1970-01-01"
                    ? new Date(datePartA.textContent.trim())
                    : new Date(0);

                const dateB = datePartB && datePartB.textContent.trim() !== "1970-01-01"
                    ? new Date(datePartB.textContent.trim())
                    : new Date(0);

                return dateB - dateA; // 최신순 정렬
            });

            renderSortedReviews(sortedByDate);
        });
    }

    // 평점순 정렬
    if (sortByRateButton) {
        sortByRateButton.addEventListener("click", () => {
            setActiveButton(sortByRateButton); 
            const sortedByRate = reviews.slice().sort((a, b) => {
                const ratingPartA = a.querySelector(".rating-part span");
                const ratingPartB = b.querySelector(".rating-part span");
                const ratingA = ratingPartA ? parseFloat(ratingPartA.getAttribute("data-rating") || "0") : 0;
                const ratingB = ratingPartB ? parseFloat(ratingPartB.getAttribute("data-rating") || "0") : 0;

                return ratingB - ratingA; // 높은 점수순 정렬
            });

            renderSortedReviews(sortedByRate);
        });
    }

    // 이미지가 없을 경우 기본 이미지로 설정
    // 리뷰 이미지가 없는 경우 기본 이미지를 설정
    images.forEach((image) => {
        // 이미지의 src가 비어 있거나 유효하지 않을 경우 기본 이미지 설정
        if (!image.src || image.src.trim() === "" || image.src.includes("undefined")) {
            image.src = defaultImageSrc;
        }

        // 이미지 클릭 이벤트 추가 (팝업)
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