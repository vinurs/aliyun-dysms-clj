(defproject vinurs/aliyun-dysms-clj "0.1.7"
  :description "阿里云发送短信接口clojure封装"
  :url "https://github.com/vinurs/aliyun-dysms-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 ;; 阿里云短信服务
                 [com.aliyun/aliyun-java-sdk-core "4.4.6"]
                 [cheshire "5.8.1"]])
