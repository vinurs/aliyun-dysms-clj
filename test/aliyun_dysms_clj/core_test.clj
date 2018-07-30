(ns aliyun-dysms-clj.core-test
  (:require [clojure.test :refer :all]
            [aliyun-dysms-clj.core :refer :all]))

(deftest setconfig-test
  (testing "测试设置阿里云key是否成功"
    (let [config (aliyun-dysms-setconfig!
                  {:access-key-id "123456"
                   :access-key-secret "012345"})]
      (is (and (= (:access-key-id config) "123456")
               (= (:access-key-secret config) "012345"))))))


(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
