#!/bin/bash

export PATH=$PATH:../bin:${PWD}:$PATH

export CORE_PEER_TLS_ENABLED=true
export ORDERER_CA=${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/ca.crt
export BUYER1_CA=${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/sellers/buyer1.anbang.com/tls/ca.crt
export BUYER2_CA=${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/sellers/buyer2.anbang.com/tls/ca.crt
export SELLER1_CA=${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/buyers/seller1.anbang.com/tls/ca.crt
export SELLER2_CA=${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/buyers/seller2.anbang.com/tls/ca.crt
export AGENT1_CA=${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/ca.crt
export AGENT2_CA=${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/ca.crt
export FABRIC_CFG_PATH=../config/

export CHANNEL_NAME="anbang-channel"

# Orderer 설정
setGlobalsForOrderer(){
    export CORE_PEER_LOCALMSPID="ordererMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$ORDERER_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/users/Admin@orderOrganization.anbang.com/msp
}
# Peer 설정
setGlobalsForBuyer1(){
    export CORE_PEER_LOCALMSPID="buyerMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$BUYER1_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/users/Admin@buyerOrganization.anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:7051
}

setGlobalsForBuyer2(){
    export CORE_PEER_LOCALMSPID="buyerMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$BUYER2_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:8051
}

setGlobalsForSeller1(){
    export CORE_PEER_LOCALMSPID="sellerMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$SELLER1_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:9051
}

setGlobalsForSeller2(){
    export CORE_PEER_LOCALMSPID="sellerMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$SELLER2_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:10051
}

setGlobalsForAgent1(){
    export CORE_PEER_LOCALMSPID="agentMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$AGENT1_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/../anbang-ca-server/agnetOrganization/anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:11051
}

setGlobalsForAgent2(){
    export CORE_PEER_LOCALMSPID="agentMSP"
    export CORE_PEER_TLS_ROOTCERT_FILE=$AGENT2_CA
    export CORE_PEER_MSPCONFIGPATH=${PWD}/../anbang-ca-server/agentOrganization/anbang.com/msp
    export CORE_PEER_ADDRESS=localhost:12051
}

# 채널 생성
function createChannel(){
    # 이전 채널 블록 삭제
    rm -rf ../channel/artifacts/*
    
    setGlobalsForBuyer1

    peer channel create -o localhost:7050 -c $CHANNEL_NAME \
    --ordererTLSHostnameOverride localhost \
    -f ../init-artifacts/${CHANNEL_NAME}.tx --outputBlock ../channel/artifacts/${CHANNEL_NAME}.block \
    --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA
}
# 채널 가입
function joinChannel(){
    setGlobalsForPeer0Org1
    peer channel join -b ./channel/artifacts/$CHANNEL_NAME.block

    setGlobalsForPeer1Org1
    peer channel join -b ./channel/artifacts/$CHANNEL_NAME.block

    setGlobalsForPeer0Org2
    peer channel join -b ./channel/artifacts/$CHANNEL_NAME.block

    setGlobalsForPeer1Org2
    peer channel join -b ./channel/artifacts/$CHANNEL_NAME.block

}
# 앵커 피어 업데이트
function updateAnchorPeers(){
    setGlobalsForPeer0Org1
    peer channel update -o localhost:7050 \
    --ordererTLSHostnameOverride localhost \
    -c $CHANNEL_NAME -f ./init/artifacts/${CORE_PEER_LOCALMSPID}anchors.tx \
    --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA

    setGlobalsForPeer0Org2
    peer channel update -o localhost:7050 \
    --ordererTLSHostnameOverride localhost \
    -c $CHANNEL_NAME -f ./init/artifacts/${CORE_PEER_LOCALMSPID}anchors.tx \
    --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA

}

if [ -n "$1" ]; then
    $1
else
    echo "Usage: $0 <function_name>"
fi