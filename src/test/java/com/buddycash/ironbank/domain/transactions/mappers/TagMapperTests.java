package com.buddycash.ironbank.domain.transactions.mappers;

import com.buddycash.ironbank.domain.transactions.data.TagResponse;
import com.buddycash.ironbank.domain.transactions.models.Tag;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagMapperTests {
  @Test
  void tagResponseToTagModelParseTest() {
    var tagResponse = new TagResponse(UUID.randomUUID(), UUID.randomUUID(), "housing");
    var tagModel = TagMapper.parse(tagResponse);
    Assertions.assertEquals(tagResponse.id(), tagModel.getId());
    Assertions.assertEquals(tagResponse.account(), tagModel.getAccount());
    Assertions.assertEquals(tagResponse.name(), tagModel.getName());
  }

  @Test
  void tagResponseWithNullIdToTagModelParseTest() {
    var tagResponse = new TagResponse(null, UUID.randomUUID(), "housing");
    var tagModel = TagMapper.parse(tagResponse);
    Assertions.assertNull(tagModel.getId());
    Assertions.assertEquals(tagResponse.account(), tagModel.getAccount());
    Assertions.assertEquals(tagResponse.name(), tagModel.getName());
    Assertions.assertEquals(0, tagModel.getTransactions().size());
  }

  @Test
  void tagModelToTagResponseParseTest() {
    var tagModel = new Tag(UUID.randomUUID(), "housing");
    tagModel.setId(UUID.randomUUID());
    var tagResponse = TagMapper.parse(tagModel);
    Assertions.assertEquals(tagModel.getId(), tagResponse.id());
    Assertions.assertEquals(tagModel.getAccount(), tagResponse.account());
    Assertions.assertEquals(tagModel.getName(), tagResponse.name());
  }
}
