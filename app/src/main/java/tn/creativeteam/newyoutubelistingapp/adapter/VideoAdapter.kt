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

package tn.creativeteam.newyoutubelistingapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tn.creativeteam.newyoutubelistingapp.R
import tn.creativeteam.newyoutubelistingapp.model.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

class VideoAdapter(var list:ArrayList<Video>, val clickListener: (Video)->Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
       var my_view=LayoutInflater.from(p0.context).inflate(R.layout.video_row,p0,false)
        return VideoViewHolder(my_view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        (p0 as VideoViewHolder).bind(list[position],clickListener)
    }

    class VideoViewHolder(view: View):RecyclerView.ViewHolder(view){
        var video_image=view.video_act_image
        var video_title=view.video_act_tv_video_title
        fun bind(video:Video, clickListener: (Video) -> Unit){
            video_title.text=video.title
            if(video.youtubeId!=null){
                var videoPictureURL="https://img.youtube.com/vi/"+video.youtubeId+"/0.jpg"
                Picasso.get().load(videoPictureURL).into(video_image)
            }
            itemView.setOnClickListener { clickListener(video) }
        }

    }

}