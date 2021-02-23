package com.bsuir.ashabaltas.piris.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientsPagedResponse {
    @JsonProperty("pages_count")
    private int pagesCount;
    @JsonProperty("clients")
    private List<Client> clients;
}
