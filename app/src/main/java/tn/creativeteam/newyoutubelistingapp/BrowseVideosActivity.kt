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

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import tn.creativeteam.newyoutubelistingapp.adapter.VideoAdapter
import tn.creativeteam.newyoutubelistingapp.model.Video
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_browse_videos.*
import java.util.*

class BrowseVideosActivity : AppCompatActivity() {

    var selectedCategoryObjectId=""
    val TAG="BrowseVideos:"
    var videoList= ArrayList<Video>()
    var adp= VideoAdapter(videoList, {videoItem:Video->videoClickListener(videoItem)})


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_videos)
        setTitle("Category: "+intent.getStringExtra("selectedCategoryName"))
        selectedCategoryObjectId=intent.getStringExtra("selectedCategoryObjectId")
        //Log.d(TAG,"$TAG: before loading videos")
        rv_videos.layoutManager= LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rv_videos.adapter=adp
        loadVideos()
    }

    fun loadVideos(){
        video_act_ll_progressbar.visibility= View.VISIBLE
        val query = ParseQuery<ParseObject>("Video")
        query.orderByAscending("title")
        if(selectedCategoryObjectId!=null){
            val categoryPointer= ParseObject.createWithoutData("Category",selectedCategoryObjectId)
            query.whereEqualTo("category",categoryPointer)
        }
        query.findInBackground{list,e ->
            video_act_ll_progressbar.visibility= View.GONE
            if(e==null){
                //no error occured
                //Log.d(TAG,"$TAG: No error occured when running the query")
                if(list.size>0){
                    //there is categories retrieved
                    //Log.d(TAG,"$TAG: There is "+list.size+" videos retrieved")
                    for (video in list){
                        videoList.add(
                            Video(video.objectId,
                                video.get("youtubeId").toString(),
                                video.get("title").toString()
                            )
                        )
                    }
                    adp.notifyDataSetChanged()

                    //Log.d(TAG,"$TAG: videoList content: "+videoList.toString())
                    var namesOfLadedVideos=""
                    for(v in videoList){
                        namesOfLadedVideos+=v.title+"\n"
                    }
                    //Log.d(TAG,"$TAG: titles of retrieved videos: "+namesOfLadedVideos)

                }else{
                    //there is no categories in the app backend
                    //Log.d(TAG,"$TAG: there is no videos in the app backend")
                    video_act_ll_error.visibility= View.VISIBLE
                    video_error_message.text=getString(R.string.video_act_error_message)

                }
            }else{
                //there is error occured
                //Log.d(TAG,"$TAG: there is an error occured "+e.message)
                video_act_ll_error.visibility= View.VISIBLE
                video_act_btn_retry.visibility= View.VISIBLE
                video_error_message.text=getString(R.string.network_problem_message)

            }

        }
    }
    private fun videoClickListener(video: Video){
        val intent = Intent(this, PlayVideoActivity::class.java)
        intent.putExtra("youtubeId",video.youtubeId)
        startActivity(intent)
    }



    fun video_act_btn_retry_clicked(v:View){
        video_act_ll_error.visibility=View.GONE
        video_act_btn_retry.visibility=View.GONE
        video_error_message.text=""
        loadVideos()
    }
}
