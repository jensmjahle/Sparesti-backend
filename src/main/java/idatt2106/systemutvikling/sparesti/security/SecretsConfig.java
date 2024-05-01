package idatt2106.systemutvikling.sparesti.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class for the secret used to sign the JWT token and salt the password.
 */
@ConfigurationProperties(prefix = "security.secret")
@Getter
@Setter
@Component
public class SecretsConfig {

    /**
     * The secret that is used to sign the JWT token.
     */
    private String jwt = "DefaultJWTSecret";

    /**
     * The secret that is used to salt the password before hashing it.
     */
    private String salt = "DefaultSALT";

    /**
     * The algorithm that is used to hash the password.
     */
    private String messageDigestAlgorithm = "SHA-256";
}