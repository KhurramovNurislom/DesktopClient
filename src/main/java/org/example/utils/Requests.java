package org.example.utils;

import org.example.Main;
import org.example.modules.Hash;
import org.example.modules.userMessages.UserMessages;
import org.example.modules.keys.Keys;
import org.example.modules.keysGen.KeysGen;
import org.example.modules.login.LoginData;
import org.example.modules.upload.Upload;
import org.example.modules.users.AUsers;
import org.example.modules.usersMe.AUsersMe;
import org.example.modules.verificationInfo.VerificationInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class Requests {
    ObjectMapper objectMapper = new ObjectMapper();
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    public boolean RequestLogin(String login, String password) {
        /** Login Pagega kiritilgan login va parolni serverga yuborib kerakli ruhsatni oladi */
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("identifier", login).addFormDataPart("password", password).build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(Main.getUrl() + "/api/auth/local").method("POST", body).build();
        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            LoginData loginData = objectMapper.readValue(response.body().byteStream(), LoginData.class);
            Main.setLoginData(loginData);
            response.close();
            return Main.getLoginData().getJwt() != null && !Main.getLoginData().getJwt().isEmpty();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestLogin() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void ResponseUsersMe() {
        /** Foydalanuvchi haqidagi ma'lumotlarni olib keladi */
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetUser($userid:ID){ " +
                " usersPermissionsUser(id:$userid) { data { id attributes { username email " +
                " rasm { data { id attributes{ url } } } kalits{   " +
                " data { attributes { pubkey } } } } } } } \",\"variables\":{\"userid\": " +
                Main.getLoginData().getUser().getId() + "} }");
        Request request = new Request.Builder().url(Main.getUrl() + "/graphql").method("POST", body).
                addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            AUsersMe aUsersMe = objectMapper.readValue(response.body().byteStream(), AUsersMe.class);
            Main.setAUsersMe(aUsersMe);
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().ResponseUsersMe() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void RequestkeysGen(String privateKey, String publicKey, String keyName) {
        /** Kalit generatsiya qilish */
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"data\":{\"privkey\":\"" + "privateKey" +
                "\"," + "\"pubkey\":\"" + publicKey + "\",\"user\":" + Main.getLoginData().getUser().getId() +
                ",\"nomi\": \"" + keyName + "\" } }");
        Request request = new Request.Builder().url(Main.getUrl() + "/api/kalits").method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            assert response.body() != null;
            KeysGen keysGen = objectMapper.readValue(response.body().byteStream(), KeysGen.class);
            Main.setKeysGen(keysGen);
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestkeysGen() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void RequestKeys() {
        /** Kalitlarni olib keladi */
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetKeys($id:ID) { " +
                " kalits ( filters : { user : { id : { eq : $id } } } ) { data { id attributes { " +
                " privkey pubkey nomi createdAt } } } } \",\"variables\":{\"id\":" +
                Main.getLoginData().getUser().getId() + "}}");
        Request request = new Request.Builder().url(Main.getUrl() + "/graphql")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            assert response.body() != null;
            Keys keys = objectMapper.readValue(response.body().byteStream(), Keys.class);
            Main.setKeys(keys);
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestKeys() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void RequestSignLink() {
        okhttp3.Request request = new okhttp3.Request.Builder().url(Main.getUrl() + "/api/imzo/link/").method("GET", null).build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            assert response.body() != null;
            Hash hash = objectMapper.readValue(response.body().byteStream(), Hash.class);
            Main.setHash(hash);
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestSignLink() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void ResponseMessage(int to_user_id, String signText, int keyId, String xabar, String signXabar) {
        /**  Message qismi (Imzolangan fayl haqidagi ma'lumotlarni serverga yuklash) */
        MediaType mediaType = MediaType.parse("application/json");
        String bodystr = "{ \"data\":{ \"from_user\":" + Main.getLoginData().getUser().getId() + "," +
                "\"to_user\": " + to_user_id + ", \"xabar\": " + xabar + ", \"signXabar\": " + signXabar + "," +
                "\"fayllar\": [ { \"fayl\": " + Main.getUpload().getId() + ", \"imzo\": \"" + signText + "\"," +
                "\"hash\": \"" + Main.getHash().getHash() + "\" } ], \"hash\": \"" + Main.getHash().getHash() + "\"," +
                "\"kalit\": " + keyId + " } } ";
        RequestBody body = RequestBody.create(mediaType, bodystr);
        Request request = new Request.Builder().url(Main.getUrl() + "/api/messages")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().ResponseMessage() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void RequestUpload(String filePath) {
        /** Imzolangan faylni serverga yuklash */
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("files", "/" + filePath, RequestBody
                        .create(MediaType.parse("application/pdf"), new File("/" + filePath)))
                .build();
        Request request = new Request.Builder().url(Main.getUrl() + "/api/upload")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            assert response.body() != null;
            Upload[] uploadData = gson.fromJson(response.body().string(), Upload[].class);
            Main.setUpload(uploadData[0]);
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestUpload() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void RequestUsers() {
        /** Mavjud foydalanuvchilar haqidagi ma'lumotlarni olib keladi */
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetUsers($userid:ID){usersPermissionsUsers(filters:{id:{ne: $userid}}){" +
                "data { id attributes { username email rasm { data { id attributes { url } } } kalits { data { attributes { pubkey } } } } } } } \",\"variables\":{\"userid\":"
                + Main.getLoginData().getUser().getId() + "} }");
        Request request = new Request.Builder()
                .url(Main.getUrl() + "/graphql")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            assert response.body() != null;
            AUsers users = objectMapper.readValue(response.body().byteStream(), AUsers.class);
            Main.setAUsers(users);
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestUsers() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void RequestUserMessages(String user_id, String me_id, int start, int limit) {
        /** Umumiy messagelar haqidagi ma'lumotlarni olib keladi */
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetMessages($meid:ID, $userid:ID, $start:Int, $limit:Int){" +
                "messages(pagination: {start:$start, limit:$limit}, sort: \\\"createdAt:DESC\\\", filters:{ or:[ {and:[ {from_user:{id:{eq: $meid}}}," +
                "{to_user:{id:{eq: $userid}}} ]}, {and:[ {from_user:{id:{eq: $userid}}}, {to_user:{id:{eq: $meid}}} ]} ] } ){ data{ id attributes{ xabar" +
                " mavzu createdAt imzo fayllar{ imzo fayl{ data{ id attributes{ url size createdAt updatedAt name mime } } } } from_user{ data{ id " +
                "attributes { kalits{ data{ id attributes{ pubkey } } } } } } to_user{ data{ id attributes{ kalits{ data{ id attributes{ pubkey } } } }" +
                " } } } } meta{ pagination{ total pageCount } } } }\",\"variables\":{\"userid\":" + user_id + ",\"meid\":" + me_id + ",\"start\":" +
                start + ",\"limit\":" + limit + "}}");
        Request request = new Request.Builder()
                .url(Main.getUrl() + "/graphql")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            assert response.body() != null;
            UserMessages messages = objectMapper.readValue(response.body().byteStream(), UserMessages.class);
            Main.setUserMessages(messages);
            response.close();

            System.out.println("14.12.2023 => " + messages);
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestUserMessages() => " + e.getMessage());
//            throw new RuntimeException(e);
        }
    }

    public void RequestKeyDel(int id) {
        System.out.println("id => " + id);

        /** Tanlangan kalitni o'chirish */
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(Main.getUrl() + "/api/kalits/" + id)
                .method("DELETE", body)
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestKeyDel() => " + e.getCause());
            throw new RuntimeException(e);
        }


//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url("http://192.168.1.15:1337/api/kalits/287")
//                .method("DELETE", body)
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//        Response response = client.newCall(request).execute();


    }

    public void RequestGetSignedFilesInfo(String link) {
        /** Imzolangan faylni tekshirish uchun undagi ma'lumotlarni o'qib oladi; */
        Request request = new Request.Builder()
                .url(link)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
                .build();
        try {
            Response response = Main.getClient().newCall(request).execute();
            assert response.body() != null;
            VerificationInfo verification = gson.fromJson(response.body().string(), VerificationInfo.class);
            Main.setVerification(verification);
            response.close();
        } catch (IOException e) {
            System.out.println("exception : Requests().RequestGetSignedFilesInfo() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }
}
