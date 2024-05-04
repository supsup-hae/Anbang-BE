package org.anbang.anbang_server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class RealEstateDto {
  private String userId;
  private String homeId;
  private String owner;
  private String address;
  private String price;
}
