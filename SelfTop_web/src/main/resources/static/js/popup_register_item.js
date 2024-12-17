 // 모달 제어 스크립트
 const modal = document.getElementById('modal');
 const openModalBtn = document.getElementById('openModalBtn');
 const closeModalBtn = document.getElementById('closeModalBtn');
 
 openModalBtn.addEventListener('click', () => {
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

 //카테고리 선택 별 제품명 선택
 function modelChange(e){
    var model_cpu=[
        "인텔 코어 울트라 9",
        "AMD 라이젠5-6세대"
    ];
    var model_ram=[,];
    var model_mainboard=[,];
    var model_case=[,];
    var model_gpu=[,];
    var model_power=[,];
    var model_ssd=[,];
    var model_hdd=[,];
    var model_cooler=[,];
    var model_none=["제품 카테고리를 선택해주세요."];

    var target = document.getElementById("model-name");

    if(e.value=="none") var d = model_none;
    else if(e.value=="cpu") var d = model_cpu;
    else if(e.value=="ram") var d = model_ram;
    else if(e.value=="mainboard") var d = model_mainboard;
    else if(e.value=="case") var d = model_case;
    else if(e.value=="gpu") var d = model_gpu;
    else if(e.value=="power") var d = model_power;
    else if(e.value=="ssd") var d = model_ssd;
    else if(e.value=="hdd") var d = model_hdd;
    else if(e.value=="cooler") var d = model_cooler;

    target.options.length=0;

    for(x in d){
        var opt = document.createElement("option");
        opt.value= d[x];
        opt.innerHTML = d[x];
        target.appendChild(opt);
    }

 }
