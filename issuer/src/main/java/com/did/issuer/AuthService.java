package com.did.issuer;

import com.did.issuer.domain.AuthDTO;
import com.did.issuer.domain.Identity;
import com.did.issuer.exception.NonExiststentException;
import com.did.issuer.util.RedisUtil;
import kotlinx.serialization.json.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    public final AuthRepository authRepository;
    public final RedisUtil redisUtil;
    public final RestTemplate restTemplate;
    public static int num = 1;

    public Object issuingVc(AuthDTO authDTO) {

        // 인증번호 확인
        if(!verifyAuthNum(authDTO.getPhone(), authDTO.getAuthNum())){
            return "인증번호가 틀렸습니다.";
        }
        // redis에 저장된 정보 삭제
        redisUtil.deleteData(authDTO.getPhone());

        // 가상의 민증 정보를 조회
        Identity identity = authRepository.findByPhone(authDTO.getPhone())
                .orElseThrow(() -> new NonExiststentException("존재하지 않는 인간입니다."));

        // 1. Document 생성
        String did = makeDocument(authDTO.getPublicKey());

        // 2. VC 생성
        JSONObject vc = new JSONObject();
        JSONArray context = new JSONArray();
        context.add("https://www.w3.org/2018/credentials/v1");
        context.add("https://www.w3.org/2018/credentials/examples/v1");
        vc.put("@context",context);

        vc.put("id","http://example.edu/credentials/1872");

        JSONArray type = new JSONArray();
        type.add("VerifiableCredential");
        vc.put("type",type);

        JSONObject credentialSubject = new JSONObject();
        credentialSubject.put("id",did);
        JSONObject alumniOf = new JSONObject();
        alumniOf.put("id", did);
        alumniOf.put("name",authDTO.getName());
        alumniOf.put("phone", authDTO.getPhone());
        credentialSubject.put("alumniOf",alumniOf);
        vc.put("credentialSubject", credentialSubject);

        return vc;

    }

    // Document 생성 및 Blockchain 서버에 전송
    private String makeDocument(String publicKey) {
        JSONObject req = new JSONObject();
        req.put("id","did");

        JSONObject document = new JSONObject();

        JSONArray context = new JSONArray();
        context.add("https://www.w3.org/ns/did/v1");
        context.add("https://w3id.org/security/suites/ed25519-2020/v1");
        document.put("@context",context);

        String uuid = UUID.randomUUID().toString();
        document.put("id","did:example:"+uuid);
        String did = "did:example:"+uuid;

        JSONArray authentication = new JSONArray();
        JSONObject auth_child = new JSONObject();
        auth_child.put("id","did:example:"+uuid);
        auth_child.put("type","Ed25519VerificationKey2020");
        auth_child.put("controller","did:example:"+uuid+"#keys-"+num++);
        auth_child.put("publicKeyMultibase",publicKey);
        authentication.add(auth_child);
        document.put("authentication", authentication);

        JSONArray service = new JSONArray();
        JSONObject ser_child = new JSONObject();
        ser_child.put("id", "did:example:"+uuid+"#vcs");
        ser_child.put("type", "VerifiableCredentialService");
        ser_child.put("serviceEndpoint","https://example.com/vc/");
        service.add(ser_child);
        document.put("service", service);

        JSONObject doc = new JSONObject();
        doc.put("document", document);
        req.put("data", doc);
        restTemplate.postForObject("http://block.platechain.shop/v1/transaction", req, Object.class);
        return did;
    }


    // 블록체인에 hashcode 요청
    private String getHashCode() {
        return restTemplate.getForObject("http://block.platechain.shop/v1/transaction", String.class);

    }

    public Boolean verifyAuthNum(String phone, String authNum){
        String codeFoundByPhone = redisUtil.getData(phone);
        log.info(codeFoundByPhone);
        log.info(authNum);
        if (codeFoundByPhone == null){
            return false;
        }
        return codeFoundByPhone.equals(authNum);
    }


}
