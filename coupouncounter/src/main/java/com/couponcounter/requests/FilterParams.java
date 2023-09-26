package com.couponcounter.requests;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class FilterParams {

    @JsonProperty("brand")
    @JsonAlias("brand")
    private List<String> brandName;

    public FilterParams() {

    }


    public FilterParams(List<String> brandName) {
        this.brandName = brandName;
    }


    public List<String> getBrandName() {
        return brandName;
    }

    public void setBrandName(List<String> brandName) {
        this.brandName = brandName;
    }

}
