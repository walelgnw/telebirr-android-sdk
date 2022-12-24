package com.vintechplc.telebirr.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vintechplc.telebirr.logs.SessionLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;


public class EncryptUtils {

    private final static String TAG = "Encryption";
  // rsa 2048
    private int RSA_PRIVATE_ENCRYPT_MAX_SIZE = 256031916;


    private final int MAX_ENCRYPT_BLOCK = 117;

    private final String KEY_ALGORITHM = "RSA";

    private final String TYPE_ENCRYRT = "RSA/None/PKCS1Padding";

    private EncryptUtils() {

    }

    private static final class MInstanceHolder {
        static final EncryptUtils mInstance = new EncryptUtils();
    }

    public static EncryptUtils getInstance() {
        return MInstanceHolder.mInstance;
    }


    String encryptSHA256(Object obj, String appKey) {
        JSONObject jsonObject = objectToJson(obj);
        TreeMap<String, String> dataOptMap = JsonToMap(jsonObject);
        dataOptMap.put("appKey", /*PRODUCTION_APP_KEY*/appKey);
        //String key= new String(Base64.decode(KEY.getBytes(), Base64.DEFAULT));
        String signFirst = getUrlParamsByMap(dataOptMap);
      SessionLogger.log(TAG, "encryptSHA256 sign first " + signFirst);
        String sign = getSHA256Str(signFirst).toUpperCase();
       SessionLogger.log(TAG, "encryptSHA256 sign end " + sign);
        return sign;
    }

    private StringBuffer addParameter(StringBuffer sb, String key, String value) {
        sb.append("&");
        sb.append(key);
        sb.append("=");
        sb.append(value);
        return sb;
    }

    <T> JSONObject objectToJson(T obj) {
        if (null == obj) {
            return new JSONObject();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(obj);
            return new JSONObject(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }


    <T> String objectToJsonString(T obj) {
        if (null == obj) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public <T> Object JSONToObj(String jsonStr, Class<T> obj) {
        T t = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (TextUtils.isEmpty(jsonStr)) {
                return t;
            }
            t = objectMapper.readValue(jsonStr, obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    private String getSHA256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


    public TreeMap<String, String> JsonToMap(JSONObject j) {
        SessionLogger.log(TAG, "JsonToMap");
        TreeMap<String, String> parameters = new TreeMap<String, String>();
        if (j == null) {
            SessionLogger.log(TAG, "JsonToMap object is null");
            return parameters;
        }
        try {
            Iterator<String> iterator = j.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object obj = j.get(key);
                String value = String.valueOf(obj);
                if (TextUtils.isEmpty(value.trim()) || value.equals("null")) {
                    continue;
                }
                parameters.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parameters;
    }

    private String getUrlParamsByMap(Map<String, String> map) {
        SessionLogger.log(TAG, "getUrlParamsByMap ");
        if (map == null || map.isEmpty()) {
            SessionLogger.log(TAG, "getUrlParamsByMap object is null");
            return "";
        }
        SessionLogger.log(TAG, "getUrlParamsByMap  map size " + map.size());
        StringBuilder sb = new StringBuilder();

        for (Map.Entry entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    String encryptByPublicKey(String data, String publicKey) throws Exception {
        //return Base64.encodeToString(encryptByPublicKey(data.getBytes(), PUBLIC_KEY), Base64.NO_WRAP);
        return Base64.encodeToString(encryptByPublicKey(data.getBytes(), publicKey/*PRODUCTION_PUBLIC_KEY*/), Base64.NO_WRAP);
    }

    private byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decode(publicKey, Base64.NO_WRAP);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(TYPE_ENCRYRT);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
}