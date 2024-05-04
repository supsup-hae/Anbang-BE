package org.anbang.anbang_server.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.anbang.anbang_server.dto.RealEstateDto;
import org.anbang.anbang_server.service.RealEstateService;
import org.anbang.anbang_server.service.ShellRunner;
import org.anbang.realestate.transfer.AnbangRealEstateTransfer;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RealEstateServiceImpl implements RealEstateService {

  private final static String BUILD_CA_CMD = "sh src/main/resources/script/SC.sh";
  private final static String DEPLOY_CC_CMD = "sh src/main/resources/script/CC.sh";

  private final static AnbangRealEstateTransfer TRANSFER = new AnbangRealEstateTransfer();
  private Context ctx;


  @Override
  public ResponseEntity<String> searchRealEstate(String homeID) {
    try {
      return new ResponseEntity(TRANSFER.readAnbangRealEstate(ctx, homeID), HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 검색 실패", HttpStatus.NO_CONTENT);
    }
  }

  @Override
  public ResponseEntity<String> updateRealEstate(RealEstateDto realEstateDto) {
    try {
      return new ResponseEntity<>(
          TRANSFER.updateAnbangRealEstate(ctx, realEstateDto.getHomeID(), realEstateDto.getOwner(),
              realEstateDto.getHomeID(), Long.parseLong(realEstateDto.getPrice())).toString(), HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 업데이트 실패", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<String> transferRealEstate(String homeID,
      String newOwner) {
    try {
      return new ResponseEntity<>(TRANSFER.transferAnbangRealEstate(ctx, homeID, newOwner),
          HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("소유권 이전 실패", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<String> deleteRealEstate(String homeID) {
    try {
      TRANSFER.deleteAnbangRealEstate(ctx, homeID);
      return new ResponseEntity<>("매물 삭제 완료", HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 삭제 실패", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<String> queryAllRealEstate() {
    try {
      return new ResponseEntity<>(TRANSFER.queryAllAnbangRealEstate(ctx), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("원장 정보 검색 실패", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<String> realEstateExists(String homeID) {
    try {
      if (TRANSFER.anbangRealEstateExists(ctx, homeID)) {
        return new ResponseEntity<>("매물이 존재합니다!", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("매물이 존재하지 않습니다!.", HttpStatus.NOT_FOUND);
      }
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 확인 오류", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public void buildChannelCA() {
    ShellRunner.execCommand(BUILD_CA_CMD);
  }

  @Override
  public void deployChainCode() {
    ShellRunner.execCommand(DEPLOY_CC_CMD);
  }


}
