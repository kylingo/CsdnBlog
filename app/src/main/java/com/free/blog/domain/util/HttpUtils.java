package com.free.blog.domain.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {
    private static String cookieName = "";
    private static String cookieValue = "";
    private static String hostBase = "";

    @SuppressWarnings("unused")
    public static String httpGetHost(String url) {
        HttpGet httpget = new HttpGet(url);
        String strResult = "";
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        HttpClient httpclient;
        try {
            httpclient = new DefaultHttpClient(httpParams);
            HttpResponse response = httpclient.execute(httpget);
            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(response.getEntity(),
                        HTTP.UTF_8);
            }
        } catch (ConnectException e) {
            e.printStackTrace();
            System.out.println("host error");
        } catch (ClientProtocolException e) {
            System.out.println("Client");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO");
            e.printStackTrace();
        }
        return strResult;
    }

    public static String httpGet(String url) {
        System.out.println("httpGet" + url);
        HttpGet httpget = new HttpGet(url);
        String strResult = null;
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);

        HttpClient httpclient;
        try {
            httpclient = new DefaultHttpClient(httpParams);
            httpget.setHeader("Cookie", cookieName + "=" + cookieValue);
            HttpResponse response = httpclient.execute(httpget);
            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(response.getEntity(),
                        HTTP.UTF_8);
                // System.out.println(strResult);
                System.out.println("getFinish");
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            System.out.println("Client");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO");
            e.printStackTrace();
        }
        return strResult;
    }

    @SuppressWarnings("unused")
    public static String httpGetNoResult(String url) {
        System.out.println("httpGetNo" + url);
        HttpGet httpget = new HttpGet(hostBase + url);
        String strResult = "";
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);

        HttpClient httpclient;
        try {
            httpclient = new DefaultHttpClient(httpParams);
            httpget.setHeader("Cookie", cookieName + "=" + cookieValue);
            HttpResponse response = httpclient.execute(httpget);
            System.out.println(response.getStatusLine().getStatusCode());
            /*
			 * if(response.getStatusLine().getStatusCode() == 200){ strResult =
			 * EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			 * System.out.println(strResult); System.out.println("getFinish"); }
			 */
        } catch (Exception e) {
            e.printStackTrace();
            strResult = "error";
        }
        return strResult;
    }

    @SuppressWarnings("unused")
    public static Bitmap HttpGetBmp(String url) {
        HttpGet httpget = new HttpGet(url);
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        Bitmap bitmap = null;
        try {
            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpResponse response = httpclient.execute(httpget);
            InputStream is = response.getEntity().getContent();
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int count;
            while ((count = is.read(bytes)) != -1) {
                System.out.println("readBitmap");
                bos.write(bytes, 0, count);
            }
            byte[] byteArray = bos.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length);
            is.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @SuppressWarnings("unused")
    public static InputStream HttpGetBmpInputStream(String url) {
        HttpGet httpget = new HttpGet(url);
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        InputStream is = null;
        try {
            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpResponse response = httpclient.execute(httpget);
            is = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    @SuppressWarnings("unused")
    public static Integer GetCookie(String url, String number, String pw,
                                    String select, String host) {
        System.out.println("GetCookie");
        int result = 4;
        HttpPost httpPost = new HttpPost(hostBase + url);
        List<NameValuePair> vps = new ArrayList<NameValuePair>();
        vps.add(new BasicNameValuePair("number", number));
        vps.add(new BasicNameValuePair("passwd", pw));
        vps.add(new BasicNameValuePair("select", select));
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            httpClient.setRedirectHandler(new RedirectHandler() {

                @Override
                public boolean isRedirectRequested(HttpResponse response,
                                                   HttpContext context) {
                    return false;
                }

                @Override
                public URI getLocationURI(HttpResponse response,
                                          HttpContext context) throws ProtocolException {
                    return null;
                }
            });
            httpPost.setEntity(new UrlEncodedFormEntity(vps, HTTP.UTF_8));
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                return 2;
            } else if (response.getStatusLine().getStatusCode() == 302) {
                Header[] headers = response.getHeaders("Location");
                if (headers != null && headers.length > 0) {
                    List<Cookie> list = httpClient.getCookieStore()
                            .getCookies();
                    for (Cookie c : list) {
                        cookieName = c.getName();
                        cookieValue = c.getValue();
                    }
                    System.out.println(cookieName + cookieValue);
                    return 3;
                }
            } else if (response.getStatusLine().getStatusCode() == 404) {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings("unused")
    public static String httpGetCookie(String url) {
        System.out.println("httpGetCookie" + url);
        HttpGet httpget = new HttpGet(hostBase + url);
        String strResult = "";
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
        HttpConnectionParams.setSoTimeout(httpParams, 15000);
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            httpClient.setRedirectHandler(new RedirectHandler() {

                @Override
                public boolean isRedirectRequested(HttpResponse response,
                                                   HttpContext context) {
                    return false;
                }

                @Override
                public URI getLocationURI(HttpResponse response,
                                          HttpContext context) throws ProtocolException {
                    return null;
                }
            });
            httpget.setHeader("Cookie", cookieName + "=" + cookieValue);
            HttpResponse response = httpClient.execute(httpget);
            if (response.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(response.getEntity(),
                        HTTP.UTF_8);
            } else if (response.getStatusLine().getStatusCode() == 302) {
                strResult = "302"; // cookieʧЧ�������ض����־�������µ�¼��ȡ
            } else if (response.getStatusLine().getStatusCode() == 404) {
                strResult = "-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            strResult = "4";
        }
        return strResult;
    }

    @SuppressWarnings("unused")
    public static String httpPostCookie(String url, String id, String data) {
        System.out.println("httpPostCookie" + url);
        String result = "4";
        HttpPost httpPost = new HttpPost(hostBase + url);
        List<NameValuePair> vps = new ArrayList<NameValuePair>();
        vps.add(new BasicNameValuePair("marc_no", id));
        vps.add(new BasicNameValuePair("r_content", data));
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
            httpClient.setRedirectHandler(new RedirectHandler() {

                @Override
                public boolean isRedirectRequested(HttpResponse response,
                                                   HttpContext context) {
                    return false;
                }

                @Override
                public URI getLocationURI(HttpResponse response,
                                          HttpContext context) throws ProtocolException {
                    return null;
                }
            });
            httpPost.setHeader("Cookie", cookieName + "=" + cookieValue);
            httpPost.setEntity(new UrlEncodedFormEntity(vps, HTTP.UTF_8));
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity(),
                    HTTP.UTF_8) + "add");
            if (response.getStatusLine().getStatusCode() == 200) {
                return "2";
            } else if (response.getStatusLine().getStatusCode() == 302) {
                Header[] headers = response.getHeaders("Location");
                if (headers != null && headers.length > 0) {
                    System.out.println(headers[0].getValue());
                    return "3";
                }
            } else if (response.getStatusLine().getStatusCode() == 404) {
                return "-1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings("unused")
    public static int getCookie(String url) {
        System.out.println("getCookie" + url);
        HttpGet httpGet = new HttpGet(hostBase + url);
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.setRedirectHandler(new RedirectHandler() {

                @Override
                public boolean isRedirectRequested(HttpResponse response,
                                                   HttpContext context) {
                    return false;
                }

                @Override
                public URI getLocationURI(HttpResponse response,
                                          HttpContext context) throws ProtocolException {
                    return null;
                }
            });
            HttpResponse response = httpClient.execute(httpGet);
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity(),
                    HTTP.UTF_8) + "add");
            if (response.getStatusLine().getStatusCode() == 200) {
                Header[] heads = response.getAllHeaders();
                System.out.println(heads.length);
                for (Header header : heads) {
                    System.out.println(header.getName() + " = "
                            + header.getValue());
                }
                return 2;
            } else if (response.getStatusLine().getStatusCode() == 302) {
                Header[] headers = response.getHeaders("Location");
                if (headers != null && headers.length > 0) {
                    System.out.println(headers[0].getValue());
                    return 3;
                }
            } else if (response.getStatusLine().getStatusCode() == 404) {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
