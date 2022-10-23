test:
新增:http://localhost:8080/api/addtestCoin
json data
{
    "coinName": "USD",
    "coinChName": "美金",
    "exchangeRate": "30",
}

{
    "coinName": "EUR",
    "coinChName": "歐元",
    "exchangeRate": "31",
}

修改:http://localhost:8080/api/testCoin/1
json data
{
    "id": 1,
    "coinName": "GBP",
    "coinChName": "英鎊",
    "exchangeRate": "36",
}

刪除:http://localhost:8080/api/detestCoin/2
json data
{
    "id": 1,
    "coinName": "GBP",
    "coinChName": "英鎊",
    "exchangeRate": "36",
}

顯示提供api:http://localhost:8080/api/getOldUrlData

轉換api:http://localhost:8080/api/showData
