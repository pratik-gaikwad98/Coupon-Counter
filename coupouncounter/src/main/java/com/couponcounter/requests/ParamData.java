package com.couponcounter.requests;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;

@Component
public class ParamData {
	
	@JsonAlias("fields")
	private List<String> fields;
	
	@JsonAlias("limit")
    private int limit;
	
	@JsonAlias("offset")
    private int offset;
	
	@JsonAlias("sorting")
    private Sorting sorting;
	
	@JsonAlias("filter_params")
	private FilterParams filterParams;
	
    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public void setSorting(Sorting sorting) {
        this.sorting = sorting;
    }

    public FilterParams getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(FilterParams filterParams) {
        this.filterParams = filterParams;
    }
}
