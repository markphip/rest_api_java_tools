package com.collabnet.ctf.rest.filestorage;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.util.Base64;

import com.collabnet.ctf.rest.Guid;
import com.collabnet.ctf.rest.docman.PostDocumentEntity;
import com.collabnet.ctf.rest.docman.PostDocumentFolderEntity;
import com.collabnet.ctf.rest.frs.PostFileEntity;
import com.collabnet.ctf.rest.frs.PostPackageEntity;
import com.collabnet.ctf.rest.frs.PostReleaseEntity;

public class FileStorageTester {

    public static final String SAMPLE_FILE_FORM_DATA = "--4321MyBoundary\n" +
            "Content-Disposition: form-data; name=\"file\"; filename=\"myfile.txt\"\n" +
            "Content-Type: text/plain\n" + "Content-Transfer-Encoding: base64\n\n" +
            Base64.encodeBytes("This is my test data".getBytes()) + "\n" +
            "--4321MyBoundary--";

    private String serverUrl;
    private String projectId;
    private String username;
    private String password;
    private String token;
    private ObjectMapper objectMapper;

    
    public FileStorageTester(String serverUrl, String projectId, String username, String password) {
        super();
        this.serverUrl = serverUrl;
        this.projectId = projectId;
        this.username = username;
        this.password = password;
    }

    public String testCreatePackage() throws Exception {
        String url = serverUrl + "ctfrest/frs/v1/projects/" + projectId + "/packages";
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        httpPost.addRequestHeader("Content-Type", "application/json; charset=UTF-8");
        PostPackageEntity postPackageEntity = new PostPackageEntity("Test Package " + new Date().getTime(),
                "Test Package", false);
        String requestBody = getObjectMapper().writeValueAsString(postPackageEntity);
        httpPost.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
        String responseBody = httpPost.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getId();
    }

    public String testCreateRelease(String packageId) throws Exception {
        String url = serverUrl + "ctfrest/frs/v1/packages/" + packageId + "/releases";
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        httpPost.addRequestHeader("Content-Type", "application/json; charset=UTF-8");
        PostReleaseEntity postReleaseEntity = new PostReleaseEntity("Test Release " + new Date().getTime(),
                "Test Release", "pending", "Development Build");
        String requestBody = getObjectMapper().writeValueAsString(postReleaseEntity);
        httpPost.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
        String responseBody = httpPost.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getId();
    }

    public String testUploadFile() throws Exception {
        String url = serverUrl + "ctfrest/filestorage/v1/files";
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        httpPost.addRequestHeader("Content-Type", "multipart/form-data; charset=UTF-8; boundary=4321MyBoundary");
        httpPost.setRequestEntity(new StringRequestEntity(SAMPLE_FILE_FORM_DATA, "application/json", "UTF-8"));
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
        String responseBody = httpPost.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getGuid();
    }

    public String testStartFileUpload() throws Exception {
        String url = serverUrl + "ctfrest/filestorage/v1/files/chunks";
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
        String responseBody = httpPost.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getGuid();
    }

    public void testWrite(String fileStorageId, String data) throws Exception {
        String url = serverUrl + "ctfrest/filestorage/v1/files/chunks/" + fileStorageId;
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        httpPost.addRequestHeader("Content-Type", "multipart/form-data; charset=UTF-8; boundary=4321MyBoundary");
        String requestBody = "--4321MyBoundary\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"myfile.txt\"\n" +
                "Content-Type: text/plain\n" + "Content-Transfer-Encoding: base64\n\n" +
                Base64.encodeBytes(data.getBytes()) + "\n" +
                "--4321MyBoundary--";
        httpPost.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
    }

