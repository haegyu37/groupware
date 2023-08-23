package com.groupware.wimir.service;

import com.groupware.wimir.entity.Profile;
import com.groupware.wimir.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    @Value("${ProfileLocation}")
    private String profileLocation;

    private final ProfileRepository profileRepository;
    private final FileService fileService;

//    public void saveProfile(Profile profile, String base64Image) throws Exception {
//        String oriName = base64Image.getOriginalFilename();
//        String imgName = "";
//        String imgUrl = "";
//
//        //파일 업로드
//        if(!StringUtils.isEmpty(oriName)){
//            imgName = fileService.uploadFile(profileLocation, oriName, base64Image.getBytes());
//            imgUrl = "/member/profile/" + imgName;
//        }
//
//        //상품 이미지 정보 저장
//        profile.updateProfile(oriName, imgName, imgUrl);
//        profileRepository.save(profile);
//    }

    public void saveProfile(Profile profile, String base64Image) throws Exception {
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(base64Image)) {
            // base64 형태로 저장
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            imgName = fileService.uploadFile(profileLocation, "image.jpg", imageBytes);
            imgUrl = "/member/profile/" + imgName;
        }

        // 상품 이미지 정보 저장
        profile.updateProfile("image.jpg", imgName, imgUrl);
        profileRepository.save(profile);
    }


//    public void saveProfile(Profile profile, String base64Image) throws Exception {
//        String oriName = base64Image.get
//        String imgName = "";
//        String imgUrl = "";
//
//        if (!StringUtils.isEmpty(base64Image)) {
//            try {
//                // base64 이미지를 바이트 배열로 디코딩
//                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//
////                // 이미지를 저장할 경로 설정
////                String imageFilePath = "path/to/save/directory/profile.jpg"; // 경로 및 파일 이름을 원하는 대로 지정하세요
////
////                // 디코딩된 이미지를 파일로 저장
////                try (FileOutputStream fos = new FileOutputStream(imageFilePath)) {
////                    fos.write(imageBytes);
////                }
////
////                imgName = "profile.jpg"; // 이미지 파일 이름 설정
////                imgUrl = "/member/profile/" + imgName; // 이미지 URL 설정
//
//                //파일 업로드
//                if (!StringUtils.isEmpty(oriName)) {
//                    imgName = fileService.uploadFile(profileLocation, oriName, base64Image.getBytes());
//                    imgUrl = "/member/profile/" + imgName;
//                }
//
//
//            } catch (Exception e) {
//                throw new Exception("프로필 이미지 저장 중 오류 발생: " + e.getMessage(), e);
//            }
//        }
//
//        // 상품 이미지 정보 저장
//        profile.updateProfile(imgName, imgUrl);
//        profileRepository.save(profile);
//    }

}
