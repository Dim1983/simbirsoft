package org.simbirsoft.domain;

import lombok.Builder;
import lombok.Data;
@Data
@Builder(setterPrefix = "with")
public class Query {
    private final Integer ID;
    private final String link;
    private final String wordAndQuantity;
}
