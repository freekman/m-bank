package com.clouway.bricky.http;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Marian Zlatev <mzlatev91@gmail.com>
 */
class AmountDTO {

  @NotNull
  @Min(0)
  final Double amount;

  public AmountDTO(Double amount) {
    this.amount = amount;
  }

}
