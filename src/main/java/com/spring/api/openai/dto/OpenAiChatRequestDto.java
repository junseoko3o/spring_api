package com.spring.api.openai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class OpenAiChatRequestDto {
    @NotNull
    private List<OpenAiChatRequestDto.OpenAiMessageDto> messages;
    private String model;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenAiMessageDto {
        private String role;
        private String content;
    }
}
