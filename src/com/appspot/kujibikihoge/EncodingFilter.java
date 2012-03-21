package com.appspot.kujibikihoge;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/* Filterインターフェイスを実装したフィルタクラスを作成する */
public final class EncodingFilter implements Filter {
    private String encoding = null;

    /* web.xmlに定義されたencoding情報を取得する */
    public void init(FilterConfig config) throws ServletException {
        /* web.xmlの<init-param>要素のencoding値を取得する */
        encoding = config.getInitParameter("encoding");
    }

    /* ブラウザから送信された情報をencodingで指定された文字コードに設定する */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        /* リクエストの文字コードを指定する */
        request.setCharacterEncoding(encoding);
        /* フィルタを実行する */
        chain.doFilter(request, response);
    }

    /* フィルタの終了処理をする */
    public void destroy() {
        encoding = null;
    }
}