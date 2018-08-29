package com.example.ishaqfakhrozi.lololonomic

import android.app.Activity

import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class PostmanActivity : AppCompatActivity(), View.OnClickListener {
    var tvIsConnected : TextView? = null
    var etName : EditText? = null
    var etCountry : EditText? = null
    var etTwitter : EditText? = null
    var btnPost : Button?=null
    lateinit var person :Person

    val isConnected:Boolean
        get() {
            val connMgr = getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.getActiveNetworkInfo()
            if (networkInfo != null && networkInfo.isConnected())
                return true
            else
                return false
        }

   override fun onClick(view : View) {
        when(view.getId()){
            R.id.btnPost -> {
                if (!validate())
                    Toast.makeText(getBaseContext(),"Enter Some Data", Toast.LENGTH_SHORT).show()
                HttpAsyncTask().execute("http://hmkcode.appspot.com/jsonservlet")
            }
        }
    }

    private inner class HttpAsyncTask:AsyncTask<String,Void,String>(){
        override fun doInBackground(vararg urls:String):String{
            person = Person()
            person.name = (etName!!.getText().toString())
            person.country=(etCountry!!.getText().toString())
            person.twitter=(etTwitter!!.getText().toString())
            return POST(urls[0], person)
        }
         override fun onPostExecute(result:String) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show()
        }
    }
    private fun validate():Boolean {
        if (etName!!.getText().toString().trim().equals(""))
            return false
        else if (etCountry!!.getText().toString().trim().equals(""))
            return false
        else if (etTwitter!!.getText().toString().trim().equals(""))
            return false
        else
            return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postman)

        tvIsConnected = findViewById(R.id.tvIsConnected) as TextView
        etName = findViewById(R.id.etName) as EditText
        etCountry = findViewById(R.id.etCountry) as EditText
        etTwitter = findViewById(R.id.etTwitter) as EditText
        btnPost = findViewById(R.id.btnPost) as Button
        // check if you are connected or not
        if (isConnected)
        {
            tvIsConnected!!.setBackgroundColor(-0xff3400)
            tvIsConnected!!.setText("You are conncted")
        }
        else
        {
            tvIsConnected!!.setText("You are NOT conncted")
        }
        btnPost!!.setOnClickListener(this)
    }
    companion object {
        fun POST(url:String, person:Person):String {
            val inputStream: InputStream?= null
            var result = ""
            try
            {
                // 1. create HttpClient
                val httpclient = DefaultHttpClient()
                // 2. make POST request to the given URL
                val httpPost = HttpPost(url)
                var json = ""
                // 3. build jsonObject
                val jsonObject = JSONObject()
                jsonObject.accumulate("name", person.name)
                jsonObject.accumulate("country", person.country)
                jsonObject.accumulate("twitter", person.twitter)
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString()
                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);
                // 5. set json to StringEntity
                val se = StringEntity(json)
                // 6. set httpPost Entity
                httpPost.setEntity(se)
                // 7. Set some headers to inform server about the type of the content
                httpPost.setHeader("Accept", "application/json")
                httpPost.setHeader("Content-type", "application/json")
                // 8. Execute POST request to the given URL
                val httpResponse = httpclient.execute(httpPost)
                // 9. receive response as inputStream
                val inputStream = httpResponse.getEntity().getContent()
                // 10. convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream)
                else
                    result = "Did not work!"
            }
            catch (e:Exception) {
                Log.d("InputStream", e.getLocalizedMessage())
            }
            // 11. return result
            return result
        }
        @Throws(IOException::class)
        private fun convertInputStreamToString(inputStream:InputStream):String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val line = ""
            var result = ""
            while (true){
                val line = bufferedReader.readLine()?:break
                result += line
            }

            inputStream.close()
            return result
        }
    }

}
