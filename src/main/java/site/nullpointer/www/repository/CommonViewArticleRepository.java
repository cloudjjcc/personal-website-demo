package site.nullpointer.www.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import site.nullpointer.www.entity.ViewCommonArticle;

@Repository
public interface CommonViewArticleRepository extends JpaRepository<ViewCommonArticle, String> {
	Optional<ViewCommonArticle> findById(String id);
}
