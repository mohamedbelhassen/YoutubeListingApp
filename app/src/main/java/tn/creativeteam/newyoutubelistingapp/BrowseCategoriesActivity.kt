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
import android.util.Log
import android.view.View
import tn.creativeteam.newyoutubelistingapp.adapter.CategoryAdapter
import tn.creativeteam.newyoutubelistingapp.model.Category
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_browse_categories.*


class BrowseCategoriesActivity : AppCompatActivity() {

    val TAG="BrowseCategories:"
    var categoryList=ArrayList<Category>()
    var adp= CategoryAdapter(categoryList, {categoryItem:Category->categoryClickListener(categoryItem)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_categories)

        rv_categories.layoutManager=LinearLayoutManager(this)
        rv_categories.adapter=adp
        loadCategories()

    }

    fun loadCategories(){
        category_act_ll_progressbar.visibility=View.VISIBLE
        val query = ParseQuery<ParseObject>("Category")
        query.orderByAscending("name")
        query.findInBackground{list,e ->
            category_act_ll_progressbar.visibility=View.GONE
            if(e==null){
                //no error occured
                //Log.d(TAG,"$TAG: No error occured when running the query")
                if(list.size>0){
                    //there is categories retrieved
                    //Log.d(TAG,"$TAG: There is "+list.size+" categories retrieved")
                    for (category in list){
                        categoryList.add(
                            Category(category.objectId,
                                category.get("name").toString(),
                                category.getParseFile("picture")
                            )
                        )
                    }
                    adp.notifyDataSetChanged()

                    //Log.d(TAG,"$TAG: videoList content: "+categoryList.toString())
                    var namesOfLadedCategories=""
                    for(c in categoryList){
                        namesOfLadedCategories+=c.name+"\n"
                    }

                }else{
                    //there is no categories in the app backend
                    //Log.d(TAG,"$TAG: there is no categories in the app backend")
                    category_act_ll_error.visibility= View.VISIBLE
                    category_error_message.text=getString(R.string.category_act_error_message)

                }
            }else{
                //there is error occured
                //Log.d(TAG,"$TAG: there is an error occured "+e.message)
                category_act_ll_error.visibility= View.VISIBLE
                category_act_btn_retry.visibility=View.VISIBLE
                category_error_message.text=getString(R.string.network_problem_message)

            }

        }
    }

    fun category_act_btn_retry_clicked(v:View){
        category_act_ll_error.visibility=View.GONE
        category_act_btn_retry.visibility=View.GONE
        category_error_message.text=""
        loadCategories()

    }

    private fun categoryClickListener(category: Category){
        val intent = Intent(this, BrowseVideosActivity::class.java)
        intent.putExtra("selectedCategoryName",category.name)
        intent.putExtra("selectedCategoryObjectId",category.objectId)
        startActivity(intent)
    }

}
