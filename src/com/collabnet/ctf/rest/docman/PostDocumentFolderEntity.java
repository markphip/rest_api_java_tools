package com.collabnet.ctf.rest.docman;

import org.codehaus.jackson.annotate.JsonProperty;

public class PostDocumentFolderEntity {
    private final String title;
    private final String description;
    
    public PostDocumentFolderEntity(@JsonProperty("title") String title,
            @JsonProperty("description") String description) {
        this.title = title;
        this.description = description;
    }
    
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

}
