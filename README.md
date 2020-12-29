# Pagination Recyclerview Android Kotlin
Pagination Recyclerview Android Java We use the Pagination Library of Android Jetpack in this app to fetch data from the database to recyclerView by retrofit API, This code is following the principles of MVVM design pattern and LiveData.

<p align="left">
  <a href="https://github.com/sweetalert2/sweetalert2/actions"><img alt="Build Status" src="https://github.com/sweetalert2/sweetalert2/workflows/build/badge.svg"></a>
</p>

---

Screenshot Picture
-----
<p align="center">
  <img src="https://github.com/alfayedoficial/Pagination_Recyclerview_Android_Kotlin/blob/master/demo/sc1.png" width="350" title="Screen1">
  <img src="https://github.com/alfayedoficial/Pagination_Recyclerview_Android_Kotlin/blob/master/demo/sc2.png" width="350" title="Screen2">
  <img src="https://github.com/alfayedoficial/Pagination_Recyclerview_Android_Kotlin/blob/master/demo/sc3.png" width="350" title="Screen2">
  <img src="https://github.com/alfayedoficial/Pagination_Recyclerview_Android_Kotlin/blob/master/demo/sc4.png" width="350" title="Screen2">
/>

[Watch the Demo App](https://github.com/alfayedoficial/Pagination_Recyclerview_Android_Kotlin/blob/master/demo/Kotlin.webm)

Installation
------------

```kotlin

 // add dependence of pagination to gradel script 
 def paging_version = "2.1.2"
 implementation "androidx.paging:paging-runtime:$paging_version" // pagination
 
 // add another dependencies  [Optional]
 implementation 'com.github.bumptech.glide:glide:4.11.0' // For Image
 annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
 
 def retrofit2_version = "2.8.1" //Retrofit2
 implementation "com.squareup.retrofit2:retrofit:$retrofit2_version"
 implementation "com.squareup.retrofit2:converter-gson:$retrofit2_version"
  
 def lifecycle_version = "2.2.0"  //lifecycle
 implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
 implementation "android.arch.lifecycle:extensions:$lifecycle_version"
 annotationProcessor "android.arch.lifecycle:compiler:$lifecycle_version"
 
 ```
 
Create Abstract Class PaginationScrollListener
--------

```kotlin
abstract class PaginationScrollListener (layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    val layoutManager: LinearLayoutManager = layoutManager

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun getTotalPageCount(): Int
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}
 
 ```
 
Usage
-----

```kotlin
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 1
    private var currentPage: Int = pageStart
    binding.recyclerMyOrders.addOnScrollListener(object : PaginationScrollListener(binding.recyclerMyOrders.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                Handler(Looper.myLooper()!!).postDelayed({
                    loadNextPage()
                }, 1000)
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
 
 ```
 
 Create Adapter Class AdapterTopMoviesPagination
--------

```kotlin
class AdapterTopMoviesPagination(private var mActivity: HomeActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() ,
    PaginationAdapterCallback{

    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false

    private var errorMsg: String? = ""

    private var moviesModels: MutableList<ResultsItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  if(viewType == item){
            val binding: ItemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
            TopMoviesVH(binding)
        }else{
            val binding: ItemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_loading, parent, false)
            LoadingVH(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = moviesModels[position]
        if(getItemViewType(position) == item){
            val myOrderVH: TopMoviesVH = holder as TopMoviesVH
            myOrderVH.itemRowBinding.movieProgress.visibility = View.VISIBLE
            myOrderVH.bind(model)
        }else{
            val loadingVH: LoadingVH = holder as LoadingVH
            if (retryPageLoad) {
                loadingVH.itemRowBinding.loadmoreErrorlayout.visibility = View.VISIBLE
                loadingVH.itemRowBinding.loadmoreProgress.visibility = View.GONE

                if(errorMsg != null) loadingVH.itemRowBinding.loadmoreErrortxt.text = errorMsg
                else loadingVH.itemRowBinding.loadmoreErrortxt.text = mActivity.getString(R.string.error_msg_unknown)

            } else {
                loadingVH.itemRowBinding.loadmoreErrorlayout.visibility = View.GONE
                loadingVH.itemRowBinding.loadmoreProgress.visibility = View.VISIBLE
            }

            loadingVH.itemRowBinding.loadmoreRetry.setOnClickListener{
                showRetry(false, "")
                retryPageLoad()
            }
            loadingVH.itemRowBinding.loadmoreErrorlayout.setOnClickListener{
                showRetry(false, "")
                retryPageLoad()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (moviesModels.size > 0) moviesModels.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            item
        }else {
            if (position == moviesModels.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

    override fun retryPageLoad() {
        mActivity.loadNextPage()
    }


    class TopMoviesVH(binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemMovieBinding = binding
        fun bind(obj: Any?) {
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
        }
    }

    class LoadingVH(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemLoadingBinding = binding
    }

    fun showRetry(show: Boolean, errorMsg: String) {
        retryPageLoad = show
        notifyItemChanged(moviesModels.size - 1)
        this.errorMsg = errorMsg
    }

    fun addAll(movies: MutableList<ResultsItem>) {
        for(movie in movies){
            add(movie)
        }
    }

    fun add(moive: ResultsItem) {
        moviesModels.add(moive)
        notifyItemInserted(moviesModels.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ResultsItem())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position: Int =moviesModels.size -1
        val movie: ResultsItem = moviesModels[position]

        if(movie != null){
            moviesModels.removeAt(position)
            notifyItemRemoved(position)
        }
    }

 
 ```

Please note that [Pagination Library is well-supported and Free License](https://github.com/alfayedoficial/Pagination_Recyclerview_Android_Kotlin), so you can use app and edit.


