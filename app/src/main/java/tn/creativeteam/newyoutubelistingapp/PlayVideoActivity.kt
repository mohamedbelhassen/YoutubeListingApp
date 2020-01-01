/*
Copyright 2019 Mohamed Belhassen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

/*
* This App is Created by Mohamed Belhassen a co-founder of the Creative Team Club: http://creativeteam.tn/
*
* To Learn How to create your own Youtube Video Listing App From Scratch and Step by Step, you can access our online Free course using the following URL:
*
*       http://bit.ly/BuildBasicYoutubeApp
*
* Follow us on the following social Media to get many other free Trainings and Tips
*
*       Facebook Page: https://www.facebook.com/creative.team.tunisia/
*       Youtube: http://bit.ly/CREATIVE_TEAM_YOUTUBE_CHANNEL
*       Website: http://www.creativeteam.tn
* */

package tn.creativeteam.newyoutubelistingapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_play_video.*

class PlayVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set screen mode to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //hide the action bar
        supportActionBar?.hide()

        setContentView(R.layout.activity_play_video)

        val videoYoutubeId=intent.getStringExtra("youtubeId")
        var videoURL="https://www.youtube.com/embed/"+videoYoutubeId
        my_web_view.settings.javaScriptEnabled=true
        my_web_view.webChromeClient=object:WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                play_video_act_progressbar_text.text=getString(R.string.loading_youtube_video_text)+ " "+newProgress+ " %"
                if(newProgress==100)
                    play_video_act_ll_progressbar.visibility= View.GONE
            }
        }
        my_web_view.loadUrl(videoURL)
    }
}
