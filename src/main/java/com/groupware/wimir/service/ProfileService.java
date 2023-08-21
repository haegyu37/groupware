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

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    @Value("${ProfileLocation}")
    private String profileLocation;

    private final ProfileRepository profileRepository;
    private final FileService fileService;

    public void saveProfile(Profile profile, MultipartFile multipartFile) throws Exception {
        String oriName = multipartFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriName)){
            imgName = fileService.uploadFile(profileLocation, oriName, multipartFile.getBytes());
            imgUrl = "/member/profile/" + imgName;
        }

        //상품 이미지 정보 저장
        profile.updateProfile(oriName, imgName, imgUrl);
        profileRepository.save(profile);
    }
}
