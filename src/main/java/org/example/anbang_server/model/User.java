package org.example.anbang_server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

  private String id;
  private String password;
  private String name;
  private String phonenumber;
  private String birth;
  private String registrationDate;
  private String accountBank;
  private String accountNumber;
  private String postNumber;
  private String uuid;
}
