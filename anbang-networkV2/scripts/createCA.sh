#!/bin/bash

export PATH=$PATH:../bin:${PWD}:$PATH

function ordererCA(){

    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com
    export FABRIC_CA_CLIENT_HOME=${PWD}/../anbang-ca-server/ordererOrganization/anbang.com
    export FABRIC_CA_CLIENT_TLS_CERTFILES=../tls-cert.pem

    echo
    echo "==================== Enroll the CA admin ===================="
    echo

    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com --tls.certfiles  -u https://admin:adminpw@localhost:7054

    echo 'NodeOUs:
    Enable: true
    ClientOUIdentifier:
        Certificate: cacerts/localhost-7054-ordererOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: client
    PeerOUIdentifier:
        Certificate: cacerts/localhost-7054-ordererOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: peer
    AdminOUIdentifier:
        Certificate: cacerts/localhost-7054-ordererOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: admin
    OrdererOUIdentifier:
        Certificate: cacerts/localhost-7054-ordererOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: orderer' >${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/msp/config.yaml

    echo
    echo "==================== Register orderer1 ===================="
    echo

    fabric-ca-client register -d --caname ordererOrganization.anbang.com --id.name orderer1 --id.secret orderer1pw --id.type orderer -u https://localhost:7054

    echo
    echo "==================== Register orderer2 ===================="
    echo

    fabric-ca-client register -d --caname ordererOrganization.anbang.com --id.name orderer2 --id.secret orderer2pw --id.type orderer -u https://localhost:7054

    echo
    echo "==================== Register orderer3 ===================="
    echo

    fabric-ca-client register -d --caname ordererOrganization.anbang.com --id.name orderer3 --id.secret orderer3pw --id.type orderer -u https://localhost:7054

    echo
    echo "==================== Register ordererAdmin ===================="
    echo

    fabric-ca-client register -d --caname ordererOrganization.anbang.com --id.name ordereradmin --id.secret ordereradminpw --id.type admin --id.attrs "hf.Registrar.Roles=client,hf.Registrar.Attributes=*,hf.Revoker=true,hf.GenCRL=true,admin=true:ecert,abac.init=true:ecert" -u https://localhost:7054

    echo
    echo "==================== Generate orderer1 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/msp
    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/msp --csr.hosts localhost -u https://orderer1:orderer1pw@localhost:7054
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate orderer1 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com -M ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://orderer1:orderer1pw@localhost:7054

    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer1.anbang.com/msp/tlscacerts/tlsca.orderer.anbang.com-cert.pem

    echo
    echo "==================== Generate orderer2 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/msp
    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/msp --csr.hosts localhost -u https://orderer2:orderer2pw@localhost:7054
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate orderer2 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com -M ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://orderer2:orderer2pw@localhost:7054

    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer2.anbang.com/msp/tlscacerts/tlsca.orderer.anbang.com-cert.pem

    echo
    echo "==================== Generate orderer3 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/msp
    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/msp --csr.hosts localhost -u https://orderer3:orderer3pw@localhost:7054
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate orderer3 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com -M ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://orderer3:orderer3pw@localhost:7054

    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/orderers/orderer3.anbang.com/msp/tlscacerts/tlsca.orderer.anbang.com-cert.pem

    echo
    echo "==================== Generate ordererAdmin Msp ===================="
    echo
    
    mkdir -p ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/users/Admin@orderOrganization.anbang.com/msp
    fabric-ca-client enroll -d --caname ordererOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/users/Admin@orderOrganization.anbang.com/msp --csr.hosts localhost -u https://ordereradmin:ordereradminpw@localhost:7054
    cp ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/ordererOrganization/anbang.com/users/Admin@orderOrganization.anbang.com/msp/config.yaml

}

