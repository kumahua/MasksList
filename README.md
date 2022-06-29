# MasksList
Start
## OkHttp 基本拉資料方式 (GET 請求範例)
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
