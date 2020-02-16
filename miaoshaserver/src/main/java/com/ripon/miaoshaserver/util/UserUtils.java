package com.ripon.miaoshaserver.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ripon.miaoshaserver.dao.MiaoShaUserMapper;
import com.ripon.miaoshaserver.domain.MiaoShaUser;
import com.ripon.miaoshaserver.vo.LoginVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class UserUtils {
    @Autowired
    MiaoShaUserMapper miaoShaUserMapper;

    public void createUser(int count) throws Exception{
        List<MiaoShaUser> userList = new ArrayList<>(count);
        for (int i=0;i<count; i++) {
            MiaoShaUser user = new MiaoShaUser();
            user.setId(13000000000L+i);
            user.setNickname("user"+i);
            String dbSalt = RandomStringUtils.random(6,true,true);
            user.setSalt(dbSalt);
            String formPass = Md5Utils.inputPassToFormPassword("123456");
            String dbPassword = Md5Utils.addSalt(formPass, dbSalt);
            user.setPassword(dbPassword);
            user.setRegisterTime(new Date());
            user.setLoginCount(0);
            userList.add(user);
            miaoShaUserMapper.insertSelective(user);
        }
        String urlStr = "http://localhost:8080/user/login";
        File file = new File("C:\\Users\\29040\\Desktop\\tokens.txt");
        FileOutputStream fos = new FileOutputStream(file);
        for (MiaoShaUser user : userList) {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            OutputStream os = connection.getOutputStream();
            LoginVO loginVO = new LoginVO();
            loginVO.setMobile(user.getId().toString());
            String password = Md5Utils.inputPassToFormPassword("123456");
            loginVO.setPassword(password);
            String jsonStr = JSONObject.toJSONString(loginVO);
            os.write(jsonStr.getBytes());
            os.flush();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                byte [] buffer = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }
                inputStream.close();
                bos.close();
                String response = new String(bos.toByteArray());
                JSONObject jsonObject = JSON.parseObject(response);
                String token = jsonObject.getJSONObject("data").getString("token");
                fos.write(token.getBytes());
                String n= "\n";
                fos.write(n.getBytes());
                fos.flush();
            }
        }
        fos.close();
    }
}
