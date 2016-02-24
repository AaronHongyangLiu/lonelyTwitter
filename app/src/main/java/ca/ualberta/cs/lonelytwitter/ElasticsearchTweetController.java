package ca.ualberta.cs.lonelytwitter;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by esports on 2/17/16.
 */
public class ElasticsearchTweetController {
    private static JestDroidClient client;

    //TODO: A function that gets tweets
    public static class getTweetsTask extends AsyncTask<String,Void,ArrayList<Tweet>>{

        @Override
        protected ArrayList<Tweet> doInBackground(String... searchStrings) {
            verifyClient();

            ArrayList<Tweet> tweets = new ArrayList<Tweet>();

            //String query = "{\"query\":{\"term\":{\"message\":\""+searchStrings[0]+"\"}}}";
            // only the first search team will be used
            Search search = new Search.Builder(searchStrings[0]).addIndex("testing").addType("tweet").build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()){
                    //return list of tweets
                    List<NormalTweet> returned_tweets = execute.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(returned_tweets);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tweets;
        }
    }

    public static class SearchTweetsTask extends AsyncTask<String,Void,ArrayList<Tweet>>{

        @Override
        protected ArrayList<Tweet> doInBackground(String... searchStrings) {
            verifyClient();

            ArrayList<Tweet> tweets = new ArrayList<Tweet>();

            String query = "{\"query\":{\"term\":{\"message\":\""+searchStrings[0]+"\"}}}";
            // only the first search team will be used
            Search search = new Search.Builder(query).addIndex("testing").addType("tweet").build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()){
                    //return list of tweets
                    List<NormalTweet> returned_tweets = execute.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(returned_tweets);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tweets;
        }
    }
    //TODO: A function that adds a tweet
    /*public static void addTweet(Tweet tweet){
        verifyClient();

        Index index = new Index.Builder(tweet).index("testing").type("tweet").build();
        try {
            DocumentResult result =  client.execute(index);
            if(result.isSucceeded()){
                // set the ID of the tweet that elasticsearch told
                tweet.setId(result.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    //
    public static class AddTweetTask extends AsyncTask<NormalTweet,Void,Void>{

        @Override
        protected Void doInBackground(NormalTweet... tweets) {
            verifyClient();

            for (int i=0; i<tweets.length;i++){
                NormalTweet tweet = tweets[i];

                Index index = new Index.Builder(tweet).index("testing").type("tweet").build();
                try {
                    DocumentResult result =  client.execute(index);
                    if(result.isSucceeded()){
                        // set the ID of the tweet that elasticsearch told
                        tweet.setId(result.getId());
                    }else{
                        // useful for debugging
                        Log.i("TODO","faild to add a tweet");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }



    public static void verifyClient(){
        // verify that the 'client' exits, if not make it
        if(client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
