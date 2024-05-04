package org.example.anbang_server.model;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class TransactionContext extends Context {

  public TransactionContext(ChaincodeStub stub) {
    super(stub);
  }
}
