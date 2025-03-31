package com.study.project.settings.form;

import com.study.project.domain.Zone;
import lombok.Data;

@Data
public class ZoneForm {

    private String zoneName;

    public String getCityName() {
        if (zoneName == null || !zoneName.contains("(")) {
            return zoneName; // 괄호가 없으면 전체 문자열 반환
        }
        return zoneName.substring(0, zoneName.indexOf("(")).trim();
    }

    public String getProvinceName() {
        if (zoneName == null || !zoneName.contains("/")) {
            return "";
        }
        return zoneName.substring(zoneName.indexOf("/") + 1).trim();
    }

    public String getLocalNameOfCity() {
        if (zoneName == null || !zoneName.contains("(") || !zoneName.contains(")")) {
            return "";
        }
        return zoneName.substring(zoneName.indexOf("(") + 1, zoneName.indexOf(")")).trim();
    }

    public Zone getZone() {
        return Zone.builder()
                .city(this.getCityName())
                .localNameOfCity(this.getLocalNameOfCity())
                .province(this.getProvinceName())
                .build();
    }
}