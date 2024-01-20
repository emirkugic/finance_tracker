package ba.edu.ibu.finance_tracker.core.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ba.edu.ibu.finance_tracker.core.model.Image;
import ba.edu.ibu.finance_tracker.core.model.User;
import ba.edu.ibu.finance_tracker.core.repository.ImageRepository;
import ba.edu.ibu.finance_tracker.core.repository.UserRepository;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Service
public class ImageService {

    private String clientId = "ab0ad10e479e687";

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public String uploadImage(MultipartFile file, String userId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Client-ID " + clientId);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getBytes());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.imgur.com/3/image", requestEntity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to upload image to Imgur");
        }

        JSONObject jsonResponse = new JSONObject(response.getBody());
        String imgUrl = jsonResponse.getJSONObject("data").getString("link");

        Image image = new Image();
        image.setImgurUrl(imgUrl);

        User user = userRepository.getById(userId);
        user.setProfilePictureUrl(imgUrl);
        userRepository.save(user);

        imageRepository.save(image);

        return imgUrl;
    }
}
