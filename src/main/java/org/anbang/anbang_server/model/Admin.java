package org.anbang.anbang_server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Admin {
  String adminId;
  String adminPw;
  String affiliation;
}
