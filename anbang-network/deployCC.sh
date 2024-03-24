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

CC_NAME="anbnag-chaincode"
CC_SRC_PATH="../anbang-chaincode"
CC_VERSION="1.0"
CC_RUNTIME_LANGUAGE="java"

# 체인 코드 준비
setup(){
  rm -rf ${CC_SRC_PATH}/build
   echo "Compiling Java code..."
   pushd $CC_SRC_PATH || exit
   ./gradlew installDist
   popd || exit
   echo "Finished compiling Java code"
}

setup

packageChaincode() {
  rm -rf ${CC_NAME}.tar.gz
  rm -rf log.txt
  CC_SRC_PATH=../anbang-chaincode/build/libs/anbang-chaincode-1.0-SNAPSHOT.jar
   peer lifecycle chaincode package ${CC_NAME}.tar.gz \
   --path ${CC_SRC_PATH} --lang ${CC_RUNTIME_LANGUAGE} \
   --label ${CC_NAME}_${CC_VERSION} >&log.txt
}

packageChaincode

installChaincode() {
    setGlobalsForPeer0Org1
    peer lifecycle chaincode install ${CC_NAME}.tar.gz
    echo "===================== Chaincode is installed on peer0.org1 ===================== "

    setGlobalsForPeer0Org2
    peer lifecycle chaincode install ${CC_NAME}.tar.gz
    echo "===================== Chaincode is installed on peer0.org2 ===================== "

    setGlobalsForPeer0Org3
    peer lifecycle chaincode install ${CC_NAME}.tar.gz
    echo "===================== Chaincode is installed on peer0.org3 ===================== "
}
installChaincode
