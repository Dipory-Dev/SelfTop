package com.boot.selftop_web.seller.model.biz;

import com.boot.selftop_web.seller.model.dto.AdminDto;
import com.boot.selftop_web.seller.model.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBizImpl implements AdminBiz {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public AdminDto login(String id, String password) {
        System.out.println("biz : " + id);
        System.out.println("biz : " + password);
        return adminMapper.selectAdmin(id, password);
    }
}
