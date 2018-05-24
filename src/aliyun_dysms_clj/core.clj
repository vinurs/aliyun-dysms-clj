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


(def product
  "短信API产品名称（短信产品名固定，无需修改）"
  "Dysmsapi")

(def domain
  "短信API产品域名（接口地址固定，无需修改）"
  "dysmsapi.aliyuncs.com")

(defn send-sms
  [{:keys [accessKeyId accessKeySecret
           TemplateCode TemplateParam
           PhoneNumber SignName OutId]}]
  (let [profile (DefaultProfile/getProfile
                   "cn-hangzhou"
                   accessKeyId
                   accessKeySecret)
        acsClient (do
                    (DefaultProfile/addEndpoint
                       "cn-hangzhou" "cn-hangzhou"
                       product domain)
                    (DefaultAcsClient. profile))
        request (doto (SendSmsRequest.)
                  (.setPhoneNumbers PhoneNumber)
                  (.setSignName SignName)
                  (.setTemplateCode TemplateCode)
                  (.setTemplateParam TemplateParam)
                  (.setOutId OutId))
        sendSmsResponse (.getAcsResponse acsClient request)]
      (.getCode sendSmsResponse)))
