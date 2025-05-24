//package faculdade.mercadopago.adapter.driver;
//
//import faculdade.mercadopago.core.domain.enums.StatusPedidoEnum;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice(assignableTypes = {PedidoController.class})
//public class PedidoExceptionHandler {
//
//    String statusList = "( " + Arrays.stream(StatusPedidoEnum.values())
//            .map(Enum::name)
//            .collect(Collectors.joining(" | ")) + " )";
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity exceptionHandlerHttpMessageNotReadableException(){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("" +
//                "{\n" +
//                "    \"mensagem\": \"status inválido.\",\n" +
//                "    \"required\": \""+ statusList + "\"\n" +
//                "}");
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public  ResponseEntity exceptionHandlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
//        String campo = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .findFirst()
//                .map(FieldError::getField)
//                .orElse("Campo desconhecido");
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
//                ("{\n" +
//                        "    \"mensagem\": \"Campo obrigatório: \\" + campo + "\\\n" +
//                        "}");
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity exceptionHandlerEntityNotFoundException(EntityNotFoundException ex, WebRequest request){
//        String requestUri = request.getDescription(false);
//        String id = requestUri.substring(requestUri.lastIndexOf("/") + 1);
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
//                ("{\n" +
//                        "    \"mensagem\": \"Nenhum registro encontrado com o código " + id + "\n" +
//                        "}");
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity exceptionHandlerMethodArgumentTypeMismatchException(){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
//                ("{\n" +
//                        "    \"mensagem\": \"Parâmetro inválido\",\n" +
//                        "    \"required\": \"" + statusList + "\"\n" +
//                        "}");
//    }
//}
