package ba.edu.ibu.finance_tracker.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Image {
    @Id
    private String id;
    private String imgurUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgurUrl() {
        return imgurUrl;
    }

    public void setImgurUrl(String imgurUrl) {
        this.imgurUrl = imgurUrl;
    }

}
