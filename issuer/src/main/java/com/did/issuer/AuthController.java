package com.did.issuer;

import com.did.issuer.domain.AuthDTO;
import com.did.issuer.util.RedisUtil;
import com.did.issuer.util.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    public final SmsUtil smsUtil;
    public final AuthService authService;
    public final RedisUtil redisUtil;

    @PostMapping("/issue/vc")
    public ResponseEntity issuingVc(@RequestBody AuthDTO authDTO){
        return ResponseEntity.status(HttpStatus.OK).body(authService.issuingVc(authDTO));
    }

    // 문자 인증
    @PostMapping("/identify")
    public ResponseEntity identify(@RequestBody Map<String, String> req){
        String to = req.get("to");
        String verificationCode = smsUtil.createKey();
        // redis에 인증번호 저장
        redisUtil.setDataExpire(to, verificationCode, 60*30L);
        log.info(redisUtil.getData(to));
//        return ResponseEntity.status(HttpStatus.OK).body(redisUtil.getData(to));
        // 인증번호 문자 전송
        return ResponseEntity.status(HttpStatus.OK).body(smsUtil.sendOne(to, verificationCode));
    }


}
