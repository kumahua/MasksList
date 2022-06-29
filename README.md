# MasksList
使用 Kotlin 程式語言，來串接口罩即時庫存的公開資料，從實作中來學習如何開發一款 Android APP 應用程式。
<p align="center">
	<img src="https://user-images.githubusercontent.com/40682280/176395938-530188b4-6a2f-40a3-852d-7e5297f6a0c6.png" width="350"> <img src="https://user-images.githubusercontent.com/40682280/176396780-a3fe2ecc-46b4-44de-9651-d21167d97027.png" width="350">
<p>
<p align="center">
	<img src="https://user-images.githubusercontent.com/40682280/176396456-67fa42e0-809a-44b1-813a-7b5c9aeac117.png" width="350"> <img src="https://user-images.githubusercontent.com/40682280/176396569-a0c6bd10-b053-4dd9-a729-570b4f769a42.png" width="350">
    
## OkHttp 基本拉資料方式 (GET 請求)
OkHttp 主要可以分成三個部分：

Part 1. OkHttpClient
設定連線基底(SSL、TLS、連線憑證)。

Part 2. Request
設定 URL 連線網址、請求方式(GET、POST、PUT和DELETE方法)、Header 資訊

Part 3. Call
設定 execute 同步(Synchronous)或 enqueue 非同步(Asynchronous)，執行連線後，可獲取到回應的結果資料。

    private fun getPharmacyData() {
    //口罩資料網址
    val pharmaciesDataUrl =
            "https://raw.githubusercontent.com/thishkt/pharmacies/master/data/info.json"

    //Part 1: 宣告 OkHttpClient
    val okHttpClient = OkHttpClient().newBuilder().build()

    //Part 2: 宣告 Request，要求要連到指定網址
    val request: Request = Request.Builder().url(pharmaciesDataUrl).get().build()

    //Part 3: 宣告 Call
    val call = okHttpClient.newCall(request)

    //執行 Call 連線後，採用 enqueue 非同步方式，獲取到回應的結果資料
    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: java.io.IOException) {
            Log.d("HKT", "onFailure: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            Log.d("HKT", "onResponse: ${response.body?.string()}")
        }
    })
    }

## 設定 RecyclerView
MainActivity.kt，設定 RecyclerView 的 manager 和 adapter

    class MainActivity : AppCompatActivity() {

    //定義全域變數
    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding
    ...
    ...
    ...
     private fun initView() {
            // 定義 LayoutManager 為 LinearLayoutManager
            viewManager = LinearLayoutManager(this)

            // 自定義 Adapte 為 MainAdapter，稍後再定義 MainAdapter 這個類別
            viewAdapter = MainAdapter()

            // 定義從佈局當中，拿到 recycler_view 元件，
            binding.recyclerView.apply {
                // 透過 kotlin 的 apply 語法糖，設定 LayoutManager 和 Adapter
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    ...
    ...
    ...

    private fun getPharmacyData() {
    ...
            runOnUiThread {
                //將下載的口罩資料，指定給 MainAdapter
                viewAdapter.pharmacyList = pharmacyInfo.features

                //關閉忙碌圈圈
                binding.progressBar.visibility = View.GONE
            }
    }

