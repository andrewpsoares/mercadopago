package faculdade.mercadopago.core.applications.ports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiResponse<T> {
    private boolean Success;
    private T Data;
    private List<Err> Errors = new ArrayList<>();

    public ApiResponse() {}

    public ApiResponse(boolean success, T data) {
        this.Success = success;
        this.Data = data;
    }

    public void addError(String error, String message) {
        var obj = new Err();
        obj.setError(error);
        obj.setMessage(message);
        this.Errors.add(obj);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data);
    }

    public static <T> ApiResponse<T> error(String error, String message) {
        ApiResponse<T> response = new ApiResponse<>(false, null);
        response.addError(error, message);
        return response;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Err {
        @JsonProperty("error")
        private String Error;

        @JsonProperty("message")
        private String Message;
    }
}
