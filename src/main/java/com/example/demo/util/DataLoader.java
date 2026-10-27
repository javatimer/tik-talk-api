package com.example.demo.util;

import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserProfileRepository repository;
    private final PasswordEncoder encoder;

    public DataLoader(UserProfileRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        repository.saveAll(List.of(
                new UserProfile(122L, "admin", encoder.encode("admin"), "ADMIN", true, "static/avatars/dimonshev.jpg", 5, "Дмитрий", "Шевченко", List.of("Python", "Django", "FastAPI", "React", "SQLAlchemy", "PostgreSQL", "Alembic", "Docker"), "Киев", "Я python-backend разработчик, люблю писать код и помогать другим!"),
                new UserProfile(123L, "dimonshev", encoder.encode("admin"), "ADMIN", true, "static/avatars/dimonshev.jpg", 5, "Дмитрий", "Шевченко", List.of("Python", "Django", "FastAPI", "React", "SQLAlchemy", "PostgreSQL", "Alembic", "Docker"), "Киев", "Я python-backend разработчик, люблю писать код и помогать другим!"),
                new UserProfile(124L, "michael123", encoder.encode("michael123"), "USER", true, "static/avatars/michael123.jpg", 5, "Миша", "Сергеев", List.of("JavaScript", "Node.js", "Express.js", "React", "MongoDB", "GraphQL", "Apollo Server", "Docker"), "Киев", "Я JS разработчик, всегда на связи!"),
                new UserProfile(125L, "annsam", encoder.encode("annsam"), "USER", true, "static/avatars/annsam.jpg", 4, "Анна", "Самойлова", List.of("Pytest", "autotest", "unittest", "Python"), "Львов", "Я тестировщик, которого не боятся :)"),
                new UserProfile(126L, "alexsmirn", encoder.encode("alex"), "USER", true, "static/avatars/alexsmirn.jpg", 7, "Алексей", "Смирнов", List.of("TypeScript", "Node.js", "Angular"), "Харьков", "Я Angular разработчик, спец по сайтам и фрилансу!"),
                new UserProfile(127L, "tatsiko", encoder.encode("tat123"), "USER", true, "static/avatars/tatsiko.jpg", 1, "Татьяна", "Конькова", List.of("C++", "Qt", "Linux", "CMake", "PostgreSQL", "C#", "Docker"), "Луцк", "Я C++ разработчик, прошел три круга ада и знаю все, что нужно!"),
                new UserProfile(128L, "iCherniakov", encoder.encode("qwerty"), "ADMIN", true, "static/avatars/vanya.jpg", 10, "Иван", "Черняков", List.of("Angular", "JavaScript", "Vue", "TypeScript", "HTML", "CSS", "React"), "", "WebMaster")
        ));
    }
}
