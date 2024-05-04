#!/bin/bash

function one_line_pem {
    echo "`awk 'NF {sub(/\\n/, ""); printf "%s\\\\\\\n",$0;}' $1`"
}

function json_cco {
    local PP=$(one_line_pem $4)
    local CP=$(one_line_pem $5)
    sed -e "s/\${ORG}/$1/" \
        -e "s/\${POPORT1}/$2/" \
        -e "s/\${POPORT2}/$3/" \
        -e "s/\${CAPORT}/$4/" \
        -e "s#\${PEERPEM}#$PP#" \
        -e "s#\${CAPEM}#$CP#" \
        ../json/cco-template.json
}

ORG=1
POPORT1=7051
POPORT2=8051
CAPORT=7054
PEERPEM=:~/hyperledger-fabric/anbang-network/channel/crypto-config/peerOrganizations/org1.anbang.com/peers/peer0.org1.anbang.com/msp/tlscacerts/tlsca.org1.anbang.com-cert.pem
CAPEM=~/hyperledger-fabric/anbang-network/channel/crypto-config/peerOrganizations/org1.anbang.com/msp/tlscacerts/tlsca.org1.anbang.com-cert.pem

"$(json_cco $ORG $POPORT1 $POPORT2 $CAPORT $PEERPEM $CAPEM )" > ../json/connection-org1.json

ORG=2
POPORT1=9051
POPORT2=10051
CAPORT=8054
PEERPEM=~/hyperledger-fabric/anbang-network/channel/crypto-config/peerOrganizations/org2.anbang.com/peers/peer0.org2.anbang.com/msp/tlscacerts/tlsca.org2.anbang.com-cert.pem
CAPEM=~/hyperledger-fabric/anbang-network/channel/crypto-config/peerOrganizations/org2.anbang.com/msp/tlscacerts/tlsca.org2.anbang.com-cert.pem

"$(json_cco $ORG $POPORT1 $POPORT2 $CAPORT $PEERPEM $CAPEM)" > ../json/connection-org2.json
