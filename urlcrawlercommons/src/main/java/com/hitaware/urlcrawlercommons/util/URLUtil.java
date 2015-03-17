package com.hitaware.urlcrawlercommons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtil {

	/**
	 * Reads pattern information from out of the box tlds file.
	 *
	 *
	 * @param tldsData
	 *            pattern file input stream
	 * @return consolidated pattern information
	 * @throws IOException
	 * @since 1.0
	 */

	public static Pattern getPattern() throws IOException {
		return getPattern(URLUtil.class
				.getResourceAsStream("/com/hitaware/urlcrawlercommons/util/tlds"));
	}

	/**
	 * Reads pattern information from input stream.
	 *
	 *
	 * @param tldsData
	 *            pattern file input stream
	 * @return consolidated pattern information
	 * @throws IOException
	 * @since 1.0
	 */
	public static Pattern getPattern(InputStream tldsData) throws IOException {
		ArrayList<String> terms = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new InputStreamReader(tldsData));
		String s = null;
		while ((s = br.readLine()) != null) {
			s = s.trim();
			if (s.length() == 0 || s.startsWith("//") || s.startsWith("!"))
				continue;
			terms.add(s);
		}
		Collections.sort(terms, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if (s1.length() > s2.length())
					return -1;
				if (s1.length() < s2.length())
					return 1;
				return 0;
			}
		});
		StringBuffer sb = new StringBuffer();
		for (String t : terms) {
			t = t.replace(".", "\\.");
			t = "\\." + t;
			if (t.startsWith("*")) {
				t = t.replace("*", ".+");
				sb.append(t).append("|");
			} else {
				sb.append(t).append("|");
			}
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		sb.insert(0, "[^.]+?(");
		sb.append(")$");
		Pattern pattern = Pattern.compile(sb.toString());
		br.close();
		return pattern;
	}

	/**
	 * Extracts second level domain information from host using pattern data.
	 *
	 *
	 * @param pattern
	 *            pattern information
	 * @param host
	 *            url
	 * @return Extracted domain information
	 * @since 1.0
	 */
	public static String extract2LD(Pattern pattern, URL host) {
		Matcher m = pattern.matcher(host.getHost());
		if (m.find()) {
			return m.group(0);
		}
		return null;
	}

	/**
	 * Extracts top level domain information from host using pattern data.
	 *
	 *
	 * @param pattern
	 *            pattern information
	 * @param host
	 *            url
	 * @return Extracted domain information
	 * @since 1.0
	 */

	public static String extractTLD(Pattern pattern, URL host) {
		Matcher m = pattern.matcher(host.getHost());
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
}
