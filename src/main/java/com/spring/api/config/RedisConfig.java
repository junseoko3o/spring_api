package com.spring.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String hostName;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    @Bean
//    SessionRepositoryCustomizer<RedisSessionRepository> redisSessionRepositoryCustomizer() {
//        return (redisSessionRepository) -> redisSessionRepository
//                .setRedisSessionMapper(new SafeRedisSessionMapper(redisSessionRepository));
//    }
//
//    static class SafeRedisSessionMapper implements BiFunction<String, Map<String, Object>, MapSession> {
//
//        private final RedisSessionMapper delegate = new RedisSessionMapper();
//
//        private final RedisSessionRepository sessionRepository;
//
//        SafeRedisSessionMapper(RedisSessionRepository sessionRepository) {
//            this.sessionRepository = sessionRepository;
//        }
//
//        @Override
//        public MapSession apply(String sessionId, Map<String, Object> map) {
//            try {
//                return this.delegate.apply(sessionId, map);
//            }
//            catch (IllegalStateException ex) {
//                this.sessionRepository.deleteById(sessionId);
//                return null;
//            }
//        }
//    }
}
