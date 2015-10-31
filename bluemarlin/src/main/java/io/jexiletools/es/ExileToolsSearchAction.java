package io.jexiletools.es;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import io.searchbox.action.AbstractAction;
import io.searchbox.action.AbstractMultiTypeActionBuilder;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.sort.Sort;
import io.searchbox.params.Parameters;
import io.searchbox.params.SearchType;

public class ExileToolsSearchAction extends AbstractAction<ExileToolsSearchResult> {

	    private String query;
	    private List<Sort> sortList = new LinkedList<Sort>();

	    private ExileToolsSearchAction(ExileToolsSearchAction.Builder builder) {
	        super(builder);

	        this.query = builder.query;
	        this.sortList = builder.sortList;
	        setURI(buildURI());
	    }

	    public ExileToolsSearchResult createNewElasticSearchResult(String responseBody, int statusCode, String reasonPhrase, Gson gson) {
	        return createNewElasticSearchResult(new ExileToolsSearchResult(gson), responseBody, statusCode, reasonPhrase, gson);
	    }

	    public String getIndex() {
	        return this.indexName;
	    }

	    public String getType() {
	        return this.typeName;
	    }

	    @Override
	    protected String buildURI() {
//	        StringBuilder sb = new StringBuilder();
//	        sb.append(super.buildURI()).append("/_search");
//	        System.out.println(sb.toString());
	        return "/_search?pretty"; // TODO parametarized pretty
	    }

	    @Override
	    public String getPathToResult() {
	        return "hits/hits/_source";
	    }

	    @Override
	    public String getRestMethodName() {
	        return "POST";
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public String getData(Gson gson) {
	        String data;
	        if (sortList.isEmpty()) {
	            data = query;
	        } else {
	            List<Map<String, Object>> sortMaps = new ArrayList<Map<String, Object>>(sortList.size());
	            for (Sort sort : sortList) {
	                sortMaps.add(sort.toMap());
	            }

	            Map rootJson = gson.fromJson(query, Map.class);
	            rootJson.put("sort", sortMaps);
	            data = gson.toJson(rootJson);
	        }
	        return data;
	    }

	    @Override
	    public int hashCode() {
	        return new HashCodeBuilder()
	                .appendSuper(super.hashCode())
	                .append(query)
	                .toHashCode();
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (obj == this) {
	            return true;
	        }
	        if (obj.getClass() != getClass()) {
	            return false;
	        }

	        ExileToolsSearchAction rhs = (ExileToolsSearchAction) obj;
	        return new EqualsBuilder()
	                .appendSuper(super.equals(obj))
	                .append(query, rhs.query)
	                .append(sortList, rhs.sortList)
	                .isEquals();
	    }
	    
	    @Override
	    protected JsonObject parseResponseBody(String responseBody) {
	        if (responseBody != null && !responseBody.trim().isEmpty()) {
	        		JsonReader jsonReader = new JsonReader(new StringReader(responseBody));
	        		jsonReader.setLenient(true);
	                return new JsonParser().parse(jsonReader).getAsJsonObject();
	        }
	        return new JsonObject();
	    }

	    public static class Builder extends AbstractMultiTypeActionBuilder<ExileToolsSearchAction, ExileToolsSearchAction.Builder> {
	        private String query;
	        private List<Sort> sortList = new LinkedList<Sort>();

	        public Builder(String query) {
	            this.query = query;
	        }

	        public ExileToolsSearchAction.Builder setSearchType(SearchType searchType) {
	            return setParameter(Parameters.SEARCH_TYPE, searchType);
	        }

	        public ExileToolsSearchAction.Builder addSort(Sort sort) {
	            sortList.add(sort);
	            return this;
	        }

	        public ExileToolsSearchAction.Builder addSort(Collection<Sort> sorts) {
	            sortList.addAll(sorts);
	            return this;
	        }

	        @Override
	        public ExileToolsSearchAction build() {
	            return new ExileToolsSearchAction(this);
	        }
	    }
	}