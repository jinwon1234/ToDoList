## 📝 소개
겨울방학동안 공부했던 기술들(Spring, JPA, Thymleaf, Spring Data JPA, Thymeleaf, QueryDsl)을 실제로 간단히 활용해보기 위한 간단한 ToDoList 프로젝트입니다.

현재까지 사용한 기술 : Spring, JPA, Spring Data JPA, Thymeleaf

앞으로 사용할 기술 : QueryDsl

모든 기능을 백엔드에서 구현

## 1️⃣ 구현한 기능
로그인, 회원가입, 회원정보변경(이름, 비밀번호, 프로필사진), ToDoForm 등록/수정/삭제

## 2️⃣ 앞으로 구현할 기능
Querydsl을 활용한 ToDoList 검색기능 추가 예정

## 3️⃣ 어려웠던 혹은 고민했던 기술적인 부분

1. 세션에 저장되어 있는 value는 최신 값이 아닐 수 있다.
   ```java
   @GetMapping("/")
    public String home(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false)
                       Member member, Model model) {

        if (member == null) {
            return "member/loginHome";
        }

        /**
         * 최신 업데이트가 된 Member 객체를 보내야한다.
         * 세션(@SessionAttribute)을 통해 받은 Member를 모델로 전달하면 수정내용이 반영되지 않은채로 넘겨질 수 있다.
         */
        Member findMember = memberService.findMember(member.getId());

        List<ToDoDto> formList = toDoService.findForms(findMember);
        model.addAttribute("formList", formList);
        model.addAttribute("member",
                new MemberDto(findMember.getId(),findMember.getName(), findMember.getUserImage().getUrl()));

        return "home";
    }
   ```

## 4️⃣ 실제 화면

<img src="https://github.com/user-attachments/assets/c6fb5011-829a-4f09-8a6e-9d84bc59f4c7" width="300" height="400">
<img src="https://github.com/user-attachments/assets/9bc60209-6fcb-4336-b554-17a910d7dd0b" width="300" height="400">
<img src="https://github.com/user-attachments/assets/037457d0-7178-4e54-848d-1d83e405dad1" width="300" height="400">
<img src="https://github.com/user-attachments/assets/a9d1781d-e350-47a8-ae5b-a8fad706ac4a" width="300" height="400">
<img src="https://github.com/user-attachments/assets/06af47ae-5e9f-4977-8927-bfb19442b742" width="300" height="400">
<img src="https://github.com/user-attachments/assets/1453afe1-f7a7-4f6d-831b-df43702c9ec6" width="300" height="400">
<img src="https://github.com/user-attachments/assets/9e3e87c2-3b3f-4427-b756-186b87caf55f" width="300" height="400">


