package com.my.dao;

import com.my.dao.interfaces.TagDao;
import com.my.model.Tag;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TagDaoImpl extends HibernateTemplate implements TagDao {

    @Override
    public List<Tag> getAllTags() {
        return (List<Tag>) find("from Tag");
    }

    @Override
    public List<String> getAllTagNames() {
        return (List<String>) find("select name from Tag");
    }

    @Override
    @Transactional(readOnly = false)
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
    public void updateTag(Tag tag) {
        merge(tag);
    }
}
