package com.my.dao;

import com.my.dao.interfaces.TagDao;
import com.my.model.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TagDaoImpl extends HibernateTemplate implements TagDao {

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allTags")
    public List<Tag> getAllTags() {
        return (List<Tag>) find("from Tag");
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllTagNames() {
        return (List<String>) find("select name from Tag");
    }

    @Override
    @Transactional(readOnly = false)
    @CacheEvict(value = "allTags", allEntries = true)
    public Tag addTag(Tag tag) {
        return (Tag) save(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTag(long id) {
        return get(Tag.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTag(String name) {
        return (Tag) this.executeWithNativeSession(
                session -> {
                    return session
                            .createQuery("from Tag where name = :name")
                            .setParameter("name", name)
                            .list()
                            .get(0);
                }
        );
    }

    @Override
    @Transactional(readOnly = false)
    @CacheEvict(value = "allTags", allEntries = true)
    public void updateTag(Tag tag) {
        merge(tag);
    }

    @Override
    @Transactional(readOnly = false)
    @CacheEvict(value = "allTags", allEntries = true)
    public void deleteTag(Tag tag) {
        delete(tag);
    }

}
