package ru.kamapcuc.myownwotreplays;

import ru.kamapcuc.myownwotreplays.search.facets.FacetBuilder;
import ru.kamapcuc.myownwotreplays.search.facets.RepositoryFacetBuilder;
import ru.kamapcuc.myownwotreplays.search.facets.TankLevelFacetBuilder;

import java.util.Locale;

public class Config {

    public final static Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public final static int PAGINATION_SIZE = 9;

    public final static String REPLAYS_INDEX_NAME = "replays";

    public final static String BATTLE_TYPE_NAME = "battle";
    public final static String TANK_TYPE_NAME = "tank";
    public final static String MAP_TYPE_NAME = "map";
    public final static String CLASS_TYPE_NAME = "class";
    public final static String NATION_TYPE_NAME = "nation";
    public final static String LANGUAGE_TYPE_NAME = "language";
    public final static String MEDAL_TYPE_NAME = "medal";

    public static String getReplaysPath() {
        return System.getProperty("wotPath") + "replays/";
    }

    public static String getElasticDataPath() {
        return System.getProperty("wotPath") + "MyOwnWotreplays/data/";
    }

    public final static FacetBuilder[] FACET_BUILDERS = new FacetBuilder[]{
            new RepositoryFacetBuilder("tankNation", "facet_tank_nation", NATION_TYPE_NAME),
            new RepositoryFacetBuilder("tankClass", "facet_tank_class", CLASS_TYPE_NAME),
            new RepositoryFacetBuilder("map", "facet_map", MAP_TYPE_NAME),
            new TankLevelFacetBuilder("tankLevel", "facet_tank_level")
    };

}