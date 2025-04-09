package savorly.backend.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    Se injectează valoarea configurată în fișierul
//    application.properties (sau application.yml) pentru cheia file.upload-dir și se salvează în variabila uploadDir.
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
//     Această metodă permite configurarea modului în care Spring va servi resursele statice (de exemplu, imagini,
//     fișiere CSS, etc.).
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Definirea unui pattern URL /uploads/** pentru a trata cererile de resurse.
        registry.addResourceHandler("/uploads/**")
//                Indică locația reală a fișierelor, care se află în directorul specificat de variabila uploadDir.
                .addResourceLocations("file:" + uploadDir + "/");
    }
}