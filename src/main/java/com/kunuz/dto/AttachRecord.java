package com.kunuz.dto;

import lombok.Builder;
import lombok.Getter;


@Builder
public record AttachRecord(
        String id,
        String url
) {
}
