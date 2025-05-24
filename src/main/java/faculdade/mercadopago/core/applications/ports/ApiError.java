package faculdade.mercadopago.core.applications.ports;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String Message;
    private String Path;
    private LocalDateTime Timestamp;

    public ApiError(String message, String path) {
        Message = message;
        Path = path;
        Timestamp = LocalDateTime.now();
    }
}
