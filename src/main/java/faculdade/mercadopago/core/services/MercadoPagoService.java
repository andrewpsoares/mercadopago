package faculdade.mercadopago.core.services;

import faculdade.mercadopago.AppConstants;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MercadoPagoService<T> {
//    private final RestTemplate _restTemplate;
    private final RestTemplate _restTemplate = new RestTemplate();

//    public MercadoPagoService(RestTemplate restTemplate) {
//        _restTemplate = restTemplate;
//    }

    public <T, R> ResponseEntity<?> SendRequest(String url, HttpMethod method, T request, Class<R> responseType, Map<String, String> extraHeaders) {
        try {
            var headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + AppConstants.ACCESS_TOKEN);
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (extraHeaders != null) {
                extraHeaders.forEach(headers::set);
            }

            var entity = method == HttpMethod.GET || method == HttpMethod.DELETE
                    ? new HttpEntity<>(headers)
                    : new HttpEntity<>(request, headers);

            return _restTemplate.exchange(url, method, entity, responseType);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + ex.getMessage());
        }
    }

    public <R> ResponseEntity<?> SendRequest(
            String url,
            HttpMethod method,
            Class<R> responseType
    ) {
        return SendRequest(url, method, null, responseType, null);
    }
}
