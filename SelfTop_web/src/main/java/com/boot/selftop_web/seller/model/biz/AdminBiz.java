package com.boot.selftop_web.seller.model.biz;

import com.boot.selftop_web.seller.model.dto.AdminDto;

public interface AdminBiz {
    public AdminDto login(String id, String password);

}
