[Korean Document](https://github.com/2023-oss/OSS-ISSUER/blob/main/License)
<h1 align="center">🏛Issuer</h1>

<p align="center">
<img src="https://img.shields.io/github/contributors/2023-oss/OSS-ISSUER">
<img src="https://img.shields.io/github/languages/count/2023-oss/OSS-ISSUER">
<img alt="GitHub license" src="https://img.shields.io/github/issues/2023-oss/OSS-ISSUER">
<img alt="GitHub license" src="https://img.shields.io/github/issues-closed/2023-oss/OSS-ISSUER">
<img src="https://img.shields.io/github/license/2023-oss/OSS-ISSUER">
</p>
<br/>
Issue 'VC (Verifiable Credentials)' to holders who have completed self-authentication.


<br/>
 
## Installation and Execution

The following environments are recommended
| service | version |
|---------|---------|
|**SpringBoot**|v2.7.x|
|**Java**|v11|
|**Redis**|v7.2.x|
|**Docker**|v24.0.x|
|CoolSMS|sms_service|

### How To Run
```
$ cd $REPOSITORY/$PROJECT_NAME/
$ ./gradlew build
$ cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
$ java -jar $REPOSITORY/$JAR_NAME
```

## VC(Verifiable Credential) Issuing Process
### step1 - Verification
- 'Holder' requests Issuer to authenticate himself
- 'Issuer' checks Holder's identity
<br/>

### step2 - VC Issuance
- 'Issue a VC' to Holder who succeeded in self-authentication
- Give BlockChain Server the publicKey which passed by Holder
<center>
  <img src="https://user-images.githubusercontent.com/83829352/265966855-2a92728a-cc09-403b-80e0-9ac5c74a335d.png" width=500px />
</center>