function buyerCA(){

    mkdir -p ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com
    export FABRIC_CA_CLIENT_HOME=${PWD}/../anbang-ca-server/buyerOrganization/anbang.com
    export FABRIC_CA_CLIENT_TLS_CERTFILES=../tls-cert.pem

    echo
    echo "==================== Enroll the CA admin ===================="
    echo

    fabric-ca-client enroll -d --caname buyerOrganization.anbang.com -u https://admin:adminpw@localhost:8054

    echo 'NodeOUs:
    Enable: true
    ClientOUIdentifier:
        Certificate: cacerts/localhost-8054-buyerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: client
    PeerOUIdentifier:
        Certificate: cacerts/localhost-8054-buyerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: peer
    AdminOUIdentifier:
        Certificate: cacerts/localhost-8054-buyerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: admin
    OrdererOUIdentifier:
        Certificate: cacerts/localhost-8054-buyerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: orderer' >${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/msp/config.yaml


    echo
    echo "==================== Register buyer1 ===================="
    echo

    fabric-ca-client register -d --caname buyerOrganization.anbang.com --id.name buyer1 --id.secret buyer1pw --id.type peer -u https://localhost:8054

    echo
    echo "==================== Register buyer2 ===================="
    echo

    fabric-ca-client register -d --caname buyerOrganization.anbang.com --id.name buyer2 --id.secret buyer2pw --id.type peer -u https://localhost:8054

    echo
    echo "==================== Register buyer.user ===================="
    echo

    fabric-ca-client register -d --caname buyerOrganization.anbang.com --id.name buyeruser --id.secret buyeruserpw --id.type client -u https://localhost:8054

    echo
    echo "==================== Register buyer.admin ===================="
    echo

    fabric-ca-client register -d --caname buyerOrganization.anbang.com --id.name buyeradmin --id.secret buyeradminpw --id.type admin -u https://localhost:8054

    echo
    echo "==================== Generate buyer1 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/msp
    fabric-ca-client enroll -d --caname buyerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/msp --csr.hosts localhost -u https://buyer1:buyer1pw@localhost:8054
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate buyer1 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname buyerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://buyer1:buyer1pw@localhost:8054
    
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer1.anbang.com/msp/tlscacerts/tlsca.buyer1.anbang.com-cert.pem

    echo
    echo "==================== Generate buyer2 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/msp
    fabric-ca-client enroll -d --caname buyerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/msp --csr.hosts localhost -u https://buyer2:buyer2pw@localhost:8054
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate buyer2 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname buyerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://buyer2:buyer2pw@localhost:8054
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/buyers/buyer2.anbang.com/msp/tlscacerts/tlsca.buyer2.anbang.com-cert.pem

    echo
    echo "==================== Generate buyer.user Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/users/User@buyerOrganization.anbang.com/msp
    fabric-ca-client enroll -d --caname buyerOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/users/User@buyerOrganization.anbang.com/msp --csr.hosts localhost -u https://buyeruser:buyeruserpw@localhost:8054

    echo
    echo "==================== Generate buyer.admin Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/users/Admin@buyerOrganization.anbang.com/msp
    fabric-ca-client enroll -d --caname buyerOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/users/Admin@buyerOrganization.anbang.com/msp --csr.hosts localhost -u https://buyeradmin:buyeradminpw@localhost:8054
    cp ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/buyerOrganization/anbang.com/users/Admin@buyerOrganization.anbang.com/msp/config.yaml


}

function sellerCA(){

    mkdir -p ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com
    export FABRIC_CA_CLIENT_HOME=${PWD}/../anbang-ca-server/sellerOrganization/anbang.com
    export FABRIC_CA_CLIENT_TLS_CERTFILES=../tls-cert.pem

    echo
    echo "==================== Enroll the CA admin ===================="
    echo

    fabric-ca-client enroll -d --caname sellerOrganization.anbang.com -u https://admin:adminpw@localhost:9054

    echo 'NodeOUs:
    Enable: true
    ClientOUIdentifier:
        Certificate: cacerts/localhost-9054-sellerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: client
    PeerOUIdentifier:
        Certificate: cacerts/localhost-9054-sellerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: peer
    AdminOUIdentifier:
        Certificate: cacerts/localhost-9054-sellerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: admin
    OrdererOUIdentifier:
        Certificate: cacerts/localhost-9054-sellerOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: orderer' >${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/msp/config.yaml


    echo
    echo "==================== Register seller1 ===================="
    echo

    fabric-ca-client register -d --caname sellerOrganization.anbang.com --id.name seller1 --id.secret seller1pw --id.type peer -u https://localhost:9054

    echo
    echo "==================== Register seller2 ===================="
    echo

    fabric-ca-client register -d --caname sellerOrganization.anbang.com --id.name seller2 --id.secret seller2pw --id.type peer -u https://localhost:9054

    echo
    echo "==================== Register seller.user ===================="
    echo

    fabric-ca-client register -d --caname sellerOrganization.anbang.com --id.name selleruser --id.secret selleruserpw --id.type client -u https://localhost:9054

    echo
    echo "==================== Register seller.admin ===================="
    echo

    fabric-ca-client register -d --caname sellerOrganization.anbang.com --id.name selleradmin --id.secret selleradminpw --id.type admin -u https://localhost:9054

    echo
    echo "==================== Generate seller1 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/msp
    fabric-ca-client enroll -d --caname sellerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/msp --csr.hosts localhost -u https://seller1:seller1pw@localhost:9054
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate seller TLS ===================="
    echo

    fabric-ca-client enroll -d --caname sellerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://seller1:seller1pw@localhost:9054
    
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller1.anbang.com/msp/tlscacerts/tlsca.seller1.anbang.com-cert.pem

    echo
    echo "==================== Generate seller2 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/msp
    fabric-ca-client enroll -d --caname sellerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/msp --csr.hosts localhost -u https://seller2:seller2pw@localhost:9054
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate seller2 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname sellerOrganization.anbang.com -M ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://seller2:seller2pw@localhost:9054
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/sellers/seller2.anbang.com/msp/tlscacerts/tlsca.seller2.anbang.com-cert.pem

    echo
    echo "==================== Generate seller.user Msp ===================="

    mkdir -p ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/users/User@sellerOrganization.anbang.com/msp
    fabric-ca-client enroll -d --caname sellerOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/users/User@sellerOrganization.anbang.com/msp --csr.hosts localhost -u https://selleruser:selleruserpw@localhost:9054

    echo
    echo "==================== Generate seller.admin Msp ===================="

    mkdir -p ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/users/Admin@sellerOrganization.anbang.com/msp
    fabric-ca-client enroll -d --caname sellerOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/users/Admin@sellerOrganization.anbang.com/msp --csr.hosts localhost -u https://selleradmin:selleradminpw@localhost:9054
    cp ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/sellerOrganization/anbang.com/users/Admin@sellerOrganization.anbang.com/msp/config.yaml

}

