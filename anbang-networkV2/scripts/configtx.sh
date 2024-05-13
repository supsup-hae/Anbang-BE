#!/bin/bash
    export PATH=$PATH:../bin:${PWD}:$PATH

    # 이전 블록 파일 삭제
    rm -rf ../init-artifacts/*

    SYS_CHANNEL="sys-channel"
    CHANNEL_NAME="anbang-channel"

    echo $CHANNEL_NAME
    # 시스템 제네시스 블록 생성
    echo "#######    Generating orderer Genesis block  ##########"
    configtxgen -profile AnbangOrdererGenesis -configPath ../config -channelID $SYS_CHANNEL -outputBlock ../init-artifacts/genesis.block
    # 채널 설정 블록 생성
    echo "#######    Generating channel configuration transaction  ##########"
    configtxgen -profile AnbangChannel -configPath ../config -outputCreateChannelTx ../init-artifacts/${CHANNEL_NAME}.tx -channelID $CHANNEL_NAME

    echo "#######    Generating anchor peer update for buyerMSP  ##########"
    configtxgen -profile AnbangChannel -configPath ../config -outputAnchorPeersUpdate ../init-artifacts/buyerMSPanchors.tx -channelID $CHANNEL_NAME -asOrg buyerOrganization

    echo "#######    Generating anchor peer update for sellerMSP  ##########"
    configtxgen -profile AnbangChannel -configPath ../config -outputAnchorPeersUpdate ../init-artifacts/sellerMSPanchors.tx -channelID $CHANNEL_NAME -asOrg sellerOrganization

    echo "#######    Generating anchor peer update for agentMSP  ##########"
    configtxgen -profile AnbangChannel -configPath ../config -outputAnchorPeersUpdate ../init-artifacts/agentMSPanchors.tx -channelID $CHANNEL_NAME -asOrg agentOrganization