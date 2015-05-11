/*
 * Copyright 2000-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.conspect.legacy.repository.impl;

import nl.conspect.legacy.domain.User;
import nl.conspect.legacy.repository.UserRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by marten on 17-04-15.
 */
public class HibernateUserRepositoryImpl extends HibernateDaoSupport implements UserRepository {

    public void save(User user) {
        getHibernateTemplate().saveOrUpdate(user);
    }

    public User find(long id) {
        return (User) getHibernateTemplate().get(User.class, Long.valueOf(id));
    }

    public User findWithUsername(String username) {
        String hql = "from User u where u.username=:username";
        List users = getHibernateTemplate().findByNamedParam(hql, "username", username);
        return (User) DataAccessUtils.singleResult(users);
    }

    public void remove(User user) {
        getHibernateTemplate().delete(user);
    }
}
