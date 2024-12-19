package com.boot.selftop_web.member.admin.biz;

import com.boot.selftop_web.member.admin.model.dto.AdminDto;

public interface AdminBiz {
    public AdminDto login(String id, String password);

}
