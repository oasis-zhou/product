package rf.product.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class AuthorizationHelper {

	public String generateAccessKey() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
	
	public String generateSecretKey() {
		return RandomStringUtils.randomAlphanumeric(32);
	}

	public String sign(String secretKey, String content, String charset,
			String signType) {
		if("MD5".equals(signType)) {
			content = content + secretKey;
			return DigestUtils.md5Hex(getContentBytes(content, charset));
		} else {
			throw new RuntimeException("Unsupported signature type.");
		}
	}
	
	public boolean verify(String secretKey, String content, String chartset, 
			String signType, String signature) {
		return sign(secretKey, content, chartset, signType).equals(signature);
	}

	public String createLinkString(Map<String, String> params) {
		StringBuffer linkString = new StringBuffer();

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		for(String key : keys) {
			String value = params.get(key);
			linkString.append(String.format("%s=%s&", key, value));
		}

		return linkString.deleteCharAt(linkString.length() - 1).toString();
	}
	

	private byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

}
