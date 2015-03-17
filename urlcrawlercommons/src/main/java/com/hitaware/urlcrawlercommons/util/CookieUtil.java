package com.hitaware.urlcrawlercommons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void setCookie(HttpServletResponse response,
			String cookieName, String value) {
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//
		}
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setMaxAge(1 * 3600 * 24 * 365 * 10);
		response.addCookie(cookie);
	}

	public static String getCookie(HttpServletRequest request, String cookieName) {
		if (request.getCookies() != null) {
			for (int i = 0; i < request.getCookies().length; i++) {
				Cookie cookie = request.getCookies()[i];
				if (cookieName.equals(cookie.getName())) {
					String value = null;
					try {
						value = URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						//
					}
					return (value);
				}
			}
		}
		return null;
	}

}
