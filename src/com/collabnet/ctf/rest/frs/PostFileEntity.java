package com.collabnet.ctf.rest.frs;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class PostFileEntity {
    private final String fileName;
    private final String mimeType;
    private final String fileId;

    public PostFileEntity(@JsonProperty("fileName") String fileName,
            @JsonProperty("mimeType") String mimeType,
            @JsonProperty("fileId") String fileId) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileId = fileId;
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    @JsonProperty("mimeType")
    public String getMimeType() {
        return mimeType;
    }

    @JsonProperty("fileId")
    public String getFileId() {
        return fileId;
    }

}