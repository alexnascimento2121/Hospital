# Hospital API

A API foi desenvolvida pensando em escabilidade horizontal, no estilo stateless API, onde a segurança pode ser feita por token; A arquitetura pode receber implementações de segurança como Spring security, integradas com JWT ou Keycloak;
Também foi versionada o que facilita manutenções e futuras features.

##Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring HATEOAS
- Hibernate / JPA
- H2 Database(ambiente de desenvolvimento/testes)
- JUnit 5
- Mockito
- MockMvc
- Maven
- SLF4J / Logback

---
## Como Executar

# Teste
mvn test

# Build e Test
mvn clean install

# executar
mvn spring-boot:run
```

A aplicação sobe em:
http://localhost:8080

## Banco de Dados

- Banco H2
- Console disponível em:

http://localhost:8080/h2-console

## docs
SWAGGER
http://localhost:8080/swagger-ui/index.html
OPEN API
http://localhost:8080/v3/api-docs
---


1 - Como você escolheria a stack tecnológica para esse projeto?
Em meu caso escolhi tecnologias que estou confortável em usar e sei como proceder corretamente.

2 - Quais critérios usa para definir arquitetura de backend, frontend e mobile?
No backend geralmente busco usar o padrão rest, com algum tipo de design patterns. Neste caso escolhi o de factoring, um dos que mais usei em minha carreira. No frontend,seria o mesmo padrão rest; No Mobile eu não sei dizer pq tenho pouca experiencia valida.

3 - Como garantir qualidade de código na equipe?
Seguindo os princípios Solid e utilizando ferramentas como Sonarlint e IDE como Intellij que ajudam a identificar code smells.

4 - Como você define priorização de tarefas em um sprint?
Sempre busco priorizar tarefas com alta criticidade e que envolve o core business; primeiro verificando o nível de criticidade e possíveis impactos e por fim definindo um prazo de entrega.

5 - Qual sua estratégia para gerenciar integrações com serviços externos?
Implementar Hateoas nos endpoints para que facilite para a equipe externas entender o que pode ser feito. Métodos, variáveis e comentários em inglês(apesar de que não este caso, nesta API).Versionamento de Endpoint e API, para facilitar manutenção em próximas features; Documentacao como o Swagger e OpenAPI e também collections para teste via POSTMAN;

6 - Como você lidaria com falhas em produção?
Partindo do principio que a falha já ocorreu, eu buscaria via logs entender onde ocorre o erro, e voltar para a ultima versão estável, caso seja possível. Caso não seja possível eu faria desabilitaria a função onde a falha ocorreu e depois soltaria um comunicado aos usuários explicando que houve uma falha e que já esta sendo corrigido e colocaria um prazo no comunicado dizendo quando volta. Por fim, buscaria corrigir o erro, e testar cenários antes de subir a correção. Após correção em produção, levaria para próxima reunião de equipe um diagnostico do que ocorreu e pediria atenção nos testes daquela funcionalidade especifica.

7 - Qual abordagem adotaria para CI/CD nessa API?
Primeiro começaria com veria a forma de implementação dos testes unitários,integração e E2E; Por fim implementaria uma pipeline em algum ferramenta especifica como Git Actions ou Jenkins e por fim dependendo do ambiente, faria o deploy automático após merge, e no caso de produção seria manual.
8 - Como você decide entre REST, GraphQL ou outra forma de API?
No meu caso, nunca tive oportunidade de trabalhar com GraphQL; Para mim sempre foi o uso de Rest.

9 - Como avalia desempenho e otimização de APIs?
Em meu caso, avalio se esta seguindo padrões como Solid no desenvolvimento de códigos e se as camadas da arquitetura escolhida esta de acordo com o que esperado. Quanto a métricas de desempenho, nunca tive oportunidade de trabalhar, apesar de conhecer ferramentas de logs como grafana e ferramentas de teste de carga como K6. 

10 - Como você documenta decisões técnicas e garante o conhecimento compartilhado na equipe?
Geralmente decisões técnicas especificas podem ser documentadas tanto no pedido de PULL REQUEST, repassando o que foi realizado e porque foi realizado, quanto podem ser compartilhadas na reuniões diárias conforme metodologia ágil, como Scrum, busca realizar.

