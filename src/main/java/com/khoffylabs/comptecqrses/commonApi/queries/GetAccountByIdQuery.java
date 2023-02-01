package com.khoffylabs.comptecqrses.commonApi.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class GetAccountByIdQuery {
    private String accountId;

}
