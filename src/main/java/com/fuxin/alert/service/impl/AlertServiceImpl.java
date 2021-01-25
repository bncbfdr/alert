package com.fuxin.alert.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.fuxin.alert.domain.AlarmInfo;
import com.fuxin.alert.domain.PlatformAlert;
import com.fuxin.alert.domain.alert;
import com.fuxin.alert.repository.AlarmInfoRepository;
import com.fuxin.alert.repository.PlatformAlertRepository;
import com.fuxin.alert.service.AlertService;
import com.fuxin.alert.web.rest.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

/**
 * @author Gao
 * @date 2020/9/27 0:44
 * @description 主执行程序
 */
@Service
public class AlertServiceImpl implements AlertService {
    public static Logger logger = LoggerFactory.getLogger(AlertServiceImpl.class);
   // private static  Date x;
   final static ZoneId systemDefault = ZoneId.systemDefault();

    @Autowired
    private ApplicationArguments applicationArguments;

    @Autowired
    private AlertService alertService;

    @Autowired
    private AlarmInfoRepository alarmInfoRepository;

    @Autowired
    private PlatformAlertRepository platformAlertRepository;
    /**
     *
     * @param args 参数1 发送url 参数2 告警平台编号 参数3 告警id存放路径
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     * 读取数据库的告警数据发送给阜新银行告警平台
     */
    public List getRizhiAlert() throws IOException {

        List<String> nonOptionArgs = applicationArguments.getNonOptionArgs();
        String url = nonOptionArgs.get(0);
        String number = nonOptionArgs.get(1);
        String path = nonOptionArgs.get(2);
        logger.debug("url:" + url + "\n平台编号：" + number + "\n数据库偏移量：" + path);

        JSONObject jo = alertService.readOffset(path);
        String rizhi = jo.getString("rizhi");
        long offset = Long.parseLong(rizhi);

        List<AlarmInfo> ai = alarmInfoRepository.getAlert(offset);
        ArrayList<Long> list = new ArrayList<>();
        for (int i = 0; i < ai.size(); i++) {
            PlatformAlert alert = new PlatformAlert();
            AlarmInfo alarmInfo =  ai.get(i);
            Long id = alarmInfo.getId();
            list.add(id);
            String name = alarmInfo.getName();
            String info = alarmInfo.getInfo();
            JSONObject jsonObject = JSONObject.parseObject(info);
            String message = jsonObject.getString("@message");
            Instant createdTime = alarmInfo.getCreatedTime();
            alert.setId(id);
            alert.setTitle(name);
            alert.setHappenTime(createdTime.toString().replace("T"," ").replace("Z",""));
            alert.setDesc(message);
            alert.setEventStatus("1");
            logger.debug(alert.toString());
            SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String format = yyyyMMddHHmmssSSS.format(new Date());
            String encode = alertService.encode(format + "-"+number);
            String u = url+"?token="+encode + "&id=" + URLEncoder.encode("01-"+alert.getId()) + "&eventStatus=" + URLEncoder.encode(alert.getEventStatus()) + "&title=" + URLEncoder.encode(alert.getTitle()) + "&desc=" + URLEncoder.encode(alert.getDesc()) + "&happenTime=" + URLEncoder.encode(alert.getHappenTime()) ;
            //logger.debug("url:"+u + "\n");
            Boolean judge = alertService.judge(u, new JSONObject());

        }

        Collections.sort(list);
        if(list.isEmpty()){

        } else{
            Long id = list.get(list.size() - 1);
            jo.put("rizhi",String.valueOf(id));
            alertService.wirteOffset(path,jo.toJSONString());
        }
        return list;

    }


