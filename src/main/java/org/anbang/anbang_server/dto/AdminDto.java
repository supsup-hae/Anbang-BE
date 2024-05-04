package org.anbang.anbang_server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@Getter
@ToString
public class AdminDto {

  String adminId;
  String adminPw;
  String affiliation;

}
