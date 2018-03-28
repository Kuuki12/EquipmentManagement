package pl.inzynierka.Image.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.inzynierka.Image.Domain.Image;
import pl.inzynierka.Image.Repository.ImageRepository;

@Service
public class ImageServiceCommand {

    @Autowired
    ImageRepository imageRepository;

    public void create(Image image){

        imageRepository.save(image);

    }

}
