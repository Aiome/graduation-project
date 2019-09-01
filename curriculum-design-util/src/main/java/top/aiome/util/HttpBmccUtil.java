package top.aiome.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpBmccUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpBmccUtil.class);

    /**
     * 充值页面保持登录状态
     *
     * @auther: mahongyan
     * @date: 2018/10/8 19:31
     * @param: [phone, cookie]
     * @return:
     */
    public static Map<String,String> loginRefresh(String phone, String cookie) throws Exception {
        Map<String, String> result = new HashMap<>();
        Map<String,String> param = new HashMap<>();
        param.put("reqUrl","coupon");
        param.put("method","list");
        param.put("extendParams","&&public_path=/app/ecu/resource/recharge/html/index.html&&session_sTSSOPHONE=null&session_wTSSOPHONE=null&session_mdl_PHONE=null");

        if (cookie == null){
            throw new Exception(phone + "Refresh Cookie:null失败");
        }
        CloseableHttpResponse response = HttpBmccUtil.post("http://he.10086.cn/app/ecu/ecuAction.do", param, cookie);
        JSONObject object = JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));

        logger.info("loginRefresh  phone: " + phone + " result: " + object.toJSONString());

        Header[] headers = response.getHeaders("Set-Cookie");
        String setCookie = "";
        try {
            for (int i = 0; i < 3; i++) {
                setCookie += headers[i].getValue().split(";")[0] +"; ";
            }
        } catch (Exception e){

        }


        result.put("setCookie",setCookie);

        return result;
    }

    public static Map<String ,String> getLqCookie(String url) throws Exception {
        Map<String, String> result = new HashMap<>();

        CloseableHttpResponse response = HttpBmccUtil.doGet(url, null);

        String string = EntityUtils.toString(response.getEntity(), "utf-8");
        int a = string.indexOf("_csrf") + 9;
        String re = string.substring(a, a+56);
        if (string.indexOf("系统繁忙") > 0){
            throw new Exception("限制用户！");
        }

        Header[] headers = response.getHeaders("Set-Cookie");
        String setCookie = "";
        try {
            for (int i = 0; i < 3; i++) {
                setCookie += headers[i].getValue().split(";")[0] +"; ";
            }
        } catch (Exception e){

        }

        result.put("csrf",re);
        result.put("setCookie",setCookie);

        return result;
    }

    /**
     * 参数是登录结束返回的cookie
     *
     * @auther: mahongyan
     * @date: 2018/10/6 16:54
     * @param: [cookie]
     * @return:
     */
    public static Map<String,String> mdl(String phone, String cookie) throws Exception {
        Map<String, String> result = new HashMap<>();
        Map<String,String> param = new HashMap<>();
        param.put("reqUrl","otherLinks");
        param.put("method","oc");
        param.put("extendParams","oc=mj001&&public_path=/app/ecu/resource/otherlinks/html/index.html&&session_sTSSOPHONE=null&session_wTSSOPHONE=null&session_mdl_PHONE=null");

        if (cookie == null){
            throw new Exception(phone + "初始化Cookie:null失败");
        }
        CloseableHttpResponse response = HttpBmccUtil.post("http://he.10086.cn/app/ecu/ecuAction.do", param, cookie);
        JSONObject object = JSON.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));

        logger.info("mdl  phone: " + phone + " result: " + object.toJSONString());

        result.put("bmccResult", object.toJSONString());
        result.put("verifyStr",object.getString("resultObj"));
        return result;
    }

    public static Map<String,String> login(String phone, String smsCode) throws Exception {
        Map<String, String> result = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("reqUrl","login");
        param.put("method","login");
        param.put("type","1");
        param.put("mobile",phone);
        param.put("smsCode",smsCode);

        CloseableHttpResponse response = HttpBmccUtil.post("http://he.10086.cn/app/ecu/ecuAction.do", param, null);
        Header[] headers = response.getHeaders("Set-Cookie");
        String setCookie = "";
        try {
            for (int i = 0; i < 3; i++) {
                setCookie += headers[i].getValue().split(";")[0] +"; ";
            }
        } catch (Exception e){

        }


        try {
            result.put("bmccResult",EntityUtils.toString(response.getEntity(), "utf-8"));
            result.put("setCookie",setCookie);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static CloseableHttpResponse doGet(String url, Map<String, String> param) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            return response = httpclient.execute(httpGet);
        } catch (Exception e) {
            throw e;
        }
    }


    public static CloseableHttpResponse post(String url, Map<String, String> param, String cookie) throws Exception{
        CloseableHttpClient httpClient = HttpClientPool.getHttpClient();
        CloseableHttpResponse response = null;
        HttpClientContext context = HttpClientContext.create();
        try {
            HttpPost httpPost = new HttpPost(url);
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }

            if (null != cookie){
                httpPost.setHeader("Cookie",cookie);
                httpPost.setHeader("User-Agent","hbmcc_android Mozilla/5.0 (Linux; Android 5.1.1; SM-G9350 Build/LMY48Z) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/39.0.0.0 Safari/537.36");
            }


            return response = httpClient.execute(httpPost);
        } catch (Exception e) {
           throw e;
        }
    }


    public static Map<String,String> doPost(String url, Map<String, String> param, String cookie) throws Exception {
        CloseableHttpResponse response = HttpBmccUtil.post(url, param, cookie);
        Map<String, String> result = new HashMap<>();
        try {
            result.put("bmccResult",EntityUtils.toString(response.getEntity(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}