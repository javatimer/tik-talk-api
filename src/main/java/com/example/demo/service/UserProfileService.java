package com.example.demo.service;

import com.example.demo.dto.PagebleResponse;
import com.example.demo.dto.UserProfileDto;
import com.example.demo.dto.UserProfileMapper;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.github.javafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserProfileService {

    private static final Map<Long, UserProfile> database = new HashMap<>() {{
        // put(161049271L, new UserProfile(161049271L, "iCherniakov", null, 5, "Ivan", "Cherniakov", true, new ArrayList<>(), null, "description hardcoded"));
    }};

    private final List<String> skills = Arrays.asList("Python,Django,SQL,FastAPI,React,SQLAlchemy,MySQL,Oracle,Elasticsearch,Postgresql,Alembic,Docker,JavaScript,Java,Scala,Kotlin,Groovy,TypeScript,HTML,CSS,Angular,REST,ApolloServer,GraphQL,MongoDB,NodeJS,C/C++,Redis,Kafka,Pulsar,RabbitMQ,Git,Kubernetes,VueJS,AWS".split(","));
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final Faker faker = new Faker();

    private final UserProfileRepository repository;
    private final UserProfileMapper mapper;

    public UserProfileService(UserProfileRepository repository, UserProfileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<UserProfileDto> getProfileById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    public PagebleResponse<UserProfileDto> getAll(int page, int size) {
        //return new ArrayList<>(database.values());
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<UserProfile> pageResult = repository.findAll(pageable);
        return new PagebleResponse<>(
                mapper.toDtoList(pageResult.getContent()),
                pageResult.getTotalElements(),
                page,
                size
        );
    }

    public Optional<UserProfileDto> showMyProfile() {
        //return Optional.ofNullable(repository.getOne(128L));
        return Optional.of(repository.getReferenceById(128L)).map(mapper::toDto);
    }

    public Optional<UserProfileDto> patchMyProfile(Map<String, Object> updates) {
        Long id = 128L;

        return repository.findById(id)
                .map(userProfile -> {

                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "firstName" -> userProfile.setFirstName((String) value);

                            case "lastName" -> userProfile.setLastName((String) value);

                            case "description" -> userProfile.setDescription((String) value);

                            case "stack" -> userProfile.setStack((List<String>) value);
                        }
                    });

                    return mapper.toDto(repository.save(userProfile));
                });
    }

    public Optional<UserProfileDto> patchMyAvatar(MultipartFile image) {
        Long id = 161049271L;
        UserProfile userProfile = database.get(id);

        try {
            String uploadDir = "uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) directory.mkdirs();

            String filePath = uploadDir + image.getOriginalFilename();
            //image.transferTo(new File(filePath));
            image.transferTo(File.createTempFile(filePath, ".jpg"));
            userProfile.setAvatarUrl("https://s.dou.ua/img/avatars/80x80_kote_N3LSlWS.jpg");
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }

        return Optional.of(userProfile).map(mapper::toDto);
    }

    public PagebleResponse<UserProfile> searchByFilters(String firstName, String lastName, String stack, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserProfile> result = repository.searchByFilters(firstName, lastName, stack, pageable);

        return new PagebleResponse<>(
                result.getContent(),
                (int) result.getTotalElements(),
                result.getNumber(),
                result.getSize()
        );
    }

    private List<String> generateSkills() {
        Random randomSize = new Random();
        Set<String> selectedSkills = new HashSet<>();
        for (int i = 0; i < randomSize.nextInt(skills.size()); i++) {
            selectedSkills.add(skills.get(randomSize.nextInt(skills.size())));
        }
        return new ArrayList<>(selectedSkills);
    }

    public List<UserProfile> generateFakeUsers(int count) {
        List<UserProfile> users = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            UserProfile user = new UserProfile();

            user.setId(faker.number().randomNumber(5, true));
            user.setAvatarUrl(faker.internet().avatar());
            user.setSubscriptionsAmount(faker.number().numberBetween(0, 10));
            user.setUsername(faker.name().username());
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setActive(faker.bool().bool());
            user.setStack(generateSkills());
            user.setCity(faker.address().cityName());
            user.setDescription(faker.lorem().sentence(faker.number().numberBetween(2, 20)));

            users.add(user);
        }
        return users;
    }

    /*
    public Optional<UserProfile> getProfileById(String id) {
        UserProfile user = new UserProfile();

        user.setId(faker.number().randomNumber(5, true));
        user.setAvatarUrl(faker.internet().avatar());
        user.setSubscriptionsAmount(faker.number().numberBetween(0, 10));
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setActive(faker.bool().bool());
        user.setStack(generateSkills());
        user.setCity(faker.address().cityName());
        user.setDescription(faker.lorem().sentence(faker.number().numberBetween(2, 20)));

        return Optional.of(user);
    }
*/

    /// ////////////////////////////////////////////EXAMPLE/////////////////////////////////////////////////////////////

    public Optional<UserProfile> getById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    public UserProfile create(UserProfile profile) {
        long id = idGenerator.getAndIncrement();
        profile.setId(id);
        database.put(id, profile);
        return profile;
    }

    public Optional<UserProfile> update(Long id, UserProfile updatedProfile) {
        if (database.containsKey(id)) {
            updatedProfile.setId(id);
            database.put(id, updatedProfile);
            return Optional.of(updatedProfile);
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        return database.remove(id) != null;
    }

    public Optional<UserProfile> patch(Long id, Map<String, Object> updates) {
        UserProfile profile = database.get(id);

        if (profile == null) return Optional.empty();

        updates.forEach((key, value) -> {
            switch (key) {
                case "username" -> profile.setUsername((String) value);
                case "avatarUrl" -> profile.setAvatarUrl((String) value);
                case "subscriptionsAmount" -> profile.setSubscriptionsAmount((Integer) value);
            }
        });

        return Optional.of(profile);
    }
}
