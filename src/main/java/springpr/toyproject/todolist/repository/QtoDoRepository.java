package springpr.toyproject.todolist.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import springpr.toyproject.domain.QToDoForm;
import springpr.toyproject.domain.Status;
import springpr.toyproject.todolist.dto.SearchToDoCond;
import springpr.toyproject.todolist.dto.ToDoDto;

import java.util.List;

import static springpr.toyproject.domain.QToDoForm.*;

@Repository
public class QtoDoRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public QtoDoRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<ToDoDto> searchForm(Long memberId, SearchToDoCond searchToDoCond) {
        String titleCond = searchToDoCond.getTitleCond();
        String contentCond = searchToDoCond.getContentCond();
        Status statusCond = parseStatus(searchToDoCond.getStatusCond());

        return query.select(Projections.constructor(ToDoDto.class,
                        toDoForm.id, toDoForm.title, toDoForm.content, toDoForm.status))
                .from(toDoForm)
                .where(titleLike(titleCond), contentLike(contentCond),
                        statusEq(statusCond), toDoForm.member.id.eq(memberId))
                .fetch();

    }

    private BooleanExpression titleLike(String titleCond) {
        if (StringUtils.hasText(titleCond)) {
            return toDoForm.title.like("%" + titleCond + "%");
        }
        return null;
    }

    private BooleanExpression contentLike(String contentCond) {
        if (StringUtils.hasText(contentCond)) {
            return toDoForm.content.like("%" + contentCond + "%");
        }
        return null;
    }

    private BooleanExpression statusEq(Status statusCond) {
        if (statusCond != null) {
            return toDoForm.status.eq(statusCond);
        }
        return null;
    }

    private Status parseStatus(String status) {
        try {
            return (status == null || status.isEmpty()) ? null : Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
