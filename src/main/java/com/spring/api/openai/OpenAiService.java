package com.spring.api.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.models.*;
import com.spring.api.openai.dto.OpenAiChatRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {
    @Value("${openai.secret.key}")
    private String openAiKey;

    private OpenAIClient connectOpenAI() {
        return OpenAIOkHttpClient.builder()
                .apiKey(openAiKey)
                .build();
    }

    private ChatModel findModel(String model) {
        return switch (model) {
            case "GPT_4O_LATEST" -> ChatModel.CHATGPT_4O_LATEST;
            case "GPT_4O_MINI" -> ChatModel.GPT_4O_MINI;
            case "GPT_4O" -> ChatModel.GPT_4O;
            default -> throw new IllegalArgumentException("Unknown model: " + model);
        };
    }

    private EmbeddingModel findEmbedModel(String model) {
        return switch (model) {
            case "GPT_EMBED_LARGE" -> EmbeddingModel.TEXT_EMBEDDING_3_LARGE;
            case "GPT_EMBED_SMALL" -> EmbeddingModel.TEXT_EMBEDDING_3_SMALL;
            default -> throw new IllegalArgumentException("Unknown model: " + model);
        };
    }

    public ChatCompletion chat(OpenAiChatRequestDto openAiChatRequestDto) {
        ChatCompletionCreateParams.Builder paramsBuilder = ChatCompletionCreateParams.builder();

        for (OpenAiChatRequestDto.OpenAiMessageDto message : openAiChatRequestDto.getMessages()) {
            paramsBuilder.addMessage(ChatCompletionUserMessageParam.builder()
                    .role(JsonValue.from(message.getRole()))
                    .content(message.getContent())
                    .build());
        }
        ChatModel model = findModel(openAiChatRequestDto.getModel());
        ChatCompletionCreateParams params = paramsBuilder
                .model(model)
                .build();
        return connectOpenAI().chat().completions().create(params);
    }


    public CreateEmbeddingResponse embedding(String input, long dimension) {
        EmbeddingCreateParams params = EmbeddingCreateParams.builder()
                .model(EmbeddingModel.TEXT_EMBEDDING_3_LARGE)
                .dimensions(dimension)
                .input(input)
                .build();
        return connectOpenAI().embeddings().create(params);
    }
}
