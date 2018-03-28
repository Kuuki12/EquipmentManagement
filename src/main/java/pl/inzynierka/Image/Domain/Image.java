package pl.inzynierka.Image.Domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idImage;
	private String name;
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;
	private String description;

	public Image() {
	}

	public Image(byte[] data) {
		this.name = UUID.randomUUID().toString();
		this.data = data;
	}
	public Long getIdImage() {
		return idImage;
	}
	public void setIdImage(Long idImage) {
		this.idImage = idImage;
	}
	public String getName() {
		return "/image/"+name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
