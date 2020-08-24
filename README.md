# aliyun-dysms-clj: 阿里云sms的Clojure封装
* 发送短信

## Usage
* project.clj & youns.clj
```clojure
;; project.clj
[vinurs/aliyun-dysms-clj "0.1.9"]
;; your ns
(ns test (:require [aliyun-dysms-clj.core :refer [send-sms]]))
```

* 发送短信
```clojure
(aliyun-dysms-setconfig! {:access-key-id "xxxxxxx"
                          :access-key-secret "xxxxxxxxxxx"})

(send-sms {:TemplateCode "SMS_xxxxxxxx"
           :TemplateParam "your template param"
           :PhoneNumbers "your phoneNumbers"
           :SignName "your signame"
           :OutId "your outid”})
```

* 返回错误码
参见[短信接口调用错误码](https://help.aliyun.com/knowledge_detail/57717.html?spm=a2c4g.11186623.2.9.VmRF7a)

## License

Copyright © 2020 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
