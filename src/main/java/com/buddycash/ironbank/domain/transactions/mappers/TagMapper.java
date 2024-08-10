package com.buddycash.ironbank.domain.transactions.mappers;

import com.buddycash.ironbank.domain.transactions.data.TagResponse;
import com.buddycash.ironbank.domain.transactions.models.Tag;

public abstract class TagMapper {
    public static TagResponse parse(Tag tag) {
        return new TagResponse(tag.getId(), tag.getAccount(), tag.getName());
    }

    public static Tag parse(TagResponse tagResponse) {
        var tag = new Tag();
        tag.setId(tagResponse.id());
        tag.setName(tagResponse.name());
        tag.setAccount(tagResponse.account());
        return tag;
    }
}
