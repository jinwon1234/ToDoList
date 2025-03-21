package springpr.toyproject.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springpr.toyproject.domain.Member;
import springpr.toyproject.domain.ToDoForm;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDoForm, Long> {

    @Query("select t from ToDoForm t where t.member.id = :memberId")
    List<ToDoForm> findByMember(Long memberId);

    Optional<ToDoForm> findById(Long id);

    void deleteById(Long id);
}

