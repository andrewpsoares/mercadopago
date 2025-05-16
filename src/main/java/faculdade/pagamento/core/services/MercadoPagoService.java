package faculdade.pagamento.core.services;

import faculdade.pagamento.AppConstants;
import faculdade.pagamento.core.domain.dto.NewPaymentDto;
import faculdade.pagamento.core.domain.QrOrderRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MercadoPagoService {
    private final WebClient _webClient;
    private final RestTemplate restTemplate = new RestTemplate();

    public MercadoPagoService(WebClient.Builder webClientBuilder) {
        _webClient = webClientBuilder.baseUrl(AppConstants.BASEURL_MERCADOPAGO).build();
    }

    public Mono<String> GenerateQrCode(NewPaymentDto paymentDto) {
//        var request = new QrOrderRequest(String.valueOf(paymentDto.OrderId),"Lanchonete",paymentDto.TotalAmount,AppConstants.NOTIFICATION_URL);
//        var headers = new HttpHeaders();
//
//        headers.setBearerAuth(AppConstants.ACCESS_TOKEN);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        var entity = new HttpEntity<>(request, headers);
//
//        var response = restTemplate.exchange(
//                AppConstants.BASEURL_MERCADOPAGO + AppConstants.GENERATEQRCODEURL_MERCADOPAGO,
//                HttpMethod.POST,
//                entity,
//                String.class,
//                Map.of("user_id", userId, "external_id", externalPosId)
//        )

////        Map<String, Object> payload = new HashMap<>();
////        payload.put("title", "teste");
////        payload.put("unit_price", 20.0);
////        payload.put("quantity", 1);
//
//        Map<String, Object> body = new HashMap<>();
////        body.put("items", new Object[]{ payload });
//        body.put("external_reference", String.valueOf(paymentDto.OrderId));
//        body.put("title", "titulo");
//        body.put("description", "descricao");
//        body.put("notification_url", AppConstants.NOTIFICATION_URL);
//        body.put("total_amount", paymentDto.TotalValue);
//
        var request = new QrOrderRequest(String.valueOf(paymentDto.OrderId),"Lanchonete",paymentDto.TotalAmount,AppConstants.NOTIFICATION_URL);

        return _webClient.post()
                .uri(AppConstants.GENERATEQRCODEURL_MERCADOPAGO)
                .header("Authorization", "Bearer " + AppConstants.ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class); // Aqui você pode criar uma classe para tipar se quiser QrOrderResponse
    }

    public Mono<String> GetPayment(String externalReference) {
        return _webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/payments/search")
                        .queryParam("external_reference", externalReference)
                        .build())
                .header("Authorization", "Bearer " + AppConstants.ACCESS_TOKEN)
                .retrieve()
                .bodyToMono(String.class); // ou use uma classe DTO para mapear
    }
}
