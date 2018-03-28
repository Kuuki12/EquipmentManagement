package pl.inzynierka.Image.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.inzynierka.Image.Repository.ImageRepository;
import pl.inzynierka.Image.Domain.Image;

@Controller
public class ImageController {

	@Autowired
    ImageRepository imageDAO;
	
	@ResponseBody
	@RequestMapping(value = "/image/{idImage}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable(value = "idImage") String idImage) throws IOException{
		
		Image image = imageDAO.findByName(idImage);

        return image.getData();
	}
	
}
