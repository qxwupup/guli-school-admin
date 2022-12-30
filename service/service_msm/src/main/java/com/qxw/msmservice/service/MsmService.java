package com.qxw.msmservice.service;

import java.util.Map;

public interface MsmService {
    boolean sendCode(Map<String, Object> param, String phone);
}
