package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,
                             EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int saveCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), encodedKey));
        return credential.getCredentialId() != 0 ?
                credentialMapper.updateCredential(credential) :
                credentialMapper.insertCredential(credential);
    }

    public Credential getCredential(int id){
        return credentialMapper.getCredential(id);
    }

    public List<Credential> getCredentials(int userId) {
        return credentialMapper.getCredentials(userId);
    }

    public int deleteCredential(int id){
        return credentialMapper.deleteCredential(id);
    }
}
