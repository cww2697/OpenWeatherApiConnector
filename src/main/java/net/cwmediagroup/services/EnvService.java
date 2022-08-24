package net.cwmediagroup.services;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvService {

    public static String getEnvValue(String envKey) {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();
        return dotenv.get(envKey);
    }
}
