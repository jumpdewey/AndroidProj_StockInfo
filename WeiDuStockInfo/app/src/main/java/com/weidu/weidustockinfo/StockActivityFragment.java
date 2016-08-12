package com.weidu.weidustockinfo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class StockActivityFragment extends Fragment {
    private static final String TAG = "StockInfoFragment";
    private GetNetworkInfoTask niTask = null;
    public StockActivityFragment() {
    }
    public void startNetworkInfoTask() {
        if(niTask != null) {
            AsyncTask.Status aiStatus = niTask.getStatus();
            Log.d(TAG, " startHttpURLTask :doClick: aiTask status is " + aiStatus);
            if (aiStatus != AsyncTask.Status.FINISHED) {
                niTask.cancel(true);
            }
        }
        String stockParamString = ((EditText) getActivity().findViewById(R.id.editText)).getText().toString();
        niTask = new GetNetworkInfoTask(getActivity());
        niTask.execute(stockParamString);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button button = (Button)getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNetworkInfoTask();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock, container, false);
    }

    public void handleResults(String results) {
        Log.d(TAG, "handleResults " + results);
        StockInfo stockInfo = null;
        if (results != null) {
            ParseJsonInfo parseJsonInfo = new ParseJsonInfo();
            stockInfo = parseJsonInfo.decodeMessage(results);
        } else {
            stockInfo = new StockInfo();
            stockInfo.setCompany("No such stock symbol");
            stockInfo.setExchange("");
            stockInfo.setChange("");
            stockInfo.setDate("");
            stockInfo.setClosing_price("");
        }
        Log.d(TAG,"StockInfo: " + stockInfo.toString());
        displayResults(stockInfo);
    }
    private void displayResults(StockInfo stockInfo) {
        TextView companyTV = (TextView)getActivity().findViewById(R.id.companyInfo);
        companyTV.setText(stockInfo.getCompany());
        TextView exchangeTV = (TextView)getActivity().findViewById(R.id.exchangeInfo);
        exchangeTV.setText(stockInfo.getExchange());
        TextView closingPriceTV = (TextView)getActivity().findViewById(R.id.closingPriceInfo);
        closingPriceTV.setText(stockInfo.getClosing_price());
        TextView changeTV = (TextView)getActivity().findViewById(R.id.changeInfo);
        changeTV.setText(stockInfo.getChange());
        TextView dateTV = (TextView)getActivity().findViewById(R.id.dateInfo);
        dateTV.setText(stockInfo.getDate());
    }

    public class GetNetworkInfoTask extends AsyncTask<String,Integer,String> {
        private static final String TAG = "GetNetworkInfoTask";
        private Context context = null;
        GetNetworkInfoTask(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String...params){
            String networkInfo = getDataFromWeb(params[0]);
            return networkInfo;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(String result) {
            handleResults(result);
        }

        private String getDataFromWeb(String stockSymbol){
            Log.d(TAG,"getDataFromWeb "+stockSymbol);
            String queryString = "http://finance.google.com/finance/info?client=ig&q="+ stockSymbol;
            String sb = null;
            try{
                URL url = new URL(queryString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int response = urlConnection.getResponseCode();
                if(response!=HttpURLConnection.HTTP_OK){
                    Log.e(TAG, "getDataFromWeb HttpURLConnection not ok: " + response);

                } else {
                    Log.d(TAG, "getDataFromWeb HttpURLConnection is ok: " + response);
                    sb = getInputString(urlConnection);
                    Log.d(TAG,sb);
                    urlConnection.disconnect();
                }
            } catch (Exception e){
                Log.e(TAG, this.getClass().getSimpleName() + e + " for url: " + queryString);
                e.printStackTrace();
            }
            return sb;
        }
        private String getInputString(HttpURLConnection urlConnection){
            StringBuffer sb = null;
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                sb = new StringBuffer("");
                String line;
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
            } catch (IOException e){
                Log.d(TAG, "");
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}
