package pl.zajaczkowski.bugtracker.issue.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.issue.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
