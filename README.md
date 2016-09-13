TeamForge REST API Java "Tests"
--------------------------

This repository contains some Java classes for testing the TeamForge REST API.  Eventually
it might evolve into a Java REST Client for the TeamForge REST API.  For now, it exercises
file storage REST API and FRS and Document REST API that depend on file storage.

Usage:
------

## File Storage Testing ##
### com.collabnet.ctf.rest.filestorage.FileStorageTester ###
    Program arguments:  <projectId> <serverUrl> <username> <password>
    
    projectId (required) - the projectId for testing
    serverUrl (optional) - the CTF server URL.  Defaults to http://ctf-dev-build-box.collab.net
    username (optional) - default is admin
    password (optional) - default is admin
    
    Example: proj1008 http://ctf-dev-build-box.collab.net myusername mypassword