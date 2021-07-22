package config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private final Dotenv config;

    public Config() {
        this.config = Dotenv.configure()
                .directory("./")
                .filename(".env")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
    }

    public Dotenv getConfig() {
        return config;
    }
}
