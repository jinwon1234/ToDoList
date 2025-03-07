package springpr.toyproject.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springpr.toyproject.domain.Member;
import springpr.toyproject.domain.UserImage;
import springpr.toyproject.member.dto.ChangePasswordForm;
import springpr.toyproject.member.dto.MemberEditForm;
import springpr.toyproject.member.dto.JoinForm;
import springpr.toyproject.member.repository.MemberRepository;
import springpr.toyproject.member.repository.UserImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Value("${userImage.dir}")
    private String userImageDir;

    private final UserImageRepository userImageRepository;
    private final MemberRepository memberRepository;

    public Member findMember(Long Id) {
        return memberRepository.findById(Id).orElseThrow();
    }

    public Member login(String userId, String password) {
        Member findMember = memberRepository.findByUserIdAndPassword(userId, password);
        if (findMember == null) {
            return null;
        }

        return findMember;
    }

    public boolean join(JoinForm member, MultipartFile file) throws IOException {
        String username = member.getUsername();
        String userId = member.getUserId();
        String password = member.getPassword();

        Member findMember = memberRepository.findByUserId(userId);
        if (findMember != null) return false;

        UserImage userImage = new UserImage();

        uploadImage(file, userImage);

        userImageRepository.save(userImage);
        memberRepository.save(new Member(username, userId, password, userImage));
        return true;
    }

    public void editProfile(MemberEditForm member, MultipartFile file) throws IOException {

        Member findMember = memberRepository.findById(member.getId()).orElseThrow();
        findMember.changeName(member.getName());
        if (!file.isEmpty()) {
            makeImageURL(file, findMember.getUserImage());
        }
    }


    public void deleteImage(Long Id) {
        Member findMember = memberRepository.findById(Id).orElseThrow();
        findMember.getUserImage().updateURL("defaultImage.jpeg");
    }

    /**
     * @Transactional은 public 메소드에만 트랜잭션을 적용한다.
     */
    private void uploadImage(MultipartFile file, UserImage userImage) throws IOException {
        if (!file.isEmpty()) {
            makeImageURL(file, userImage);
        }
        else {
            userImage.updateURL("defaultImage.jpeg");
        }
    }

    public void changePassword(ChangePasswordForm member) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow();
        findMember.changePassword(member.getNewPassword());
    }

    public boolean checkPassword(ChangePasswordForm member) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow();
        if (findMember.getPassword().equals(member.getCurPassword())) return true;
        return false;
    }

    private void makeImageURL(MultipartFile file, UserImage userImage) throws IOException {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();
        File imageName = new File(userImageDir + imageFileName);
        file.transferTo(imageName);
        userImage.updateURL(imageFileName);
    }


}
