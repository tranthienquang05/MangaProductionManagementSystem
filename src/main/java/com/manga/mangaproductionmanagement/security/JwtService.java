package com.manga.mangaproductionmanagement.security;

import com.manga.mangaproductionmanagement.entity.User;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import org.springframework.stereotype.Service;

/**
 * Minimal token generator for development/demo only.
 * Produces a simple Base64-encoded string containing userId:email:timestamp.
 * Do NOT use in production. Replace with real JWT implementation.
 */
@Service
public class JwtService {

    public String generateToken(User user) {
        String payload = user.getId() + ":" + user.getEmail() + ":" + Instant.now().toEpochMilli();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
    }

}
