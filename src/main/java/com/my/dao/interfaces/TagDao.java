package com.my.dao.interfaces;

import com.my.model.Tag;

import java.util.List;

public interface TagDao {

    public List<Tag> getAllTags();

    public List<String> getAllTagNames();

    public Tag addTag(Tag tag);

    public Tag getTag(long id);

    public Tag getTag(String name);

    public void updateTag(Tag tag);

}
