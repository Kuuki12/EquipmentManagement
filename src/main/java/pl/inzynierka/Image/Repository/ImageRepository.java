package pl.inzynierka.Image.Repository;

import org.springframework.data.repository.CrudRepository;

import pl.inzynierka.Image.Domain.Image;
import java.lang.String;

public interface ImageRepository extends CrudRepository<Image, Long>{

	Image findByName(String name);
}
