package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.TagDto;

@Repository
public interface TagRepository extends CrudRepository<TagDto, Long> {
    TagDto findByTag(String tag);
}
