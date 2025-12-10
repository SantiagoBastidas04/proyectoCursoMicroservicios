@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Document Management API")
                        .version("3.0")
                        .description("API para gestión de documentos, formatos y anteproyectos"));
    }
}