function agentCA(){
    mkdir -p ${PWD}/../anbang-ca-server/agentOrganization/anbang.com
    export FABRIC_CA_CLIENT_HOME=${PWD}/../anbang-ca-server/agentOrganization/anbang.com
    export FABRIC_CA_CLIENT_TLS_CERTFILES=../tls-cert.pem

    echo
    echo "==================== Enroll the CA admin ===================="
    echo

    fabric-ca-client enroll -d --caname agentOrganization.anbang.com -u https://admin:adminpw@localhost:10054

    echo 'NodeOUs:
    Enable: true
    ClientOUIdentifier:
        Certificate: cacerts/localhost-10054-agentOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: client
    PeerOUIdentifier:
        Certificate: cacerts/localhost-10054-agentOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: peer
    AdminOUIdentifier:
        Certificate: cacerts/localhost-10054-agentOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: admin
    OrdererOUIdentifier:
        Certificate: cacerts/localhost-10054-agentOrganization-anbang-com.pem
        OrganizationalUnitIdentifier: orderer' >${PWD}/../anbang-ca-server/agentOrganization/anbang.com/msp/config.yaml

    echo
    echo "==================== Register agent1 ===================="
    echo

    fabric-ca-client register -d --caname agentOrganization.anbang.com --id.name agent1 --id.secret agent1pw --id.type peer -u https://localhost:10054

    echo
    echo "==================== Register agent2 ===================="
    echo

    fabric-ca-client register -d --caname agentOrganization.anbang.com --id.name agent2 --id.secret agent2pw --id.type peer -u https://localhost:10054

    echo
    echo "==================== Register agentuser ===================="
    echo

    fabric-ca-client register -d --caname agentOrganization.anbang.com --id.name agentuser --id.secret agentuserpw --id.type client -u https://localhost:10054

    echo
    echo "==================== Register agentadmin ===================="
    echo

    fabric-ca-client register -d --caname agentOrganization.anbang.com --id.name agentadmin --id.secret agentadminpw --id.type admin -u https://localhost:10054

    echo
    echo "==================== Generate agent1 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/msp
    fabric-ca-client enroll -d --caname agentOrganization.anbang.com -M ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/msp --csr.hosts localhost -u https://agent1:agent1pw@localhost:10054
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate agent1 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname agentOrganization.anbang.com -M ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://agent1:agent1pw@localhost:10054

    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent1.anbang.com/msp/tlscacerts/tlsca.agent1.anbang.com-cert.pem

    echo
    echo "==================== Generate agent2 Msp ===================="
    echo

    mkdir -p ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/msp
    fabric-ca-client enroll -d --caname agentOrganization.anbang.com -M ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/msp --csr.hosts localhost -u https://agent2:agent2pw@localhost:10054
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/msp/config.yaml

    echo
    echo "==================== Generate agent2 TLS ===================="
    echo

    fabric-ca-client enroll -d --caname agentOrganization.anbang.com -M ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls --enrollment.profile tls --csr.hosts localhost -u https://agent2:agent2pw@localhost:10054
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/signcerts/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/server.crt
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/ca.crt
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/keystore/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/server.key

    mkdir -p ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/msp/tlscacerts
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/tls/tlscacerts/* ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/agents/agent2.anbang.com/msp/tlscacerts/tlsca.agent2.anbang.com-cert.pem

    echo
    echo "==================== Generate agentuser Msp ===================="

    mkdir -p ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/users/User@agentOrganization.anbang.com/msp
    fabric-ca-client enroll -d --caname agentOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/users/User@agentOrganization.anbang.com/msp --csr.hosts localhost -u https://agentuser:agentuserpw@localhost:10054

    echo
    echo "==================== Generate agentadmin Msp ===================="

    mkdir -p ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/users/Admin@agentOrganization.anbang.com/msp
    fabric-ca-client enroll -d --caname agentOrganization.anbang.com  -M ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/users/Admin@agentOrganization.anbang.com/msp --csr.hosts localhost -u https://agentadmin:agentadminpw@localhost:10054
    cp ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/msp/config.yaml ${PWD}/../anbang-ca-server/agentOrganization/anbang.com/users/Admin@agentOrganization.anbang.com/msp/config.yaml



}

if [ -n "$1" ]; then
    $1
else
    echo "Usage: $0 <function_name>"
fi