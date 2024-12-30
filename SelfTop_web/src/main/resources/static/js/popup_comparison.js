// 견적 데이터
const tableData = [
    {
        cartName: "견적 1",
        components: {
            cpu: { name: "Intel i5", price: 300000 },
            mainboard: { name: "ASUS B560", price: 150000 },
            memory: { name: "Samsung DDR4 16GB", price: 80000 },
            graphiccard: { name: "NVIDIA RTX 3060", price: 400000 },
            SSD: { name: "Samsung 970 EVO 1TB", price: 150000 },
            HDD: { name: "Seagate 2TB", price: 70000 },
            power: { name: "Corsair 750W", price: 120000 },
            case: { name: "CoolerMaster", price: 50000 },
            cooler: { name: "Noctua NH-D15", price: 100000 }
        }
    },
    {
        cartName: "견적 2",
        components: {
            cpu: { name: "AMD Ryzen 5", price: 350000 },
            mainboard: { name: "MSI B450", price: 130000 },
            memory: { name: "Kingston DDR4 16GB", price: 70000 },
            graphiccard: { name: "AMD RX 6600", price: 350000 },
            SSD: { name: "WD Blue 1TB", price: 120000 },
            HDD: { name: "Toshiba 2TB", price: 60000 },
            power: { name: "Seasonic 650W", price: 110000 },
            case: { name: "NZXT H510", price: 60000 },
            cooler: { name: "CoolerMaster Hyper 212", price: 50000 }
        }
    },
    {
        cartName: "견적 3",
        components: {
            cpu: { name: "Intel i7", price: 500000 },
            mainboard: { name: "Gigabyte Z590", price: 200000 },
            memory: { name: "Corsair DDR4 32GB", price: 200000 },
            graphiccard: { name: "NVIDIA RTX 3080", price: 800000 },
            SSD: { name: "Samsung 980 Pro 2TB", price: 250000 },
            HDD: { name: "Western Digital 4TB", price: 150000 },
            power: { name: "EVGA 850W", price: 150000 },
            case: { name: "Lian Li PC-O11", price: 180000 },
            cooler: { name: "NZXT Kraken X73", price: 250000 }
        }
    }
];

// 테이블 생성 함수
function createTable(data) {
    const { cartName, components } = data;
    const { cpu, mainboard, memory, graphiccard, SSD, HDD, power, case: pcCase, cooler } = components;

    const totalPrice =
        cpu.price +
        mainboard.price +
        memory.price +
        graphiccard.price +
        SSD.price +
        HDD.price +
        power.price +
        pcCase.price +
        cooler.price;

    return `
        <table>
            <thead>
                <tr>
                    <th colspan="3">${cartName}</th>
                </tr>
                <tr>
                    <th>부품</th>
                    <th>이름</th>
                    <th>가격 (원)</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>CPU</td><td>${cpu.name}</td><td>${cpu.price}</td></tr>
                <tr><td>메인보드</td><td>${mainboard.name}</td><td>${mainboard.price}</td></tr>
                <tr><td>메모리</td><td>${memory.name}</td><td>${memory.price}</td></tr>
                <tr><td>그래픽카드</td><td>${graphiccard.name}</td><td>${graphiccard.price}</td></tr>
                <tr><td>SSD</td><td>${SSD.name}</td><td>${SSD.price}</td></tr>
                <tr><td>HDD</td><td>${HDD.name}</td><td>${HDD.price}</td></tr>
                <tr><td>파워</td><td>${power.name}</td><td>${power.price}</td></tr>
                <tr><td>케이스</td><td>${pcCase.name}</td><td>${pcCase.price}</td></tr>
                <tr><td>쿨러</td><td>${cooler.name}</td><td>${cooler.price}</td></tr>
                <tr class="total-row">
                    <td colspan="2" class="total-cell">총액</td>
                    <td>${totalPrice.toLocaleString()} 원</td>
                </tr>
            </tbody>
        </table>
    `;
}

// DOM 요소 참조
const openModalButton = document.getElementById('open-modal');
const closeModalButton = document.getElementById('close-modal');
const compareButton = document.getElementById('compare-button');
const tableContainer = document.getElementById('table-container');
const modal = document.getElementById('compare-modal');

// 모달 열기
openModalButton.addEventListener('click', () => {
    modal.style.display = 'block';
});

// 모달 닫기
closeModalButton.addEventListener('click', () => {
    modal.style.display = 'none';
    tableContainer.innerHTML = ''; // 테이블 초기화
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
    // 클릭된 대상이 모달의 배경인지 확인
    if (event.target === modal) {
        modal.style.display = 'none';
        tableContainer.innerHTML = ''; // 테이블 초기화
    }
});

// 비교하기 버튼 클릭 이벤트
compareButton.addEventListener('click', () => {
    const checkedQuotes = Array.from(document.querySelectorAll('input[type="checkbox"]:checked'));

    if (checkedQuotes.length < 2 || checkedQuotes.length > 3) {
        alert('최소 2개 이상, 최대 3개까지 선택하세요.');
        return;
    }

    tableContainer.innerHTML = ''; // 초기화

    checkedQuotes.forEach(quote => {
        const index = parseInt(quote.value, 10); // 체크된 인덱스
        const tableHTML = createTable(tableData[index]); // 테이블 생성
        tableContainer.innerHTML += tableHTML; // 추가
    });
});
