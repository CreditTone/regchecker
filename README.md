# 服务调用
```kotlin
val execute = Jsoup.connect("http://120.132.22.65:8888/check")
            .requestBody("""
                {
                    "account": "18799990000",
                    "accountType": "PHONE",
                    "exclusions": [
                        "12306"
                    ]
                    "reg007CallBackUrl": "http://127.0.0.1:8888/callback"
                }
            """)
            .header("Content-Type", "application/json;charset=UTF-8")
            .method(Connection.Method.POST)
            .ignoreContentType(true)
            .timeout(1000 * 60 * 60)
            .execute()
    println(execute.body())
```
`reg007CallBackUrl`字段设置reg007回调接口地址，会以POST方式提交，Content-Type=application/json;
# 开发进度
| platform | 名称 | 状态 | 备注 |
| ------ | ------ | ------ | ------ |
| RenRenDai | 人人贷 | 完成 |  |
|  | 小白来花 | 未开发 | 官网接口奔溃 |
|  | 原子贷 | 未开发 | 官网无注册入口，登录直接为短信验证码 |
| ZhongAnBaoXian | 众安保险 | 完成 |  |
| QuGuanZhang | 趣管账 | 完成 |  |
| HengYiDai | 恒易贷 | 完成 |  |
|  | 爱钱进 | 未开发 | 请求响应都加密，有图片验证码 |
| RongYiJie | 容易借 | 完成 |  |
| XinYongGuanJia | 信用管家 | 完成 |  |
| NanXingSiRenYiSheng | 男性私人医生 | 完成 |  |
|  | 分期贷 | 未开发 | 一直提示：频繁登录操作 |
| XiaoYingKaDai | 小赢卡贷 | 完成 |  |
|  | 读秒钱包 | 未开发 | 有图片验证码 |
|  | 点点 | 未开发 | 已改版，返回信息不包含注册未注册 |
| WeiDaiQianBao | 微贷钱包 | 完成 |  |
|  | 51公积金管家 | 未开发 | 没抓到包 |
| WaCaiJiZhang | 挖财记账 | 完成 | 容易触发访问频繁 |
| DaZhiHui | 大智慧 | 完成 |  |
|  | 同花顺 | 未完成 | 提示信息无法区分 |
|  | 借贷宝 | 未完成 | 无提示语，改密码直接注册 |
|  | 京东股票 | 未完成 | 流程复杂，有加密，登录有点选验证，改密码有圆圈验证 |
|  | 京东股票 | 未完成 | 流程复杂，有加密，登录有点选验证，改密码有圆圈验证 |
|  | 人人贷 | 未完成 | 请求返回乱码，待解决 |
|  | 携程 | 未完成 | 滑块跟点选验证 |
|  | 美团WEB | 未完成 | 滑块验证 |
|  | 西瓜视频 | 未完成 | 直接访问频繁，找回密码点选滑块验证 |
|  | 唱吧 | 未完成 | 验证码 |
|  | 皮皮虾 | 未完成 | 接口加密 |
|  | 启信宝 | 未完成 | 接口加密 |
|  | 豆瓣 | 未完成 | 短信验证体验问题 |
|  | csdn | 完成 |  |
|  | 大街网 | 完成 |  |
|  | 人人网 | 未完成 | 验证码不好识别 |
|  | 爱奇艺 | 未完成 | 滑块 |
|  | 本来生活网 | 完成 |  |
|  | 学信网 | 完成 |  |
|  | 中国国际航空 | 完成 |  |
|  | 空中网 | 完成 |  |
|  | 二手车之家 | 完成 |  |
|  | 网贷之家 | 完成 |  |
|  | 周大福 | 完成 |  |
|  | yy直播 | 完成 |  |
|  | 自如 | 完成 |  |
|  | 拍拍贷 | 完成 |  |
|  | 111医药馆 | 完成 |  |
|  | 盛大游戏 | 完成 |  |
|  | 光宇游戏 | 完成 |  |
|  | 暴雪娛樂 | 完成 |  |
|  | PICC | 完成 |  |
|  | 好大夫在线 | 完成 |  |

# 暗网查询
```kotlin
Jsoup.connect("http://120.132.22.65:8888/tor/search?account=13800138000")
    .ignoreContentType(true)
    .timeout(1000 * 60 * 60)
    .method(Connection.Method.GET)
    .execute()
    
println(execute.body())
```