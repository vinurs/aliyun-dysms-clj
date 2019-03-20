(ns aliyun-dysms-clj.core
  (:require [cheshire.core :refer :all])
  (:import (com.aliyuncs CommonRequest)
           (com.aliyuncs CommonResponse)
           (com.aliyuncs DefaultAcsClient)
           (com.aliyuncs IAcsClient)
           (com.aliyuncs.exceptions ClientException)
           (com.aliyuncs.exceptions ServerException)
           (com.aliyuncs.http MethodType)
           (com.aliyuncs.profile DefaultProfile)))

(def ^{:private true} sms-cfg
  "阿里云短信配置"
  {;; "短信API产品名称（短信产品名固定，无需修改）"
   :product  "Dysmsapi"
   ;; "短信API产品域名（接口地址固定，无需修改）"
   :domain "dysmsapi.aliyuncs.com"
   ;; 区域，目前支持这这个
   :region "default"
   :access-key-id "xxxxxxxx"
   :access-key-secret "xxxxxxxx"})

(defn aliyun-dysms-setconfig!
  "设置阿里云访问密钥"
  [{:keys [access-key-id access-key-secret]}]
  (System/setProperty "sun.net.client.defaultConnectTimeout" "10000")
  (System/setProperty "sun.net.client.defaultReadTimeout" "10000")
  (alter-var-root #'sms-cfg (fn [var id secret]
                              (assoc sms-cfg :access-key-id id
                                     :access-key-secret secret))
                  access-key-id access-key-secret))


(defn send-sms
  [{:keys [TemplateCode TemplateParam SmsUpExtendCode
           PhoneNumbers SignName OutId]}]
  ;; 设置超时时间-可自行调整
  (let [profile (DefaultProfile/getProfile (:region sms-cfg)
                                           (:access-key-id sms-cfg)
                                           (:access-key-secret sms-cfg))
        client (DefaultAcsClient. profile)
        ;; 组装请求
        request (doto (CommonRequest.)
                  (.setMethod MethodType/POST)
                  (.setDomain (:domain sms-cfg))
                  (.setVersion "2017-05-25")
                  (.setAction "SendSms")
                  (.putQueryParameter "PhoneNumbers" PhoneNumbers)
                  (.putQueryParameter "SignName" SignName)
                  (.putQueryParameter "TemplateCode" TemplateCode)
                  (.putQueryParameter "TemplateParam" TemplateParam))]
    (try
      (let [response (.getCommonResponse client request)
            data (parse-string (.getData response) true)
            code (:Code data)]
        (println response data code)
        (if (= "OK" code)
          {:ok true :errmsg code}
          {:ok false :errmsg code}))
      (catch ServerException e {:ok false
                                :errmsg (.getMessage e)}))))
