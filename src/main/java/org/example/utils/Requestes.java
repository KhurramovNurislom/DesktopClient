package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.example.Main;
import org.example.moduls.login.LoginData;

public class Requestes {

    /******************
     *** server.loc ***
     ******************/

    ObjectMapper objectMapper = new ObjectMapper();

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    public boolean RequestLogin(String login, String password) throws Exception {

        /** Login Pagega kiritilgan login va parolni serverga yuborib kerakli ruhsatni oladi */

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("identifier", login).addFormDataPart("password", password).build();

        Request request = new Request.Builder().url(Main.getUrl() + "/api/auth/local").method("POST", body).build();

        Response response = client.newCall(request).execute();

        LoginData loginData = objectMapper.readValue(response.body().byteStream(), LoginData.class);

        Main.setLoginData(loginData);

//        System.out.println(Main.getLoginData());

        if (Main.getLoginData().getJwt().equals("") || Main.getLoginData().getJwt() == null || Main.getLoginData().getJwt().isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

//    public void ResponseUsersMe() throws IOException {
//
//        /** Foydalanuvchi haqidagi ma'lumotlarni olib keladi */
//
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//        MediaType mediaType = MediaType.parse("application/json");
//
//        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetUser($userid:ID){ " +
//                "usersPermissionsUser(id:$userid){ data{ id attributes{ username email   " +
//                " rasm{ data{ id attributes{ url } } } kalits{   " +
//                "data{ attributes{ pubkey } } } } } } } \",\"variables\":{\"userid\":" +
//                Main.getLoginData().getUser().getId() + "}}");
//
//        Request request = new Request.Builder().url(Main.getUrl() + "/graphql").method("POST", body).
//                addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        AUsersMe aUsersMe = objectMapper.readValue(response.body().byteStream(), AUsersMe.class);
//
//        Main.setaUsersMe(aUsersMe);
//    }
//
//    public void RequestkeysGen(String privateKey, String publicKey, String keyName) throws IOException {
//        /**
//         * Kalit generatsiya qilish
//         */
//        MediaType mediaType = MediaType.parse("application/json");
//
//        RequestBody body = RequestBody.create(mediaType, "{  \"data\":{  \"privkey\": \"" + privateKey +
//                "\",  " + " \"pubkey\": \"" + publicKey + "\",  \"user\":" + Main.getLoginData().getUser().getId() +
//                ",  \"nomi\": \"" + keyName + "\"  } }");
//
//        Request request = new Request.Builder().url(Main.getUrl() + "/api/kalits").method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//
//        Response response = Main.getClient().newCall(request).execute();
//
//        KeysGen keysGen = objectMapper.readValue(response.body().byteStream(), KeysGen.class);
//
//        Main.setKeysGen(keysGen);
//    }
//
//    public void RequestKeys() throws IOException {
//        /**
//         * Kalitlarni olib keladi
//         */
//
//        MediaType mediaType = MediaType.parse("application/json");
//
//        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetKeys($id:ID){   " +
//                " kalits(filters:{user:{id:{eq: $id}}}){ data{ id attributes{ " +
//                "privkey pubkey nomi createdAt } } } } \",\"variables\":{\"id\":" +
//                Main.getLoginData().getUser().getId() + "}}");
//
//// System.out.println(Main.getUrl());
//
//        Request request = new Request.Builder().url(Main.getUrl() + "/graphql")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//
//        Response response = Main.getClient().newCall(request).execute();
//
//        Keys keys = objectMapper.readValue(response.body().byteStream(), Keys.class);
//        Main.setKeys(keys);
//    }
//
//    public void RequestSignLink() throws IOException, NoSuchAlgorithmException, KeyManagementException {
//
//        Request request = new Request.Builder().url(Main.getUrl() + "/api/imzo/link/").method("GET", null).build();
//
//        Response response = Main.getClient().newCall(request).execute();
//
//        Hash hash = objectMapper.readValue(response.body().byteStream(), Hash.class);
//        Main.setHash(hash);
//// System.out.println("hash: => " + hash.getHash());
//
//    }
//
//
//    public void ResponseMessage(int to_user_id, String signText, int keyId, String xabar, String signXabar) throws IOException {
//        /**
//         *  Message qismi (Imzolangan fayl haqidagi ma'lumotlarni serverga yuklash)
//         */
//
////        System.out.println(
////                "from_user_id => " + Main.getLoginData().getUser().getId() + "\n"
////                        + "to_user_id => " + to_user_id + "\n"
////                        + "signText => " + signText + "\n"
////                        + "keyId => " + keyId + "\n"
////                        + "upload file id => " + Main.getUpload().getId()
////        );
//
//        MediaType mediaType = MediaType.parse("application/json");
//
//        String bodystr = "{" +
//                "\"data\":{" +
//                "\"from_user\":" + Main.getLoginData().getUser().getId() + "," +
//                "\"to_user\": " + to_user_id + "," +
//                "\"xabar\": " + xabar + "," +
//                "\"signXabar\": " + signXabar + "," +
//                "\"fayllar\": [" +
//                "{" +
//                "\"fayl\": " + Main.getUpload().getId() + "," +
//                "\"imzo\": \"" + signText + "\"," +
//                "\"hash\": \"" + Main.getHash().getHash() + "\"" +
//                "}" +
//                "]," +
//                "\"hash\": \"" + Main.getHash().getHash() + "\"," +
//                "\"kalit\": " + keyId + "   }" +
//                "}";
//
//        RequestBody body = RequestBody.create(mediaType, bodystr);
//
////        System.out.println("bodystr : => " + bodystr);
//
//        Request request = new Request.Builder().url(Main.getUrl() + "/api/messages")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//
//        Response response = Main.getClient().newCall(request).execute();
//
//        assert response.body() != null;
//
//
////        Messages messages = gson.fromJson(response.body().string(), Messages.class);
////
////        Main.setMessages(messages);
//
////        System.out.println("response message => " + messages.toString());
//    }
//
//
//    public void RequestUpload(String filePath) throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        /**
//         * Imzolangan faylni serverga yuklash
//         */
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("files", "/" + filePath, RequestBody
//                        .create(MediaType.parse("application/pdf"), new File("/" + filePath)))
//                .build();
//
//        Request request = new Request.Builder().url(Main.getUrl() + "/api/upload")
//                .method("POST", body)
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//
//        Response response = Main.getClient().newCall(request).execute();
//
//        assert response.body() != null;
//        Upload[] uploadData = gson.fromJson(response.body().string(), Upload[].class);
//
////        System.out.println(Arrays.toString(uploadData));
//
//
//        Main.setUpload(uploadData[0]);
//
////        System.out.println("uploaded file: " + Main.getUpload());
//    }
//
//    public void RequestUsers() throws IOException {
//
////        System.out.println("getUsers => " + Main.getLoginData().getUser().getId());
//
//        /**
//         * Mavjud foydalanuvchilar haqidagi ma'lumotlarni olib keladi
//         */
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetUsers($userid:ID){usersPermissionsUsers(filters:{id:{ne: $userid}}){" +
//                "data{  id     attributes{username   email   rasm{     data{   id   attributes{  url   }   }   }  kalits{  data{  attributes{  pubkey} } } } } } } \",\"variables\":{\"userid\":" + Main.getLoginData().getUser().getId() + "}}");
//        Request request = new Request.Builder()
//                .url(Main.getUrl() + "/graphql")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
////        Response response = Main.getClient().newCall(request).execute();
//
//        Response response = Main.getClient().newCall(request).execute();
//
//        AUsers users = objectMapper.readValue(response.body().byteStream(), AUsers.class);
//
//        Main.setaUsers(users);
//
////        Main.getClient().newCall(request).enqueue(new Callback() {
////            @Override
////            public void onFailure(@NotNull Call call, @NotNull IOException e) {
////
////            }
////
////            @Override
////            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
////                AUsers users = objectMapper.readValue(response.body().byteStream(), AUsers.class);
////                Main.setaUsers(users);
////            }
////        });
//
//
////        System.out.println(users);
//
//
//    }
//
//    public void RequestUserMessages(String user_id, String me_id, int start, int limit) throws IOException {
//        /**
//         * Umumiy messagelar haqidagi ma'lumotlarni olib keladi
//         */
//
//        MediaType mediaType = MediaType.parse("application/json");
//
//        RequestBody body = RequestBody.create(mediaType, "{\"query\":\"query GetMessages($meid:ID, $userid:ID, $start:Int, $limit:Int){" +
//                "messages(pagination: {start:$start, limit:$limit}, sort: \\\"createdAt:DESC\\\", filters:{ or:[ {and:[ {from_user:{id:{eq: $meid}}}," +
//                "{to_user:{id:{eq: $userid}}} ]}, {and:[ {from_user:{id:{eq: $userid}}}, {to_user:{id:{eq: $meid}}} ]} ] } ){ data{ id attributes{ xabar" +
//                " mavzu createdAt imzo fayllar{ imzo fayl{ data{ id attributes{ url size createdAt updatedAt name mime } } } } from_user{ data{ id attributes{ kalits{ data{ id" +
//                " attributes{ pubkey } } } } } } to_user{ data{ id attributes{ kalits{ data{ id attributes{ pubkey } } } } } } } } meta{ pagination{ total" +
//                " pageCount } } } }\",\"variables\":{\"userid\":" + user_id + ",\"meid\":" + me_id + ",\"start\":" + start + ",\"limit\":" + limit + "}}");
//
//        Request request = new Request.Builder()
//                .url(Main.getUrl() + "/graphql")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//
//        /*****************************/
//
//        Response response = Main.getClient().newCall(request).execute();
//
//        UserMessages messages = objectMapper.readValue(response.body().byteStream(), UserMessages.class);
//        Main.setUserMessages(messages);
//
////        System.out.println(Main.getUserMessages().toString());
//
//    }
//
//    public void RequestKeyDel(int id) throws IOException {
//        /**
//         * Tanlangan kalitni o'chirish
//         */
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder().url(Main.getUrl() + "/api/kalits/" + id)
//                .method("DELETE", body)
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//        Response response = Main.getClient().newCall(request).execute();
//    }
//
//    public void RequestGetSignerFilesInfo(String link) throws IOException {
//        /**
//         *    Imzolangan faylni tekshirish uchun undagi ma'lumotlarni o'qib oladi;
//         * */
//        Request request = new Request.Builder()
//                .url(link)
//                .method("GET", null)
//                .addHeader("Authorization", "Bearer " + Main.getLoginData().getJwt())
//                .build();
//        Response response = Main.getClient().newCall(request).execute();
////        System.out.println(" => " + response.body().string());
//
//        VerificationInfo verification = gson.fromJson(response.body().string(), VerificationInfo.class);
//        Main.setVerification(verification);
//
////        System.out.println(Main.getVerification());
//    }

}
