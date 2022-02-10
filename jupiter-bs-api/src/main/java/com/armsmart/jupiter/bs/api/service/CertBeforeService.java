package com.armsmart.jupiter.bs.api.service;

import com.allinpay.sdk.OpenClient;
import com.allinpay.sdk.bean.OpenConfig;
import com.armsmart.jupiter.bs.api.tlpay.TlPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Slf4j
@Service
public class CertBeforeService {
    protected OpenClient client;

    public CertBeforeService(){
//        /**
//         * Dev
//         */
        final String url = "https://cloud.allinpay.com/gateway";
        final String appId = "1469220498779484162";
        final String secretKey = "wht6zLZRaydIEe7a9v6DSRdUO6M8tcI8";
        final String pwd = "484162";
//        final String privateKeyPath = "E:\\tl_pfx\\1469220498779484162.pfx";
		final String privateKeyPath = "/usr/local/jupiter/jupiter-bs/config/1469220498779484162.pfx";
//        final String tlPublicKey = "E:\\tl_pfx\\TLCert.cer";
        final String tlPublicKey = "/usr/local/jupiter/jupiter-bs/config/TLCert.cer";
        /**
         * Test
         */
//        final String url = "http://test.allinpay.com/op/gateway";
//		final String appId = "1283233906081939458";
//		final String secretKey = "eMDVxpfvt1k8mIiCZvDLWx9wtJcdtVBQ";
////		final String privateKeyPath = "E:\\tl_pfx\\1283233906081939458.pfx";
//        final String privateKeyPath = "/usr/local/jupiter/jupiter-bs/config/tl_test/1283233906081939458.pfx";
//		final String pwd = "939458";
////		final String tlPublicKey = "E:\\tl_pfx\\TLCert-test.cer";
//        final String tlPublicKey = "/usr/local/jupiter/jupiter-bs/config/tl_test/TLCert-test.cer";
            final OpenConfig oc = new OpenConfig(url, appId, secretKey, privateKeyPath, pwd, tlPublicKey);
            try {
                client = new OpenClient(oc);
            } catch (final Exception e) {
                e.printStackTrace();
            }

//        }
    }


    /**
     * 打开默认浏览器，支持苹果和windows
     *
     * @param url
     */
    protected void browser(final String url) {
        try {
            final String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler  " + url);
            } else if (osName.startsWith("Mac OS")) {
                final Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
                final Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
                openURL.invoke(null, new Object[] { url });
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
