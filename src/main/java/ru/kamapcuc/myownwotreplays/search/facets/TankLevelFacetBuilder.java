package ru.kamapcuc.myownwotreplays.search.facets;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public class TankLevelFacetBuilder extends FieldFacetBuilder {

    public TankLevelFacetBuilder(String field, String code) {
        super(field, code);
    }

    @Override
    protected void toXContentValues(XContentBuilder builder) throws IOException {
        for (int i = 1; i <= 10; i++)
            builder.field(Integer.toString(i), Integer.toString(i));
    }

}
