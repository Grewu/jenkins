package dao.impl;

import dao.api.UserProfileDao;
import jakarta.persistence.criteria.JoinType;
import model.entity.UserProfile;
import model.entity.UserProfile_;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<Long, UserProfile> implements UserProfileDao {

    private final static String JPQL = """
            SELECT up FROM UserProfile up
            LEFT JOIN FETCH up.user u
            LEFT JOIN FETCH up.department d
            LEFT JOIN FETCH up.position p
            WHERE up.id = :id
            """;

    @Override
    public UserProfile findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(Long userProfileId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
        var root = criteriaQuery.from(UserProfile.class);

        root.fetch(UserProfile_.USER, JoinType.LEFT);
        root.fetch(UserProfile_.DEPARTMENT, JoinType.LEFT);
        root.fetch(UserProfile_.POSITION, JoinType.LEFT);

        var query = criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(UserProfile_.ID), userProfileId));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public UserProfile findUserProfilesWithUserDepartmentAndPositionJPQL(Long userProfileId) {
        var query = entityManager.createQuery(JPQL, UserProfile.class);
        query.setParameter(UserProfile_.ID, userProfileId);
        return query.getSingleResult();
    }

    @Override
    public UserProfile findUserProfilesWithUserDepartmentAndPositionEntityGraph(Long userProfileId) {
        var entityGraph = entityManager.getEntityGraph("userProfile_entity-graph");

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
        var root = criteriaQuery.from(UserProfile.class);


        var query = entityManager.createQuery(criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(UserProfile_.ID), userProfileId)));

        query.setHint("jakarta.persistence.loadgraph", entityGraph);
        return query.getSingleResult();
    }

}
