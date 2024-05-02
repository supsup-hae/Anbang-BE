package org.example.anbang_server.service;


import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.anbang.realestate.transfer.AnbangRealEstateTransfer;
import org.example.anbang_server.dto.RealEstateDto;
import org.example.anbang_server.model.TransactionContext;
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

  @Override
  public ResponseEntity<String> createRealEstate(TransactionContext ctx, RealEstateDto realEstateDto) {
    try {
      TRANSFER.createAnbangRealEstate(ctx, realEstateDto.getHomeID(), realEstateDto.getOwner(),
          realEstateDto.getAddress(),
          realEstateDto.getPrice());
      return new ResponseEntity<>(String.valueOf(UUID.randomUUID()), HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 생성 실패", HttpStatus.BAD_REQUEST);
    }
  }


  @Override
  public ResponseEntity<String> searchRealEstate(TransactionContext ctx, String homeID) {
    try {
      return new ResponseEntity(TRANSFER.readAnbangRealEstate(ctx, homeID), HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 검색 실패", HttpStatus.NO_CONTENT);
    }
  }

  @Override
  public ResponseEntity<String> updateRealEstate(TransactionContext ctx, RealEstateDto realEstateDto) {
    try {
      return new ResponseEntity<>(
          TRANSFER.updateAnbangRealEstate(ctx, realEstateDto.getHomeID(), realEstateDto.getOwner(),
              realEstateDto.getHomeID(), realEstateDto.getPrice()).toString(), HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 업데이트 실패", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<String> transferRealEstate(TransactionContext ctx, String homeID,
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
  public ResponseEntity<String> deleteRealEstate(TransactionContext ctx, String homeID) {
    try {
      TRANSFER.deleteAnbangRealEstate(ctx, homeID);
      return new ResponseEntity<>("매물 삭제 완료", HttpStatus.OK);
    } catch (ChaincodeException e) {
      e.printStackTrace();
      return new ResponseEntity<>("매물 삭제 실패", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<String> queryAllRealEstate(TransactionContext ctx) {
    try {
      return new ResponseEntity<>(TRANSFER.queryAllAnbangRealEstate(ctx), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("원장 정보 검색 실패", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<String> realEstateExists(TransactionContext ctx, String homeID) {
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

  @Override
  public ResponseEntity<String> enrollClient(String userId) {
    if (userId != null) {
      log.info("userId = " + userId);
      new ResponseEntity<>("Id 값 반환에 성공했습니다!", HttpStatus.OK);
    } else {
      new ResponseEntity<>("Id 값 반환에 실패했습니다", HttpStatus.BAD_REQUEST);
    }
    return null;
  }

}
