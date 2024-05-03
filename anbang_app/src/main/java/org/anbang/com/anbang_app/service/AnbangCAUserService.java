package org.anbang.com.anbang_app.service;

public interface AnbangCAUserService {
    void enrollAdmin(String adminId, String adminPw, String orgMspId) throws Exception;
    void registerUser(String userId, String affiliation, String orgMspId, String adminId) throws Exception;
}
