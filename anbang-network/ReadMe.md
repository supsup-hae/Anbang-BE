# 하이퍼레저 패브릭 네트워크 구축 단계 지침서
## 1. 전제 조건
git 설치

- `sudo apt-get install git`

docker 설치

- `sudo apt-get -y install docker-compose`

cURL 설치

- `sudo apt-get install curl`

hyperledger fabric install.sh 설치

- `curl -sSLO https://raw.githubusercontent.com/hyperledger/fabric/main/scripts/install-fabric.sh && chmod +x install-fabric.sh`

hyperledger fabric binary 설치

- `./install-fabric.sh b`

binary 파일 이동

- `mv bin anbang-server/anbang-network/channel`

---

## 2. 파일 구조

```
├── ReadMe.md
├── bin
│   ├── configtxgen
│   ├── cryptogen
│   └── peer
├── channel
│   ├── CC.sh
│   ├── GC.sh
│   ├── channel-artifacts
│   └── init-artifacts
├── config
│   ├── base.yaml
│   ├── configtx.yaml
│   ├── core.yaml
│   └── crypto-config.yaml
├── deployCC.sh
└── docker-compose.yaml

```
---

## 3. 네트워크 구축 단계
1. 실행 파일이 있는 폴더로 이동. 
- `cd channel`
2. 인증서 생성
- `./GC.sh cryptogens`
3. 초기 채널 생성
- `./GC.sh configtxgens` 
4. docker-compose file이 있는 anbang-network 폴더로 이동 
- `cd ../`
5. 모든 서비스 실행 
- `docker-compose up -d` 
6. 실행 스크립트가 있는 channel 디렉토리로 이동 
- `cd channel`
7. 채널 생성
- `./CC.sh createChannel` 
8. 채널 가입
- `./CC.sh joinChannel`
9. 앵커 피어 업데이트
- `./CC.sh updateAnchorPeers`

---

## 4. 체인코드 배포 단계
1. 체인코드 배포 스크립트가 있는 폴더로 이동
- `cd chaincode`
2. 체인코드 설치를 위한 준비
- `./deployCC.sh setup`
3. 체인코드 패키징
- `./deployCC.sh packageChaincode`
4. 체인코드 설치
- `./deployCC.sh installChaincode`
5. 체인코드 패키지 아이디 확인
- `./deployCC.sh queryInstalled`
6. 체인코드 조직1 승인
- `./deployCC.sh approveForMyOrg1`
7. 체인코드 승인 확인
- `./deployCC.sh checkCommitReadiness1`
8. 체인코드 조직1 승인
- `./deployCC.sh approveForMyOrg2`
9. 체인코드 승인 확인
- `./deployCC.sh checkCommitReadiness2`
10. 체인코드 정의
- `./deployCC.sh commitChaincodeDefinition`
11. 체인코드 정의 내용 확인
- `./deployCC.sh queryCommitted`
12. 체인코드 초기화
- `./deployCC.sh chaincodeInvokeInit`

>[참고] 서버 사용시
> - cryptoconfig.yaml 파일의 SANS 주소를 서버의 주소로 변경해야 합니다.
> - CC.sh 파일의 corepeer 주소를 서버의 주소로 변경해야 합니다.
> - CC.sh 파일에 작성된 localhost를 서버의 주소로 변경해야 합니다.
> - deployCC.sh 파일의 corepeer 주소를 서버의 주소로 변경해야 합니다.
> - deployCC.sh 파일에 작성된 localhost를 서버의 주소로 변경해야 합니다.
> - docker-compose.yaml 파일의 `CORE_VM_DOCKER_HOSTCONFIG_NETWORKMODE`을 자신이 사용중인 docker 네트워크로 적절하게 변경해야 합니다.
> - docker-compose.yaml 파일에 작성된 호스트 포트를 적절하게 열어주어야 합니다.
