package com.kunuz.dto;

import lombok.Builder;

@Builder
public record ExtraShortInfo(
        Integer viewCount,
        Integer shareCount,
        String ipAddress) {
}
