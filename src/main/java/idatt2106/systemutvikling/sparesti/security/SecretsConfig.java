package idatt2106.systemutvikling.sparesti.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for holding the secrets that are used in the application.
 * The secrets are stored in the application.properties file.
 *
 * @ConfigurationProperties is used to make sure that Spring injects the properties into this class.
 * @Getter Lombok annotation to automatically generate getters.
 * @Setter Lombok annotation to automatically generate setters.
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