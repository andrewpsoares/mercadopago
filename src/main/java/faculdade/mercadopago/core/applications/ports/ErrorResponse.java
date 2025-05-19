package faculdade.mercadopago.core.applications.ports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    private String tipo;
    private String msg;

    public static ErrorResponse of(String tipo, String msg) {
        return new ErrorResponse(tipo, msg);
    }
}
