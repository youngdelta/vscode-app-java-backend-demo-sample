package web.example.app.config;


import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CustomRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> additionalParams;

    public CustomRequestWrapper(HttpServletRequest request) {
        super(request);
        additionalParams = new HashMap<>();
    }

    /**
     * 
     * @param name
     * @param value
     */
    public void addParameter(String name, String value) {
        additionalParams.put(name, new String[]{value});
    }

    @Override
    public String getParameter(String name) {
        String[] value = additionalParams.get(name);
        if (value != null) {
            return value[0];
        }
        return super.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = additionalParams.get(name);
        if (values != null) {
            return values;
        }
        return super.getParameterValues(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> set = new HashSet<>();
        set.addAll(additionalParams.keySet());

        Enumeration<String> e = ((HttpServletRequest) getRequest()).getParameterNames();
        while (e.hasMoreElements()) {
            set.add(e.nextElement());
        }

        return Collections.enumeration(set);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new HashMap<>();
        // 기본 요청의 파라미터 맵을 모두 복사
        map.putAll(super.getParameterMap());
        // 추가된 파라미터들을 덮어쓰기 (또는 추가)
        map.putAll(additionalParams);
        return Collections.unmodifiableMap(map);
    }
}