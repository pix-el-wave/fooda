package inha.capstone.fooda.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI pixelWaveAPI(@Value("${pix-el-wave.application.version}") String appVersion) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("pix-el-wave API")
                                .description("pix-el-wave API 명세서")
                                .version(appVersion)
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Team pix-el-wave GitHub Organization")
                                .url("https://github.com/pix-el-wave")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "access-token",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}