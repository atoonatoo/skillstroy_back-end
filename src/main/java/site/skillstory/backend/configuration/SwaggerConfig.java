package site.skillstory.backend.configuration;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    public Info apiInfo() {
        return new Info()
        .title("Springdoc 테스트")
        .description("Springdoc을 사용한 Swagger UI 테스트")
        .version("1.0.0");
    }
}
