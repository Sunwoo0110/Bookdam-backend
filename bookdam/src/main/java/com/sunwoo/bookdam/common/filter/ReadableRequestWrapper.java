package com.sunwoo.bookdam.common.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ReadableRequestWrapper extends HttpServletRequestWrapper {

    private final Charset encoding;
    private byte[] rawData;
    private final Map<String, String[]> params = new HashMap<>();

    public ReadableRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());

        String charEncoding = request.getCharacterEncoding();
        this.encoding = (charEncoding == null || charEncoding.isBlank())
                ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);

        try {
            InputStream is = request.getInputStream();
            this.rawData = is.readAllBytes();

            // body 파싱 (JSON만)
            String body = new String(this.rawData, this.encoding);
            if (!body.isBlank()
                    && (request.getContentType() != null && !request.getContentType().toLowerCase().contains("multipart/form-data"))) {
                // 간단한 JSON 파싱 (Jackson 등으로 대체 가능)
                Map<String, Object> jsonMap = parseJsonToMap(body);
                if (jsonMap != null) {
                    for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                        setParameter(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("ReadableRequestWrapper Error", e);
        }
    }

    @Override
    public String getParameter(String name) {
        String[] paramArray = getParameterValues(name);
        return (paramArray != null && paramArray.length > 0) ? paramArray[0] : null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] dummyParamValue = params.get(name);
        if (dummyParamValue != null) {
            return Arrays.copyOf(dummyParamValue, dummyParamValue.length);
        }
        return null;
    }

    public void setParameter(String name, String value) {
        setParameter(name, new String[]{value});
    }

    public void setParameter(String name, String[] values) {
        params.put(name, values);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData != null ? this.rawData : new byte[0]);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) { }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }

    // 아주 간단한 JSON 문자열 -> Map 변환 (실제 서비스에서는 Jackson ObjectMapper 사용 추천)
    private Map<String, Object> parseJsonToMap(String json) {
        try {
            // 여기는 org.json, org.json.simple 대신 Jackson 등 사용 권장
            // 단순 Map 변환 예시 (build.gradle에 com.fasterxml.jackson.core:jackson-databind 추가 필요)
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            log.warn("JSON parse error in ReadableRequestWrapper: {}", e.getMessage());
            return null;
        }
    }
}
