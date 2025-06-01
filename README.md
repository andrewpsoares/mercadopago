# 📦 Tech Challenge - Fase 1

Essa aplicação foi construida para entrega do Tech Challenge da fase 1. 

A aplicação contempla a construção de APIs que juntas irão fornecer um sistema de gerenciamento para uma lanchonete.

O sistema possui rotas disponiveis para:

- Cadastro e gerenciamento de usuários
- Cadastro, atualização e exclusão de produtos
- Inclusão de pedidos
- Gestão de pagamentos
- Gerenciamento de status e entrega do pedido


---

## ✅ Tecnologias Utilizadas

- Java 17  
- Spring Boot 


---

## 🚀 Como Executar Localmente

```bash
git clone https://github.com/andrewpsoares/mercadopago.git

```

Acesse a documentação Swagger:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📚 Endpoints e Exemplos

### 👤 Usuário


#### 🔍 Buscar usuário por CPF

**GET** `/usuario?cpf=12345678900`

**Resposta:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "nome": "João Silva",
    "cpf": "12345678900"
  }
}
```


#### ➕ Criar novo usuário

**POST** `/usuario`

**Body:**
```json
{
  "nome": "Maria Oliveira",
  "cpf": "98765432100",
  "email": "mariaoliveira@gmail.com"
}
```

**Resposta:**
```json
{
  "success": true,
  "data": {
    "id": 2,
    "nome": "Maria Oliveira",
    "cpf": "98765432100",
    "email": "mariaoliveira@gmail.com"
  }
}
```

---

### 📦 Produto

#### 🔍 Buscar produto por código

**GET** `/api/produtos/buscar/produto/1`

**Resposta:**
```json
{
  "success": true,
  "data": {
    "codigo": 1,
    "nome": "X-Burger",
    "descricao": "Hambúrguer artesanal",
    "categoria": 10,
    "preco": 19.90,
    "tempopreparo": "00:15:00"
  }
}
```

#### ➕ Cadastrar novo produto

**POST** `/api/produtos`

**Body:**
```json
{
  "nome": "Coca-Cola",
  "descricao": "Refrigerante 350ml",
  "categoria": 10,
  "preco": 5.00,
  "tempopreparo": "00:01:00"
}
```

**Resposta:**
```json
{
  "success": true,
  "data": {
    "codigo": 2,
    "nome": "Coca-Cola",
    "descricao": "Refrigerante 350ml",
    "categoria": 10,
    "preco": 5.00,
    "tempopreparo": "00:01:00"
  }
}
```

---

### 🧾 Pedido

#### 📄 Listar pedidos por status

**GET** `/pedido?status=EM PREPARO`

**Resposta (paginada):**
```json
{
  "content": [
    {
      "codigo": 1,
      "status": "EM PREPARAÇÃO",
      "itens": [
        
      ]
    }
  ],
  "totalPages": 1,
  "totalElements": 1
}
```

#### 🔄 Alterar status do pedido

**PUT** `/pedido/1`

**Body:**
```json
"EM_PREPARO"
```

**Resposta:**
```json
{
  "codigo": 1,
  "status": "EM_PREPARO"
}
```

#### ❌ Remover pedido da fila de preparo

**DELETE** `/pedido/1`

**Resposta:** `204 No Content`

---

### 💳 Pagamento

#### 🧾 Gerar QR Code de pagamento

**POST** `/api/payments`

**Body:**
```json
{
  "pedidoId": 1,
  "valor": 29.90
}
```

**Resposta:**
```json
{
  "success": true,
  "data": {
    "qrData": "000201010212...MP123",
    "orderId": "order_xyz"
  }
}
```

#### ✅ Confirmar pagamento

**POST** `/api/payments/mercadopago/confirmapagamento`

**Body:**
```json
{
  "orderId": "order_xyz",
  "status": "APPROVED"
}
```

**Resposta:**
```json
{
  "success": true,
  "message": "Pagamento confirmado"
}
```

---

### 🛵 Entrega

#### 🚚 Finalizar pedido

**POST** `/entregar`

**Body:**
```json
{
  "pedidoId": 1,
  "nomeEntregador": "Carlos Motoboy"
}
```

**Resposta:**
```json
{
  "success": true,
  "data": {
    "pedidoId": 1,
    "status": "ENTREGUE",
    "entregador": "Carlos Motoboy"
  }
}
```

---

## ✅ Testes Automatizados

Utilize JUnit 5 e Mockito para testar a camada de controller e serviços:

```bash
./mvnw test
```

Exemplo de estrutura de teste:

```java
@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController controller;

    @Test
    void deveBuscarProdutoPorCodigo() throws Exception {
        var produto = ViewProdutoDto.builder().codigo(1).nome("X-Burger").build();
        var response = ApiResponse.ok(produto);

        when(produtoService.buscarProduto(1L)).thenReturn(response);

        var result = controller.buscarPorCodigoProduto(1L);
        assertEquals(200, result.getStatusCodeValue());
    }
}
```

---

## 📌 Observações Finais

- Todas as respostas seguem o padrão de `ApiResponse<T>`.
- A API não possui autenticação implementada (considerar em produção).
- Configure o banco de dados no `application.properties`.