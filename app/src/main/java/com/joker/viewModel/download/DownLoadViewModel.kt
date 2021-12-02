//package com.task.viewModel.download
//
//import android.util.Log
//import androidx.annotation.VisibleForTesting
//import androidx.lifecycle.*
//import com.joker.data.repository.JokeGetRepositorySource
//import com.task.data.dto.ImageItem
//import com.joker.data.error.NETWORK_ERROR
//import com.task.ui.base.BaseViewModel
//import com.task.utils.RegexUtils
//import com.task.utils.SingleEvent
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.withContext
//import org.jsoup.Jsoup
//import javax.inject.Inject
//
///**
// * Created by Yan
// */
//@HiltViewModel
//class DownLoadViewModel @Inject
//constructor(private val jokeGetRepositoryRepository: JokeGetRepositorySource) : BaseViewModel(){
//
//    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
//    val imageItems: MutableLiveData<ArrayList<ImageItem?>> by lazy { MutableLiveData<ArrayList<ImageItem?>>().also { it.value = arrayListOf() } }
//
//    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
//    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
//
//    fun parseHtml(url: String) : MutableLiveData<ArrayList<ImageItem?>>{
//        viewModelScope.launch {
//            val items = arrayListOf<ImageItem?>()
//            withContext(Dispatchers.IO) {
//                try {
//                    val document = Jsoup.connect(url).get()
//                    val images = document.select("img")
//                    images.forEach {
//                        Log.e("img is ", it.absUrl("src"))
//                        val legalUrl = RegexUtils.urlRex(it.absUrl("src"))
//                        legalUrl?.let {url->
//                            val imageItem = ImageItem(MutableLiveData(0L),
//                                0,url)
//                            items.add(imageItem)
//                        }
//
//                    }
//                }catch (e:Exception){
//                    showToastMessage(NETWORK_ERROR)
//                }finally {
//
//                }
//            }
//            imageItems.value = items
//        }
//        return imageItems
//    }
//
//
//
//    suspend fun download(item: ImageItem, filePath: String):Flow<ImageItem> {
//        return jokeGetRepositoryRepository.requestImageDownload(item,filePath)
//    }
//
//
//    fun showToastMessage(errorCode: Int) {
//        val error = errorManager.getError(errorCode)
//        showToastPrivate.value = SingleEvent(error.description)
//    }
//
//
//
//}
