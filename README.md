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

**1. 세션에 저장되어 있는 value는 최신 값이 아닐 수 있다.**
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
   @SessionAttribute는 세션에서 가져온 객체를 바로 사용하기 때문에, 해당 객체가 최신 상태인지 보장되지 않는다.
   @SessionAttribute로 가져온 Member 객체는 초기 로그인 시점의 데이터이며, 이후 Member 정보가 수정되더라도 기존 세션 객체는 갱신되지 않는다.
   그렇기 때문에 위 코드에서 findMember를 통해서 다시 Member를 조회해 로그인 된 사용자의 상태를 최신 상태로 보장되게 해준다.

**2. 엔티티 간의 관계가 양방향 연관관계일때, 연관관계 편의 메소드는 항상 사용해야하는가?**
   
   연관관계 편의 메소드를 사용하는 이유는 비즈니스 로직의 일관성을 지켜주기 위함이다.

   이번 프로젝트에서는 Member와 ToDoForm이 양방향 연관관계를 맺고있고 ToDoForm의 연관관계의 주인이다.
   ```java
   public void delete(Long id) {
        toDoRepository.deleteById(id);
    }
   ```
   위 코드처럼 특정 ToDoForm을 삭제할 때 Member.getForms.remove(form) 처럼 Member의 리스트에서도 삭제해줘야하나? -> 정답은 삭제해주지 않아도 된다.

   위의 로직은 연관관계 편의 메소드가 필요한 로직이 아니다. 
   영속성 컨텍스트의 생명주기는 트랜잭션이 시작하고 끝날때까지이다.
   만약 위 코드의 비즈니스 로직이 Member.getForms().size()같은 코드를 사용한다면 당연히 연관관계 편의 메소드를 사용해줘야하지만 오직 toDoForm만 삭제하는 비즈니스 로직이기 때문에 필요없다.

**3. 객체 바인딩시 오류 발생**

   ```java
   @PostMapping("{id}/edit")
    public String editMember(@PathVariable Long id, @Validated @ModelAttribute("member") MemberEditForm editForm,
                             BindingResult bindingResult, @RequestParam MultipartFile profileImage,
                             Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            /**
             * 객체 바인딩에서 오류가 발생했을 때 다른 필드값들이 null로 남아있을 수도 있다.
             * 이를 방지하기 위해서는 매개변수인 editForm에 기존 값들을 모두 넣어서 전달해야 출력이 정상적으로되고 오류메시지도 표시가된다.
             */
            Member member = memberService.findMember(id);
            editForm.setId(member.getId());
            editForm.setUserId(member.getUserId());
            editForm.setImageURL(member.getUserImage().getUrl());
            model.addAttribute("member", editForm);
            return "member/edit";
        }

        memberService.editProfile(editForm, profileImage);

        return "redirect:/";

    }
   ```
    
    위의 코드에서는 @Validated를 통해 editForm 객체에 바인딩 되는 필드에 대한 검증을 실행한다. 하지만 객체 바인딩에 오류가 발생했을 때 다른 필드값이 null로 남아있을 수도 있다.
    이를 방지하기 위해서는 매개변수인 editForm에 기존 값들을 모두 넣어서 전달해야 출력이 정상적으로되고 오류메시지도 표시가된다.

   
## 4️⃣ 실제 화면

<img src="https://github.com/user-attachments/assets/c6fb5011-829a-4f09-8a6e-9d84bc59f4c7" width="300" height="400">
<img src="https://github.com/user-attachments/assets/9bc60209-6fcb-4336-b554-17a910d7dd0b" width="300" height="400">
<img src="https://github.com/user-attachments/assets/037457d0-7178-4e54-848d-1d83e405dad1" width="300" height="400">
<img src="https://github.com/user-attachments/assets/a9d1781d-e350-47a8-ae5b-a8fad706ac4a" width="300" height="400">
<img src="https://github.com/user-attachments/assets/06af47ae-5e9f-4977-8927-bfb19442b742" width="300" height="400">
<img src="https://github.com/user-attachments/assets/1453afe1-f7a7-4f6d-831b-df43702c9ec6" width="300" height="400">
<img src="https://github.com/user-attachments/assets/9e3e87c2-3b3f-4427-b756-186b87caf55f" width="300" height="400">


