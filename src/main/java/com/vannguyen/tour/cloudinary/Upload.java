package com.vannguyen.tour.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vannguyen.tour.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class Upload {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dfapum2fd",
            "api_key", "821542417418447",
            "api_secret", "RzD7B0cuTe5EQ06UuK4zQ4yB0kM"));

    public String uploadImageToCloudinary(MultipartFile file) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) result.get("secure_url");

            return imageUrl;
        } catch (Exception e) {
            throw new BadRequestException("Đã xảy ra lỗi khi upload hình ảnh." + e);
        }
    }
}
