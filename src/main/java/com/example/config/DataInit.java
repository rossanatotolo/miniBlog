package com.example.config;

import com.example.model.Post;
import com.example.model.User;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DataInit {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setEmail("HarryPottta@example.com");
            user1.setUsername("Harry");
            user1.setCreatedAt(Instant.now());
            userRepository.save(user1);

            User user2 = new User();
            user2.setEmail("graffMriakula@example.com");
            user2.setUsername("Graff");
            user2.setCreatedAt(Instant.now());
            userRepository.save(user2);

            Post post1 = new Post();
            post1.setTitle("Тот кого нельзя назвать)");
            post1.setContent("Кот у нас последнее время болеет, для профилактики даём ему лекарства, которые ему не " +
                    "нравятся. Теперь в нашей семье говорят: «Поймайте того, кого нельзя назвать», " +
                    "«Найдите того, кого нельзя назвать», «Давайте полечим того, кого нельзя назвать».");
            post1.setAuthor(user1);
            post1.setCreatedAt(Instant.now());
            post1.setUpdatedAt(Instant.now());
            postRepository.save(post1);

            Post post2 = new Post();
            post2.setTitle("Джейсон Айзекс празднует возвращение «сына» Тома Фелтона в «Гарри Поттер»");
            post2.setContent("У Люциуса Малфоя были не самые тёплые отношения с сыном Драко, но реальная динамика " +
                    "отношений Джейсона Айзекса и Тома Фелтона сильно отличается. На прошлой неделе Айзекс испытал " +
                    "чувство гордости как отец, посетив возвращение Фелтона во вселенную Гарри Поттера.");
            post2.setAuthor(user2);
            post2.setCreatedAt(Instant.now());
            post2.setUpdatedAt(Instant.now());
            postRepository.save(post2);

            Post post3 = new Post();
            post3.setTitle("Кодкод: Ровные дырочки на шее добычи. Как маленький дикий котик заставил думать местных " +
                    "жителей, что он вампир.");
            post3.setContent("По интернету гуляет много прикольных видео, где коты воруют вкусняшки раза в три больше " +
                    "себя. Так вот, в жадности своей почти все усатые одинаковые — что дикие, что домашние. Да и " +
                    "покуситься на кусок побольше — дело святое.");
            post3.setAuthor(user1);
            post3.setCreatedAt(Instant.now());
            post3.setUpdatedAt(Instant.now());
            postRepository.save(post3);

            System.out.println("Тестовые данные добавлены!");
        }
    }
}

