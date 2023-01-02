package com.qxw.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.qxw.common.core.config.AliyunConstantProperties;
import com.qxw.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean sendCode(Map<String, Object> param, String phone) {

        try {
            Config config = new com.aliyun.teaopenapi.models.Config()
                    // 必填，您的 AccessKey ID
                    .setAccessKeyId(AliyunConstantProperties.ACCESS_KEY_ID)
                    // 必填，您的 AccessKey Secret
                    .setAccessKeySecret(AliyunConstantProperties.ACCESS_KEY_SECRET);
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";

            Client client = new Client(config);

            // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(AliyunConstantProperties.SIGN_NAME)
                    .setTemplateCode(AliyunConstantProperties.TEMPLATE_CODE)
                    .setPhoneNumbers(phone)
                    .setTemplateParam(JSONObject.toJSONString(param));
            RuntimeOptions runtime = new RuntimeOptions();
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse response = client.sendSmsWithOptions(sendSmsRequest, runtime);
            return 200==response.getStatusCode();
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            Common.assertAsString(error.message);
            return false;
        }

    }
}
