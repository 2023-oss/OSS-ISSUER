<h1 align="center">🏛Issuer</h1>

<p align="center">
<img src="https://img.shields.io/github/contributors/2023-oss/OSS-BACKEND">
<img src="https://img.shields.io/github/languages/count/2023-oss/BACKEND">
<img alt="GitHub license" src="https://img.shields.io/github/issues/2023-oss/OSS-BACKEND">
<img alt="GitHub license" src="https://img.shields.io/github/issues-closed/2023-oss/OSS-BACKEND">
<img src="https://img.shields.io/github/license/2023-oss/OSS-BACKEND">
</p>
<br/>
본인인증이 완료된 Holder에게 `VC(Verifiable Credential)`를 발급합니다.


<br/>
 
## 설치 및 실행 방법
아래의 환경을 권장합니다.
| service | version |
|---------|---------|
|**SpringBoot**|v2.7.x|
|**Java**|v11|
|**Redis**|v7.2.x|
|**Docker**|v24.0.x|
|CoolSMS|sms_service|

### 실행 방법
```
$ cd $REPOSITORY/$PROJECT_NAME/
$ ./gradlew build
$ cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
$ java -jar $REPOSITORY/$JAR_NAME
```

## VC(Verifiable Credential) 발급 과정
### step1 - 본인 인증
- `Holder`가 Issuer에게 본인 인증 요청
- `Issuer`는 Holder의 신원 정보를 조회
<br/>

### step2 - VC 발급
- 본인인증에 성공한 Holder에게 `VC를 발급`
- Holder가 전달한 publicKey를 블록체인 서버에 전달
<center>
  <img src="https://user-images.githubusercontent.com/83829352/265966855-2a92728a-cc09-403b-80e0-9ac5c74a335d.png" width=500px />
</center>
