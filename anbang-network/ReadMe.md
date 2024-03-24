# 하이퍼레저 패브릭 네트워크 구축 단계 지침서
## 1. 전제 조건
- 도커 설치
- 도커 컴포즈 설치
- bin 디렉토리에 configtxgen, cryptogen, peer 바이너리 파일이 존재해야 함 (hyperledger fabric sample 다운로드시 fabric-samples/bin 디렉토리에 존재함)

## 2. 파일 구조

```
├── ReadMe.md
├── bin
│   ├── configtxgen
│   ├── cryptogen
│   └── peer
├── channel
│   ├── CC.sh
│   ├── GC.sh
│   ├── channel-artifacts
│   └── init-artifacts
├── config
│   ├── base.yaml
│   ├── configtx.yaml
│   ├── core.yaml
│   └── crypto-config.yaml
└── docker-compose.yaml
```
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