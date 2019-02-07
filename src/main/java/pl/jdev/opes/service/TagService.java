package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jdev.opes.db.dto.TagDto;
import pl.jdev.opes.db.dto.mapper.TagMapper;
import pl.jdev.opes.db.repo.TagRepository;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagMapper mapper;

    /**
     * @param tag
     */
    public void createTag(String tag) throws EntityExistsException {
        Optional<TagDto> dto = Optional.of(tagRepository.findByTag(tag));
        dto.ifPresent(tagDto -> {
            throw new EntityExistsException(String.format("Tag %s already exists!", tagDto.getTag()));
        });
        tagRepository.save(mapper.convertToDto(tag));
    }

    public void removeTag(String tag) {

    }
}