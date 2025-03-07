package springpr.toyproject.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springpr.toyproject.domain.Member;
import springpr.toyproject.domain.ToDoForm;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDoForm, Long> {

    List<ToDoForm> findByMember(Member member);

    Optional<ToDoForm> findById(Long id);

    void deleteById(Long id);
}

