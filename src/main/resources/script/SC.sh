#!/bin/bash

echo "채널 생성 시작"

# Step 1: artifacts/channel 디렉토리로 이동
cd ~/capstone-project-anbang/anbang-network/artifacts/channel

# Step 1-2: docker-compose 실행 종료
docker-compose down

sleep 10
echo " "

# Step 2: ./GC.sh cryptogens 실행
./GC.sh cryptogens
echo " "

# Step 3: ./GC.sh configtxgens 실행
./GC.sh configtxgens
echo " "

# Step 4: docker-compose가 존재하는 디렉토리로 이동
cd ../

# Step 5: docker-compose up -d 실행
docker-compose up -d

sleep 20
echo " "

# Step 6: channel 디렉토리로 이동
cd channel

# Step 7: ./CC.sh createChannel 실행
./CC.sh createChannel
echo " "

# Step 8: ./CC.sh joinChannel 실행
./CC.sh joinChannel
echo " "

# Step 9: ./CC.sh updateAnchorPeers 실행
./CC.sh updateAnchorPeers
echo " "
echo "Blockchain network setup completed successfully."

exit 0