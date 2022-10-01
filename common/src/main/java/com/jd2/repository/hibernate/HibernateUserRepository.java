package com.jd2.repository.hibernate;

import com.jd2.domain.hibernate.HibernateUser;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernateUserRepository implements HibernateUserInterface {

    private final SessionFactory sessionFactory;

    @Override
    public HibernateUser findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hu from HibernateUser hu where hu.id = ?1", HibernateUser.class)
                    .setParameter(1, id).getSingleResult();
        }
    }

    @Override
    public Optional<HibernateUser> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<HibernateUser> findAll() {

        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<HibernateUser> findAll(int limit, int offset) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select hu from HibernateUser hu order by hu.id", HibernateUser.class)
                    .setMaxResults(limit)
                    .setFirstResult(offset)
                    .getResultList();
        }
    }

    @Override
    public HibernateUser create(HibernateUser object) {

        try (Session session = sessionFactory.openSession()) {

            HibernateUser user = HibernateUser.builder()
                    .userName(object.getUserName())
                    .surname(object.getSurname())
                    .birth(object.getBirth())
                    .isDeleted(object.getIsDeleted())
                    .entityInfo(object.getEntityInfo())
                    .userLogin(object.getUserLogin())
                    .userPassword(object.getUserPassword())
                    .gender(object.getGender())
                    .build();

            Serializable save = session.save(user);

            Query<BigInteger> sqlQuery = session
                    .createSQLQuery("SELECT currval('carshop.users_id_seq') as last_id")
                    .addScalar("last_id", StandardBasicTypes.BIG_INTEGER);
            final Long userId = sqlQuery.uniqueResult().longValue();

            final String query = "INSERT INTO carshop.l_user_role (user_id, role_id) values (:userId, :roleId);";
            Query insertSQLQuery = session.createSQLQuery(query);

            session.beginTransaction();

            insertSQLQuery.setParameter("userId", userId);
            insertSQLQuery.setParameter("roleId", 1);
            insertSQLQuery.executeUpdate();

            insertSQLQuery.setParameter("userId", userId);
            insertSQLQuery.setParameter("roleId", 4);
            insertSQLQuery.executeUpdate();

            session.getTransaction().commit();

            System.out.println(user.getId());

            return findById(userId);
        }
    }

    @Override
    public HibernateUser update(HibernateUser object) {

        /*try (Session session = sessionFactory.openSession()) {

            return object;
        }*/
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
