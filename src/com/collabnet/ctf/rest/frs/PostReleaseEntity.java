package com.collabnet.ctf.rest.frs;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class PostReleaseEntity {
    private final String title;
    private final String description;
    private final String status;
    private final String maturity;

    public PostReleaseEntity(@JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("status") String status,
            @JsonProperty("maturity") String maturity) {
        super();
        this.title = title;
        this.description = description;
        this.status = status;
        this.maturity = maturity;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("maturity")
    public String getMaturity() {
        return maturity;
    }

}
