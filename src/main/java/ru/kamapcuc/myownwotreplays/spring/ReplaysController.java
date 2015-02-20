package ru.kamapcuc.myownwotreplays.spring;

import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kamapcuc.myownwotreplays.elastic.Doc;
import ru.kamapcuc.myownwotreplays.elastic.ElasticClient;
import ru.kamapcuc.myownwotreplays.elastic.SearchResult;
import ru.kamapcuc.myownwotreplays.Config;
import ru.kamapcuc.myownwotreplays.search.ReplaysRequest;
import ru.kamapcuc.myownwotreplays.search.SortType;
import ru.kamapcuc.myownwotreplays.search.facets.FacetBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReplaysController {

    private final static ElasticClient client = ElasticClient.getInstance();

    @RequestMapping({"/", "**/search.do"})
    public String search(HttpServletRequest httpRequest, ModelMap model) {
        model.put("battlesData", searchInternal(httpRequest));
        model.put("sortTypes", SortType.values());
        model.put("defaultSort", SortType.DEFAULT_SORT);
        model.put("defaultOrder", SortType.DEFAULT_ORDER);
        model.put("facetsData", getFacetsData());
        model.put("paginationSize", Config.PAGINATION_SIZE);
        return "search";
    }

    @RequestMapping("**/view.do")
    public String view(HttpServletRequest httpRequest, ModelMap model) {
        String id = httpRequest.getParameter("id");
        if (id != null) {
            Doc battle = client.get(Config.REPLAYS_INDEX_NAME, Config.BATTLE_TYPE_NAME, id);
            model.put("battle", battle);
        }
        return "view";
    }

    @ResponseBody
    @RequestMapping("**/search_ajax.do")
    public String searchAjax(HttpServletRequest httpRequest) {
        return searchInternal(httpRequest);
    }

    @ResponseBody
    @RequestMapping("**/paginate.do")
    public String paginationAjax(HttpServletRequest httpRequest) {
        ReplaysRequest requestBuilder = new ReplaysRequest(castParams(httpRequest));
        SearchResult searchResult = requestBuilder.paginate();
        return searchResult.stringify();
    }

    private String searchInternal(HttpServletRequest httpRequest) {
        ReplaysRequest requestBuilder = new ReplaysRequest(castParams(httpRequest));
        SearchResult searchResult = requestBuilder.fullSearch();
        return searchResult.stringify();
    }

    private String getFacetsData() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            for (FacetBuilder facet : Config.FACET_BUILDERS)
                facet.toXContent(builder, ToXContent.EMPTY_PARAMS);
            builder.close();
            return builder.string();
        } catch (IOException e) {
            return null;
        }
    }

    public static Map<String, String> castParams(HttpServletRequest httpRequest) {
        Map<String, String> result = new HashMap<>();
        for (Object keyO : Collections.list(httpRequest.getParameterNames())) {
            String key = (String) keyO;
            result.put(key, httpRequest.getParameter(key));
        }
        return result;
    }

}