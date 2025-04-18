package com.amol.users_service.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ValidTokenResponse {
    private boolean valid;
}
