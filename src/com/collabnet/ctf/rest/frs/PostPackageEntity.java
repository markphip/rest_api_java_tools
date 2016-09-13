package com.collabnet.ctf.rest.frs;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class PostPackageEntity {
    private final String title;
    private final String description;
    private final Boolean published;

    public PostPackageEntity(@JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("published") Boolean published) {
        super();
        this.title = title;
        this.description = description;
        this.published = published;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("published")
    public Boolean isPublished() {
        return published;
    }

}
