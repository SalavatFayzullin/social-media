package bash.salavat.social_media.repo;

import bash.salavat.social_media.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.createdAt DESC")
    Page<Post> findAllWithAuthors(Pageable pageable);
}