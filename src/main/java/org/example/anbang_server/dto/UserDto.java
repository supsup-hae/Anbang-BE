package org.example.anbang_server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@Getter
@ToString
public class UserDto {

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
