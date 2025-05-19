package faculdade.mercadopago;

public class AppConstants {
    String conta = "vendedorpos";
    String userId = "17679366";
    String numberApplication = "2122392666603428";

    String user = "TESTUSER698580558";
    String password = "eSUdZt1qGR";

    // Vendedor
    // Usuario:
    // Senha:
    // User ID: 2437985811
    // Access Token: APP_USR-148891022870608-051507-4fbc005ea11bcacc8287dc4d854c00d9-2437985811

    // Comprador
    // Usuario:TESTUSER249718537
    // Senha: KLT4DOZAM6
    // User ID: 2438542661
    // Access Token: APP_USR-8220877649426161-051514-550b528a09b20ca780fc1673219c8765-2438542661

    public static final String ACCESS_TOKEN = "TEST-2122392666603428-051419-0f3013237427ca66260e0281b19708c8-17679366";
    public static final String NOTIFICATION_URL = "https://www.yourdomain.com/ipn";
    public static final String BASEURL_MERCADOPAGO = "https://api.mercadopago.com";
    public static final String GENERATEQRCODEURL_MERCADOPAGO = "/instore/orders/qr/seller/collectors/17679366/pos/pos01/qrs";
    public static final String CONFIRMPAYMENT_MERCADOPAGO = "/v1/payments";
    public static final String GETDATAURL_MERCADOPAGO = "/v1/payments/{payment_id}";

    private AppConstants() {
    }
}
