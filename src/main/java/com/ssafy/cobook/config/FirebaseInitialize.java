package com.ssafy.cobook.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FirebaseInitialize {
    @PostConstruct
    public void initialize() throws IOException {
        ClassPathResource resource = new ClassPathResource("TestKey.json");
        if (!resource.exists()) {
            throw new IllegalArgumentException();
        }
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                .setDatabaseUrl("https://cobook-24f25.firebaseio.com/")
//                .setDatabaseUrl("https://co-book-original.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);
    }
}
