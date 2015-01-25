package ru.kamapcuc.myownwotreplays;


import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public class Indexer implements Runnable {

    private volatile long completed;
    private final long total;

    public final Parser parser = new Parser();
    private final Client client;
    private final Stream<File> filesToIndex;

    public final static String REPLAYS_INDEX_NAME = "replays";
    public final static String BATTLE_TYPE_NAME = "replays";

    private static String getPath() {
        return System.getProperty("replaysPath");
    }

    public Indexer(Client client) {
        this.client = client;
        File[] allReplays = new File(getPath()).listFiles();
        if (allReplays == null) {
            filesToIndex = Stream.empty();
            total = 0;
        } else {
            filesToIndex = Arrays.stream(allReplays);
            total = allReplays.length;
        }
        completed = 0;
        init();
    }

    private void init() {
        ClusterHealthRequest health = new ClusterHealthRequest();
        health.waitForYellowStatus();
        client.admin().cluster().health(health).actionGet();
    }

    @Override
    public void run() {
        filesToIndex.forEach((file) -> {
            if (!file.isDirectory() && file.getName().endsWith(".wotreplay")) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String data = reader.readLine();
                    if (data.length() == 8) {
                        data = reader.readLine().substring(3);
                    } else
                        data = data.substring(12);
                    Map<String, Object> doc = parser.parse(data);
                    if (doc != null) {
                        IndexRequestBuilder indexRequest = client.prepareIndex(REPLAYS_INDEX_NAME, BATTLE_TYPE_NAME);
                        indexRequest.setId(file.getName());
                        indexRequest.setSource(doc);
                        indexRequest.execute();
                    } else
                        System.out.println(String.format("Failed to parse %s", file.getName()));
                } catch (IOException e) {
                    System.exit(0);
                }
            }
            completed++;
        });
    }

    @SuppressWarnings("unused")
    public long getCompleted() {
        return completed;
    }

    @SuppressWarnings("unused")
    public long getTotal() {
        return total;
    }

}
