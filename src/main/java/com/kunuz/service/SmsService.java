package com.kunuz.service;

import com.kunuz.dto.SmsAuthResponseDto;
import com.kunuz.entity.SmsHistoryEntity;
import com.kunuz.exps.AppBadRequestException;
import com.kunuz.repository.ProfileRepository;
import com.kunuz.repository.SmsHistoryRepository;
import com.kunuz.util.RandomUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class SmsService {
    //        @Value("${eskiz.auth.token}")
//    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MzI0MjM4NTQsImlhdCI6MTcyOTgzMTg1NCwicm9sZSI6InRlc3QiLCJzaWduIjoiMjkyOGI2OTJhNGY3YzBlNmI2MDM2MjZiODVlMDhmNjM5ZWYzM2RjMDBkYzMzMTIzYmFjNjQ4NTRkMGIxOTdlMiIsInN1YiI6Ijg3NzQifQ.fnYmPw-dhkXlj-ui11SOtZBSBcYd-fx9nwxB-Zczl1U";
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;


    public void registration(String phone) {
        int code = RandomUtil.randomInt();
        String message = "<#>kitabu.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi: " + code;
        sendSms(phone, message, code);
    }

    private void sendSms(String phone, String message, int code) {
        Long smsCount = smsHistoryRepository.getSmsCount(phone, LocalDateTime.now().minusMinutes(1), LocalDateTime.now());
        if (smsCount >= 3) {
            throw new AppBadRequestException("Limit reached");
        }

        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setCode(String.valueOf(code));
        entity.setPhone(phone);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setAttemptCount(0);
        smsHistoryRepository.save(entity);
        send(phone, message);
    }

    public void send(String message, String phone) {
        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add("mobile_phone", phone)
                    .add("message", message)
                    .add("from", "4546")
                    .build();

            Request request = new Request.Builder()
                    .url("https://notify.eskiz.uz/api/message/sms/send")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + getToken())
                    .post(requestBody)
                    .build();
            OkHttpClient client = new OkHttpClient();
            Call call = client.newCall(request);

            Response response = call.execute();
            System.out.println(response.body().string());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getToken() {
        try {
            RequestBody body = new FormBody.Builder()
                    .add("email", "mahmudjonfoziljonov1@gmail.com")
                    .add("password", "42FDwG9e4s4GGKb7bIL7kTqJBaQj8Xzl6ZHckL10")
                    .build();

            Request request = new Request.Builder()
                    .url("https://notify.eskiz.uz/api/auth/login")
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Call call = client.newCall(request);

            Response response = call.execute();
            System.out.println(response.body().string());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SmsAuthResponseDto dto = new SmsAuthResponseDto();
        dto.getData().getToken();
        return dto.getData().getToken();
    }

    public boolean check(String phone, String code) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            return false;
        }
        // 3. check code is correct
        SmsHistoryEntity smsHistoryEntity = optional.get();
        if (!smsHistoryEntity.getCode().equals(code)) {
            smsHistoryRepository.increaseAttemptCount(smsHistoryEntity.getId());
            return false;
        }
        // 4. sms expiredTime
        LocalDateTime exp = LocalDateTime.now().minusMinutes(1);
        if (exp.isAfter(smsHistoryEntity.getCreatedDate())) {
            return false;
        }
        if (smsHistoryEntity.getAttemptCount() >= 3) {
            return false;
        }
        return true;
    }
}
