package com.collabnet.ctf.rest.docman;

import org.codehaus.jackson.annotate.JsonProperty;

public class PostDocumentEntity {
    private final String title;
    private final String description;
    private final String status;
    private final String fileName;
    private final String mimeType;
    private final String fileId;
    private final String versionComment;
    private final String associationId;
    private final String associationDescription;
    private final boolean createLocked;
    private final boolean createDownloadRestricted;
    private final boolean allowForceUnlock;

    public PostDocumentEntity(@JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("status") String status,
            @JsonProperty("fileName") String fileName,
            @JsonProperty("mimeType") String mimeType,
            @JsonProperty("fileId") String fileId,
            @JsonProperty("versionComment") String versionComment,
            @JsonProperty("associationId") String associationId,
            @JsonProperty("associationDescription") String associationDescription,
            @JsonProperty("createLocked") boolean createLocked,
            @JsonProperty("createDownloadRestricted") boolean createDownloadRestricted,
            @JsonProperty("allowForceUnlock") boolean allowForceUnlock) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileId = fileId;
        this.versionComment = versionComment;
        this.associationId = associationId;
        this.associationDescription = associationDescription;
        this.createLocked = createLocked;
        this.createDownloadRestricted = createDownloadRestricted;
        this.allowForceUnlock = allowForceUnlock;
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

    @JsonProperty("versionComment")
    public String getVersionComment() {
        return versionComment;
    }

    @JsonProperty("associationId")
    public String getAssociationId() {
        return associationId;
    }

    @JsonProperty("associationDescription")
    public String getAssociationDescription() {
        return associationDescription;
    }

    @JsonProperty("createLocked")
    public boolean isCreateLocked() {
        return createLocked;
    }

    @JsonProperty("createDownloadRestricted")
    public boolean isCreateDownloadRestricted() {
        return createDownloadRestricted;
    }

    @JsonProperty("allowForceUnlock")
    public boolean isAllowForceUnlock() {
        return allowForceUnlock;
    }

}