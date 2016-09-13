package com.collabnet.ctf.rest;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Guid {
    private final String guid;
    private final String prefix;
    private final String id;

    @JsonCreator
    public Guid(@JsonProperty("guid") String guid,
            @JsonProperty("prefix") String prefix,
            @JsonProperty("id") String id) {
        this.guid = guid;
        this.prefix = prefix;
        this.id = id;
    }

    @JsonProperty("guid")
    public String getGuid() {
        return guid;
    }

    @JsonProperty("prefix")
    public String getPrefix() {
        return prefix;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

}