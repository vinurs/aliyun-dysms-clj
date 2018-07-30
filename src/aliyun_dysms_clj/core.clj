(ns aliyun-dysms-clj.core
  (:import (com.aliyuncs DefaultAcsClient IAcsClient)
           (com.aliyuncs.dysmsapi.model.v20170525 QuerySendDetailsRequest
                                                  QuerySendDetailsResponse
                                                  SendSmsRequest
                                                  SendSmsResponse)
           (com.aliyuncs.dysmsapi.transform.v20170525.SendSmsResponseUnmarshaller)
           (com.aliyuncs.exceptions ClientException)
           (com.aliyuncs.http MethodType FormatType HttpResponse)
           (com.aliyuncs.profile DefaultProfile IClientProfile)))


(def ^{:private true} sms-cfg
  "阿里云短信配置"
  {;; "短信API产品名称（短信产品名固定，无需修改）"
   :product  "Dysmsapi"
   ;; "短信API产品域名（接口地址固定，无需修改）"
   :domain "dysmsapi.aliyuncs.com"
   ;; 区域，目前支持这这个
   :region "cn-hangzhou"
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
  [{:keys [TemplateCode TemplateParam
           PhoneNumbers SignName OutId]}]
  ;; 设置超时时间-可自行调整
  (let [profile (DefaultProfile/getProfile (:region sms-cfg)
                                           (:access-key-id sms-cfg)
                                           (:access-key-secret sms-cfg))
        _ (DefaultProfile/addEndpoint (:region sms-cfg) (:region sms-cfg)
                                      (:product sms-cfg) (:domain sms-cfg))
        acsClient (DefaultAcsClient. profile)
        ;; 组装请求
        request (doto (SendSmsRequest.)
                  (.setPhoneNumbers PhoneNumbers)
                  (.setSignName SignName)
                  (.setTemplateCode TemplateCode)
                  (.setTemplateParam TemplateParam)
                  (.setOutId OutId))]
    (try
      (let [sendSmsResponse (.getAcsResponse acsClient request)
            code (.getCode sendSmsResponse)]
        (if (= "OK" code)
          {:ok true :errmsg code}
          {:ok false :errmsg code}))
      (catch Exception e {:ok false
                          :errmsg (.getMessage e)}))))
