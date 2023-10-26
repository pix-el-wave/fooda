package inha.capstone.fooda.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AICommunicationUtils {
    private final WebClient aiCommunicationServerWebClient;

    public static final String IMAGE_FOOD_LIST_URI = "/image";

    private <T, R> R sendPostRequestWithBlocking(String uri, T requestBody, ParameterizedTypeReference<R> responseTypeReference) throws WebClientResponseException {
        long startTime = System.currentTimeMillis();
        log.info(uri);
        try {
            return aiCommunicationServerWebClient
                    .post()
                    .uri(uri)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(responseTypeReference)
                    .block();
        } catch (WebClientResponseException e) {
            e.printStackTrace();
            log.error("FastAPI Server Communication Error, API uri: " + uri);
            throw e;
        } finally {
            log.info("Time spent calling the api '" + uri + "' : " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    public AIServerBaseResDto<List<FoodListResDto>> requestImageList(FoodListReqDto requestImageListReqDto) throws WebClientResponseException {
        ParameterizedTypeReference<AIServerBaseResDto<List<FoodListResDto>>> typeReference = new ParameterizedTypeReference<>() {};
        return this.sendPostRequestWithBlocking(IMAGE_FOOD_LIST_URI, requestImageListReqDto, typeReference);
    }
}
