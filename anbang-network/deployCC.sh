#!/bin/bash

export PATH=$PATH:bin:${PWD}:$PATH

export CORE_PEER_TLS_ENABLED=true
export ORDERER_CA=${PWD}/channel/crypto-config/ordererOrganizations/anbang.com/orderers/orderer.anbang.com/msp/tlscacerts/tlsca.anbang.com-cert.pem
export PEER0_ORG1_CA=${PWD}/channel/crypto-config/peerOrganizations/org1.anbang.com/peers/peer0.org1.anbang.com/tls/ca.crt
export PEER0_ORG2_CA=${PWD}/channel/crypto-config/peerOrganizations/org2.anbang.com/peers/peer0.org2.anbang.com/tls/ca.crt
export FABRIC_CFG_PATH=config

# Orderer 설정
setGlobalsForOrderer(){
    export CORE_PEER_LOCALMSPID="OrdererMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$ORDERER_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/channel/crypto-config/ordererOrganizations/anbang.com/users/Admin@anbang.com/msp
}
# Peer 설정
setGlobalsForPeer0Org1(){
    export CORE_PEER_LOCALMSPID="Org1MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG1_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/channel/crypto-config/peerOrganizations/org1.anbang.com/users/Admin@org1.anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:7051
}

setGlobalsForPeer1Org1(){
    export CORE_PEER_LOCALMSPID="Org1MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG1_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/channel/crypto-config/peerOrganizations/org1.anbang.com/users/Admin@org1.anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:8051
}

setGlobalsForPeer0Org2(){
    export CORE_PEER_LOCALMSPID="Org2MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG2_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/channel/crypto-config/peerOrganizations/org2.anbang.com/users/Admin@org2.anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:9051
}

setGlobalsForPeer1Org2(){
    export CORE_PEER_LOCALMSPID="Org2MSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$PEER0_ORG2_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/channel/crypto-config/peerOrganizations/org2.anbang.com/users/Admin@org2.anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:10051
}

# 체인코드 환경 변수 설정
CHANNEL_NAME="anbang-channel"
CC_NAME="anbnag-chaincode"
CC_SRC_PATH="../anbang-chaincode"
CC_VERSION="1.0"
CC_SEQUENCE=1
CC_RUNTIME_LANGUAGE="java"

# 체인 코드 파일 빌드
function setup(){
  rm -rf ${CC_SRC_PATH}/build

   echo "Compiling Java code..."
   pushd $CC_SRC_PATH || exit
   ./gradlew installDist
   popd || exit
   echo "Finished compiling Java code"
}

# 체인코드 패키징
function packageChaincode() {
  rm -rf ${CC_NAME}.tar.gz
  rm -rf log.txt

  CC_SRC_PATH=../anbang-chaincode/build/install/anbang-chaincode

   peer lifecycle chaincode package ${CC_NAME}.tar.gz \
   --path ${CC_SRC_PATH} --lang ${CC_RUNTIME_LANGUAGE} \
   --label ${CC_NAME}_${CC_VERSION} >&log.txt
}

# 체인코드 설치
function installChaincode() {
    setGlobalsForPeer0Org1
    peer lifecycle chaincode install ${CC_NAME}.tar.gz
    echo "===================== Chaincode is installed on peer0.org1 ===================== "

    setGlobalsForPeer0Org2
    peer lifecycle chaincode install ${CC_NAME}.tar.gz
    echo "===================== Chaincode is installed on peer0.org2 ===================== "

}

# 체인코드 패키지 아이디 확인
function queryInstalled() {
    setGlobalsForPeer0Org1
    peer lifecycle chaincode queryinstalled >&log.txt
    cat log.txt
}

# 체인코드 조직1 에서 승인
function approveForMyOrg1() {
    setGlobalsForPeer0Org1
    PACKAGE_ID=$(sed -n "/${CC_NAME}_${VERSION}/{s/^Package ID: //; s/, Label:.*$//; p;}" log.txt)
    echo PackageID is ${PACKAGE_ID}
    peer lifecycle chaincode approveformyorg -o localhost:7050 \
        --ordererTLSHostnameOverride orderer.anbang.com --tls \
        --cafile $ORDERER_CA --channelID $CHANNEL_NAME --name ${CC_NAME} --version ${CC_VERSION} \
        --init-required --package-id ${PACKAGE_ID} \
        --sequence ${CC_SEQUENCE}
    echo "===================== chaincode approved from org 1 ===================== "
}

# 체인코드 승인 확인
function checkCommitReadyness1() {
    setGlobalsForPeer0Org1
    peer lifecycle chaincode checkcommitreadiness \
        --channelID $CHANNEL_NAME --name ${CC_NAME} --version ${CC_VERSION} \
        --sequence ${CC_SEQUENCE} --output json --init-required
    echo "===================== checking commit readyness from org 1 ===================== "
}

# 체인코드 조직2 에서 승인
function approveForMyOrg2() {
    setGlobalsForPeer0Org2
    PACKAGE_ID=$(sed -n "/${CC_NAME}_${VERSION}/{s/^Package ID: //; s/, Label:.*$//; p;}" log.txt)
    echo PackageID is ${PACKAGE_ID}
    peer lifecycle chaincode approveformyorg -o localhost:7050 \
        --ordererTLSHostnameOverride orderer.anbang.com --tls \
        --cafile $ORDERER_CA --channelID $CHANNEL_NAME --name ${CC_NAME} --version ${CC_VERSION} \
        --init-required --package-id ${PACKAGE_ID} \
        --sequence ${CC_SEQUENCE}
    echo "===================== chaincode approved from org 1 ===================== "
}

# 체인코드 승인 확인
function checkCommitReadyness2() {
    setGlobalsForPeer0Org2
    peer lifecycle chaincode checkcommitreadiness \
        --channelID $CHANNEL_NAME --name ${CC_NAME} --version ${CC_VERSION} \
        --sequence ${CC_SEQUENCE} --output json --init-required
    echo "===================== checking commit readyness from org 1 ===================== "
}

#체인 코드 정의
function commitChaincodeDefination() {
    setGlobalsForPeer0Org1
    peer lifecycle chaincode commit -o localhost:7050 --ordererTLSHostnameOverride orderer.anbang.com \
        --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
        --channelID $CHANNEL_NAME --name ${CC_NAME} \
        --peerAddresses localhost:7051 --tlsRootCertFiles $PEER0_ORG1_CA \
        --peerAddresses localhost:9051 --tlsRootCertFiles $PEER0_ORG2_CA \
        --version ${CC_VERSION} --sequence ${CC_SEQUENCE} --init-required

}

# 체인코드 정의 내용 확인
function queryCommitted() {
    setGlobalsForPeer0Org1
    peer lifecycle chaincode querycommitted --channelID $CHANNEL_NAME --name ${CC_NAME}

}

# 체인코드 초기화
function chaincodeInvokeInit() {
    setGlobalsForPeer0Org1
    peer chaincode invoke -o localhost:7050 \
        --ordererTLSHostnameOverride orderer.anbang.com \
        --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
        -C $CHANNEL_NAME -n ${CC_NAME} \
        --peerAddresses localhost:7051 --tlsRootCertFiles $PEER0_ORG1_CA \
        --peerAddresses localhost:9051 --tlsRootCertFiles $PEER0_ORG2_CA \
        --isInit -c '{"Args":[]}'
}


if [ -n "$1" ]; then
    $1
else
    echo "Usage: $0 <function_name>"
fi