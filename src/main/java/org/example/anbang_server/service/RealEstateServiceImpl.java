package org.example.anbang_server.service;


import java.util.Map;
import java.util.UUID;
import org.anbang.realestate.transfer.AnbangRealEstateTransfer;
import org.example.anbang_server.model.RealEstate;
import org.example.anbang_server.model.TransactionContext;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RealEstateServiceImpl implements RealEstateService {

  private final static ShellRunner SHELL_RUNNER = new ShellRunner();
  private final static String[] CALL_CMD = {"/bin/bash", "-c",
      "sh src/main/resources/script/SC.sh"};
  private final static AnbangRealEstateTransfer TRANSFER = new AnbangRealEstateTransfer();

  @Override
  public ResponseEntity<String> createRealEstate(TransactionContext ctx, RealEstate realEstate) {
    try {
      TRANSFER.createAnbangRealEstate(ctx, realEstate.getHomeID(), realEstate.getOwner(),
          realEstate.getHomeID(),
          realEstate.getPrice());
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
  public ResponseEntity<String> updateRealEstate(TransactionContext ctx, RealEstate realEstate) {
    try {
      return new ResponseEntity<>(
          TRANSFER.updateAnbangRealEstate(ctx, realEstate.getHomeID(), realEstate.getOwner(),
              realEstate.getHomeID(), realEstate.getPrice()).toString(), HttpStatus.OK);
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
  @Transaction
  public void buildChannelCA() {
    Map<Integer, String> map = SHELL_RUNNER.execCommand(CALL_CMD);
    System.out.println(map);
  }
}