    /**
     *
     * @param url 地址
     * @param param 参数
     * @return 根据接收的数据是否发送成功
     */
    @Override
    public  Boolean judge(String url,JSONObject param){
        int num = 0;
        JSONObject result = HttpClient.doPost(url, param);
        String status = result.getString("id");
        if(status.equalsIgnoreCase("200")){
            logger.debug("发送消息成功");
            return true;
        }else if(status.equals("201") || status.equals("202") || status.equals("203") || status.equals("204")){
            logger.error("错误信息：" + result.getString("title"));
        }else {
            logger.warn("未知的状态码"+status);
                JSONObject two = HttpClient.doPost(url, param);
            String twoStatus = two.getString("id");
            if (twoStatus.equals("200")){
                    return true;
                }else if(twoStatus.equals("201") || twoStatus.equals("202") || twoStatus.equals("203") || twoStatus.equals("204")){
                    return false;
                }else{
                JSONObject three = HttpClient.doPost(url, param);
                if(three.getString("id").equals("200")){
                    return true;
                }else {
                    return false;
                }
            }

        }

        return true;
    }


    /**
     *
     * @param path 告警id存放路径
     * @return 读取保存的偏移量
     * @throws IOException
     */
    @Override
    public JSONObject readOffset(String path) throws IOException {

    FileInputStream fileInputStream = new FileInputStream(path);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
    String line = null;
    Long offset = 0L;
        JSONObject jsonObject = new JSONObject();
        while ((line = bufferedReader.readLine()) != null){
       // offset = Long.parseLong(line);
         jsonObject = JSONObject.parseObject(line);
    }
    return jsonObject;
    }

    /**
     *  写入数据库告警信息offset
     * @param path 告警id存放路径
     * @param offset 偏移量
     */
    @Override
    public  void wirteOffset(String path,String offset){
        try{
            File file =new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(path,false);
            fileWritter.write(offset);

            fileWritter.close();
            logger.debug("偏移量写入成功");

        }catch(IOException e){
            e.printStackTrace();

        }
    }

    /**
     *
     * @param str 源字符串
     * @return 对字符串进行base64编码后的数据
     * @throws UnsupportedEncodingException
     */
    @Override
    public  String encode(String str) throws UnsupportedEncodingException {
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = str.getBytes("UTF-8");
        String encodedText = encoder.encodeToString(textByte);
        logger.debug("token加密后为"+encodedText);
        //解码
        //System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
        return encodedText;

    }

    @Override
    public List<PlatformAlert> getSuanfaAlert() throws IOException {
        List<String> nonOptionArgs = applicationArguments.getNonOptionArgs();
        String url = nonOptionArgs.get(0);
        String number = nonOptionArgs.get(1);
        String path = nonOptionArgs.get(2);
        logger.debug("url:" + url + "\n平台编号：" + number + "\n数据库偏移量：" + path);
        JSONObject jo = alertService.readOffset(path);
        String rizhi = jo.getString("suanfa");
        long offset = Long.parseLong(rizhi);
        List<PlatformAlert> alert = platformAlertRepository.getAlert(offset);
        ArrayList<Long> list = new ArrayList<>();
        for (int i = 0; i < alert.size(); i++) {
            PlatformAlert platformAlert =  alert.get(i);
            list.add(platformAlert.getId());
            SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String format = yyyyMMddHHmmssSSS.format(new Date());
            String encode = alertService.encode(format + "-"+number);
            String u = url+"?token="+encode + "&id=" + URLEncoder.encode("02-"+platformAlert.getId()) + "&eventStatus=" + URLEncoder.encode(platformAlert.getEventStatus()) + "&title=" + URLEncoder.encode(platformAlert.getTitle()) + "&desc=" + URLEncoder.encode(platformAlert.getDesc()) + "&happenTime=" + URLEncoder.encode(platformAlert.getHappenTime()) ;
            //logger.debug("url:"+u + "\n");
            Boolean judge = alertService.judge(u, new JSONObject());
        }

        Collections.sort(list);
        if(list.isEmpty()){

        } else{
            Long id = list.get(list.size() - 1);
            jo.put("suanfa",String.valueOf(id));
            alertService.wirteOffset(path,jo.toJSONString());
        }
        return alert;
    }

}
