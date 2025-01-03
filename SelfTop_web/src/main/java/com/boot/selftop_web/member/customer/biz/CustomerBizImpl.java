package com.boot.selftop_web.member.customer.biz;

import com.boot.selftop_web.member.customer.model.dto.CustomerDto;
import com.boot.selftop_web.member.customer.model.dto.CustomerorderDto;
import com.boot.selftop_web.member.seller.model.dto.SellerDto;
import com.boot.selftop_web.member.seller.model.dto.SellerOrderDto;
import com.boot.selftop_web.member.customer.biz.mapper.CustomerMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerBizImpl implements CustomerBiz {

	@Autowired
	public CustomerMapper mapper;

	@Override
	public CustomerDto memberlogin(String id, String pw) {

		return mapper.memberlogin(id, pw);
	}

	@Override
	public String checkpw(CustomerDto dto) {
		return mapper.checkpw(dto);
	}

	@Override
	public int changepw(CustomerDto dto, String pw) {
		return mapper.changepw(dto, pw);
	}

	@Override
	public int delUser(String id, String email, String pw) {
		return mapper.delUser(id, email, pw);
	}

	@Override
	public boolean idchk(String id) {
		SellerDto res = mapper.idchk(id);
		System.out.println("biz : " + res);
		if (res == null){
			return true;
		} else {
			return false;
		}
	}

	// 구매자 회원가입
	@Override
	public int insertCustomer(CustomerDto dto) {
		return mapper.insertCustomer(dto);
	}

	@Override
	public CustomerDto selectCustomer(int member_no) {
		CustomerDto res = mapper.selectCustomer(member_no);
		return res;
	}

	@Override
	public int changeInfo(String email, String phone, int member_no)
	{
		return mapper.changeInfo(email, phone, member_no);
	}

	@Override
	public CustomerDto findkakao(Map<String, Object> userInfo) {
		// 카카오 사용자 정보를 받아서 DTO로 변환 후 반환
		String id = (String) userInfo.get("id");

		return mapper.findkakao(id);
		// 최초 로그인 시 null 반환으로 kakaoinsert 메서드 실행되도록 구성
	}

	@Override
	public int kakaoinsert(CustomerDto dto) {
		// 카카오 사용자 정보를 받아서 고객 등록
		System.out.println("userinfo 비즈 " + dto);
		return mapper.insertCustomer(dto); // 카카오 사용자 저장
	}

	@Override
	public List<SellerOrderDto> selectcustomerorderlist(int member_no) {

		return mapper.selectcustomerorderlist(member_no);
	}

	@Override
	public List<SellerOrderDto> searchcustomerorderlist(String startdate, String enddate, int member_no) {
		// TODO Auto-generated method stub
		return mapper.searchcustomerorderlist(startdate,enddate,member_no);
	}
	
	
	// 결제 관련 메서드 추가

    /**
     * 결제 요청 메서드
     * @param amount 결제 금액
     * @param orderId 주문 ID
     * @param orderName 주문 이름
     * @return 결제 요청 결과
     */
    /*public String initiatePayment(int amount, String orderId, String orderName) {
        try {
            // TossPayments 결제 API 호출
            TossPayments tossPayments = new TossPayments("test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm");  // Secret Key 설정
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(amount);
            paymentRequest.setOrderId(orderId);
            paymentRequest.setOrderName(orderName);

            // 결제 요청
            tossPayments.requestPayment("카드", paymentRequest);
            return "Payment initiated successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Payment initiation failed: " + e.getMessage();
        }
    }

    /**
     * 결제 성공 후 처리 메서드
     * @param paymentResponse 결제 응답 데이터
     * @return 결제 처리 결과
     */
    /*public String handlePaymentSuccess(PaymentResponse paymentResponse) {
        // 결제 성공 시 처리 로직 (예: DB 업데이트, 사용자에게 알림 등)
        System.out.println("Payment Success: " + paymentResponse);
        return "Payment successful!";
    }

    /**
     * 결제 실패 후 처리 메서드
     * @param errorMessage 결제 실패 메시지
     * @return 결제 실패 처리 결과
     */
    /*public String handlePaymentFailure(String errorMessage) {
        // 결제 실패 시 처리 로직
        System.out.println("Payment Failed: " + errorMessage);
        return "Payment failed: " + errorMessage;
    }*/

	@Override
	public List<SellerOrderDto> customerpurchaselist(int member_no, int order_no) {
		// TODO Auto-generated method stub
		return mapper.customerpurchaselist(member_no, order_no);
	}
}
