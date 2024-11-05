package com.example.auth_shop.service.image;

import com.example.auth_shop.dto.ImageDto;
import com.example.auth_shop.exceptions.ResourceNotFoundException;
import com.example.auth_shop.model.Image;
import com.example.auth_shop.model.Product;
import com.example.auth_shop.repository.ImageRepository;
import com.example.auth_shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("Image not found with id: " + id);
        });
    }


    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {

        // Retrieve the product by productId
        Product product = productRepository.getProductById(productId);

        // List to hold the saved image DTOs
        List<ImageDto> savedImageDtos = new ArrayList<>();

        // Iterate over each file in the list of uploaded files
        for (MultipartFile file : files) {
            try {
                // Create a new Image entity
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);                     // Associate the image with the product

                // Create the base download URL for the image
                String baseDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = baseDownloadUrl + image.getId();  // Initial download link
                image.setDownloadUrl(downloadUrl);

                // Save the image to the repository
                Image savedImage = imageRepository.save(image);

                // Update the download URL with the saved image's ID
                savedImage.setDownloadUrl(baseDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);  // Save the image again with the updated URL

                // Create a DTO for the saved image
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());       // Set image ID in the DTO
                imageDto.setImageName(savedImage.getFileName()); // Set the image file name
                imageDto.setDownLoadUrl(savedImage.getDownloadUrl()); // Set the download URL

                // Add the DTO to the list of saved images
                savedImageDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                // Handle exceptions for I/O or SQL errors
                throw new RuntimeException(e.getMessage());
            }
        }

        // Return the list of saved image DTOs
        return savedImageDtos;
    }



    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
