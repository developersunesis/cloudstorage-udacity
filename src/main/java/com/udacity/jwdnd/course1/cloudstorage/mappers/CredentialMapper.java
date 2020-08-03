package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password}, key=#{key} " +
            "WHERE credentialid = #{credentialId}")
    int updateCredential(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userid) VALUES (#{url}, #{username}, " +
            "#{password}, #{key}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Delete("DELETE CREDENTIALS WHERE credentialid = #{credentialId}")
    int deleteCredential(int id);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(int id);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{credentialId}")
    List<Credential> getCredentials(int userId);
}
