package org.example.anbang_server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RealEstate {

  private String homeID;
  private String owner;
  private String address;
  private long price;

}
