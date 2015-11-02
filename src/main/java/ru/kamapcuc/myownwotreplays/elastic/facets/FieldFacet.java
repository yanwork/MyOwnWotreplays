package ru.kamapcuc.myownwotreplays.elastic.facets;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import ru.kamapcuc.myownwotreplays.base.Parameters;
import ru.kamapcuc.myownwotreplays.base.Translator;

import java.util.HashMap;
import java.util.Map;

public abstract class FieldFacet extends Facet {

    private final String field;

    private final String code;

    public FieldFacet(String field, String code) {
        this.field = field;
        this.code = code;
    }

    @Override
    public String getId() {
        return field;
    }

    @Override
    public String getType() {
        return "FieldFacet";
    }

    @Override
    public String getDisplayName() {
        return Translator.translate(code);
    }

    @Override
    public QueryBuilder getFilter(Parameters params) {
        String selectedValues = params.getParameter(getId());
        if (selectedValues != null)
            return new TermsQueryBuilder(field, selectedValues.split(","));
        else
            return null;
    }

    @Override
    public Object getResult(Aggregations aggregations) {
        Aggregation aggregation = aggregations.get(getId());
        if (aggregation instanceof Terms) {
            Map<String, Object> result = new HashMap<>();
            Terms termsFacet = (Terms) aggregation;
            for (Terms.Bucket bucket : termsFacet.getBuckets())
                result.put(bucket.getKeyAsString(), bucket.getDocCount());
            return result;
        } else {
            Filter filteredFacet = (Filter) aggregation;
            Aggregations inner = filteredFacet.getAggregations();
            return getResult(inner);
        }
    }

    @Override
    protected AggregationBuilder getOwnAggregation() {
        TermsBuilder facet = new TermsBuilder(getId());
        facet.field(field);
        facet.size(1000);
        return facet;
    }

}
