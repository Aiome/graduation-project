package top.aiome.util;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Title: curriculum-design<br>
 * Description: <br>
 * Copyright: Copyright (c) 2018<br>
 * Company: 北京云杉世界信息ji术有限公司<br>
 *
 * @author mahongyan 2018/10/3 22:47
 */
public class HttpClientPool {
    private static PoolingHttpClientConnectionManager cm = null;
    static{
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(1000);
        cm.setDefaultMaxPerRoute(1000);
    }
    public static CloseableHttpClient getHttpClient(){
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig).build();
        return client;
    }
}
