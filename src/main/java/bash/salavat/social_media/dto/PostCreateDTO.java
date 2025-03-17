package bash.salavat.social_media.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class PostCreateDTO {
    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Post cannot exceed 2000 characters")
    private String content;

    @URL(message = "Invalid image URL")
    private String imageUrl;

    // Getters and setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}