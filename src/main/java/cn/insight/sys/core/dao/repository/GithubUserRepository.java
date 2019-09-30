package cn.insight.sys.core.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.insight.sys.core.dao.domain.GithubUserEntity;

@Repository
public interface GithubUserRepository extends JpaRepository<GithubUserEntity,Integer>,JpaSpecificationExecutor<GithubUserEntity> {
    GithubUserEntity findByGithubId(long githubId);
}