    public long testGetSize(String fileStorageId) throws Exception {
        String url = serverUrl + "ctfrest/filestorage/v1/files/" + fileStorageId + "/size";
        GetMethod httpGet = new GetMethod(url);
        httpGet.addRequestHeader("Accept", "application/json");
        httpGet.addRequestHeader("Authorization", "Bearer " + getToken());
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpGet);
        if (!successful(response)) {
            throw new Exception("GET " + url + " request failed with response " + response + ".\n\n"
                    + httpGet.getResponseBodyAsString());
        }
        String responseBody = httpGet.getResponseBodyAsString();
        Long size = getObjectMapper().readValue(responseBody, Long.class);
        return size.longValue();
    }

    public void testEndFileUpload(String fileStorageId) throws Exception {
        String url = serverUrl + "ctfrest/filestorage/v1/files/chunks/" + fileStorageId;
        DeleteMethod httpDelete = new DeleteMethod(url);
        httpDelete.addRequestHeader("Authorization", "Bearer " + getToken());
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpDelete);
        if (!successful(response)) {
            throw new Exception("DELETE " + url + " request failed with response " + response + ".\n\n"
                    + httpDelete.getResponseBodyAsString());
        }
    }

    public String testGetFileStorageId(String frsFileId) throws Exception {
        String url = serverUrl + "ctfrest/frs/v1/files/" + frsFileId +"/file-id";
        GetMethod httpGet = new GetMethod(url);
        httpGet.addRequestHeader("Accept", "application/json");
        httpGet.addRequestHeader("Authorization", "Bearer " + getToken());
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpGet);
        if (!successful(response)) {
            throw new Exception("GET " + url + " request failed with response " + response + ".\n\n"
                    + httpGet.getResponseBodyAsString());
        }
        String responseBody = httpGet.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getGuid();
    }

    public String testDownloadFile(String fileStorageId) throws Exception {
        String url = serverUrl + "ctfrest/filestorage/v1/files/" + fileStorageId;
        GetMethod httpGet = new GetMethod(url);
        httpGet.addRequestHeader("Accept", "*/*");
        httpGet.addRequestHeader("Authorization", "Bearer " + getToken());
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpGet);
        if (!successful(response)) {
            throw new Exception("GET " + url + " request failed with response " + response + ".\n\n"
                    + httpGet.getResponseBodyAsString());
        }
        return httpGet.getResponseBodyAsString();
    }

    public String testRead(String fileStorageId, int offset, int length) throws Exception {
        String url = serverUrl + "ctfrest/filestorage/v1/files/chunks/" + fileStorageId + "?offset=" + offset
                + "&length=" + length;
        GetMethod httpGet = new GetMethod(url);
        httpGet.addRequestHeader("Accept", "*/*");
        httpGet.addRequestHeader("Authorization", "Bearer " + getToken());
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpGet);
        if (!successful(response)) {
            throw new Exception("GET " + url + " request failed with response " + response + ".\n\n"
                    + httpGet.getResponseBodyAsString());
        }
        return httpGet.getResponseBodyAsString();
    }

    public String testCreateFrsFile(String releaseId, String fileName, String fileStorageId) throws Exception {
        String url = serverUrl + "ctfrest/frs/v1/releases/" + releaseId + "/files";
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        httpPost.addRequestHeader("Content-Type", "application/json; charset=UTF-8");
        PostFileEntity postFileEntity = new PostFileEntity(fileName, "text/plain", fileStorageId);
        String requestBody = getObjectMapper().writeValueAsString(postFileEntity);
        httpPost.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
        String responseBody = httpPost.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getId();
    }

    public String testCreateFolder() throws Exception {
        String url = serverUrl + "ctfrest/docman/v1/projects/" + projectId + "/documentfolders";
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        httpPost.addRequestHeader("Content-Type", "application/json; charset=UTF-8");
        PostDocumentFolderEntity postDocumentFolderEntity = new PostDocumentFolderEntity("Test Folder " + new Date().getTime(),
                "Test folder");
        String requestBody = getObjectMapper().writeValueAsString(postDocumentFolderEntity);
        httpPost.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
        String responseBody = httpPost.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getId();
    }

    public String testCreateDocument(String folderId, String fileStorageId) throws Exception {
        String url = serverUrl + "ctfrest/docman/v1/documentfolders/" + folderId + "/documents";
        PostMethod httpPost = new PostMethod(url);
        httpPost.addRequestHeader("Accept", "application/json");
        httpPost.addRequestHeader("Authorization", "Bearer " + getToken());
        httpPost.addRequestHeader("Content-Type", "application/json; charset=UTF-8");
        PostDocumentEntity postDocumentEntity = new PostDocumentEntity("My Title", "My document", "draft", "myfile.txt",
                "text/plain", fileStorageId, null, null, null, false, false, false);
        String requestBody = getObjectMapper().writeValueAsString(postDocumentEntity);
        httpPost.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
        HttpClient client = new HttpClient();
        int response = client.executeMethod(httpPost);
        if (!successful(response)) {
            throw new Exception("POST " + url + " request failed with response " + response + ".\n\n"
                    + httpPost.getResponseBodyAsString());
        }
        String responseBody = httpPost.getResponseBodyAsString();
        Guid guid = getObjectMapper().readValue(responseBody, Guid.class);
        return guid.getId();
    }

    private String getToken() throws UnsupportedEncodingException {
        if (token == null) {
            String requestBody = "client_id=api-client&grant_type=password&scope=urn:ctf:services:ctf&username="
                    + username + "&password=" + password;
            PostMethod httpPost = new PostMethod(serverUrl + "sf/auth/token");
            httpPost.setRequestEntity(new StringRequestEntity(requestBody, "application/json", "UTF-8"));
            httpPost.addRequestHeader("Accept", "application/gson");
            httpPost.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            HttpClient client = new HttpClient();
            try {
                int response = client.executeMethod(httpPost);
                if (!successful(response)) {
                    throw new Exception("sf/auth/token request failed with response " + response + ".");
                }
                String responseBody = httpPost.getResponseBodyAsString();
                if (responseBody.contains("error")) {
                    throw new Exception(responseBody);
                }
                int index = responseBody.indexOf("access_token");
                if (index == -1) {
                    throw new Exception("sf/auth/token request returned unexpected response body.");
                }
                token = responseBody.substring(index + 15);
                index = token.indexOf(",");
                token = token.substring(0, index-1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    private boolean successful(int response) {
        return response >= 200 && response <= 299;
    }

    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new InvalidParameterException("projectId (args[0]) required");
        }
        String projectId = args[0];
        String serverUrl = args.length < 2 ? "http://ctf-dev-build-box.collab.net" : args[1];
        if (!serverUrl.endsWith("/")) {
            serverUrl = serverUrl + "/";
        }
        String username = args.length < 3 ? "admin" : args[2];
        String password = args.length < 4 ? "admin" : args[3];
        FileStorageTester fileStorageTester = new FileStorageTester(serverUrl, projectId, username, password);
        try {
            System.out.println("Testing create package...");
            String packageId = fileStorageTester.testCreatePackage();
            System.out.println("Package created: " + packageId);

            System.out.println("Testing create release...");
            String releaseId = fileStorageTester.testCreateRelease(packageId);
            System.out.println("Release created: " + releaseId);

            System.out.println("Testing uploadFile...");
            String fileStorageId = fileStorageTester.testUploadFile();
            System.out.println("File uploaded: " + fileStorageId);

            System.out.println("Testing create FRS file...");
            String frsFileId = fileStorageTester.testCreateFrsFile(releaseId, "allAtOnce.txt", fileStorageId);
            System.out.println("FRS file created: " + frsFileId);

            System.out.println("Testing startFileUpload...");
            fileStorageId = fileStorageTester.testStartFileUpload();
            System.out.println("File upload started: " + fileStorageId);

            System.out.println("Writing chunk 1...");
            fileStorageTester.testWrite(fileStorageId, "This is some");
            System.out.println("Chunk 1 written");

            System.out.println("Writing chunk 2...");
            fileStorageTester.testWrite(fileStorageId, " super cool ");
            System.out.println("Chunk 2 written");

            System.out.println("Writing chunk 3...");
            fileStorageTester.testWrite(fileStorageId, "stuff I'm doing");
            System.out.println("Chunk 3 written");

            System.out.println("Writing chunk 4...");
            fileStorageTester.testWrite(fileStorageId, "!  Does it still work?");
            System.out.println("Chunk 4 written");

            System.out.println("Testing endFileUpload...");
            fileStorageTester.testEndFileUpload(fileStorageId);
            System.out.println("File upload ended");

            // Why does this not work consistently?
            try {
                System.out.println("Testing create FRS file...");
                frsFileId = fileStorageTester.testCreateFrsFile(releaseId, "inChunks.txt", fileStorageId);
                System.out.println("FRS file created: " + frsFileId);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }

            System.out.println("Testing get file storage id...");
            fileStorageId = fileStorageTester.testGetFileStorageId(frsFileId);
            System.out.println("File storage id: " + fileStorageId);

            System.out.println("Testing download file...");
            String content = fileStorageTester.testDownloadFile(fileStorageId);
            System.out.println(content);

            System.out.println("Testing read of first 5 bytes...");
            System.out.println(fileStorageTester.testRead(fileStorageId, 0, 5));
            System.out.println("Testing read of next 10 bytes...");
            System.out.println(fileStorageTester.testRead(fileStorageId, 5, 10));

            System.out.println("Testing create folder...");
            String folderId = fileStorageTester.testCreateFolder();
            System.out.println("Folder created: " + folderId);

            System.out.println("Testing uploadFile...");
            fileStorageId = fileStorageTester.testUploadFile();
            System.out.println("File uploaded: " + fileStorageId);

            System.out.println("Testing create document...");
            String docId = fileStorageTester.testCreateDocument(folderId, fileStorageId);
            System.out.println("Document created: " + docId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}