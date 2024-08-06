(ns clojure-general-ms.configs.kafka.listeners-configuration
  (:require [clojure.tools.logging :as log])
  (:import (java.time Duration)
           (java.util Collections Properties)
           [org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer]
           (org.apache.kafka.common.serialization StringDeserializer)))



(defn create-consumer []
  (let [props (doto (Properties.)
                (.put ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG "localhost:9092")
                (.put ConsumerConfig/GROUP_ID_CONFIG "transaction-consumer-group")
                ;(.put ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG "org.apache.kafka.common.serialization.StringDeserializer")
                ;(.put ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG "org.apache.kafka.common.serialization.StringDeserializer"))
                ;(.put ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG (.getBootstrapServers consumer))
                ;(.put ConsumerConfig/GROUP_ID_CONFIG (.getGroupId consumer))
                (.put ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG StringDeserializer)
                (.put ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG StringDeserializer)
                (.put ConsumerConfig/AUTO_OFFSET_RESET_CONFIG "earliest")
                (.put ConsumerConfig/MAX_POLL_RECORDS_CONFIG (int 5))
                (.put ConsumerConfig/SESSION_TIMEOUT_MS_CONFIG (int 60000))
                (.put ConsumerConfig/MAX_POLL_INTERVAL_MS_CONFIG (int 250000)))
                consumer (KafkaConsumer. props)]
    (.subscribe consumer (Collections/singletonList "transactions"))
    consumer))

(defn consume-messages [consumer]
  (while true
    (let [records (.poll consumer (Duration/ofMillis 100))]
      (doseq [record records]
        (log/info "Received message:" (.value record))))))

(defn start-consumer []
  (let [consumer (create-consumer)]
    (consume-messages consumer)))







































































;(ns clojure-general-ms.configs.kafka.listeners-configuration
;  (:gen-class)
;  (:require
;    [clojure.data.json :as json]
;    [clojure.java.io :as jio])
;  (:import
;    (java.time Duration)
;    (java.util Properties)
;    (org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer)))
;
;(defn- build-properties [config-fname]
;  (with-open [config (jio/reader config-fname)]
;    (doto (Properties.)
;      (.putAll {ConsumerConfig/GROUP_ID_CONFIG                 "clojure_example_group"
;                ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG   "org.apache.kafka.common.serialization.StringDeserializer"
;                ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG "org.apache.kafka.common.serialization.StringDeserializer"})
;      (.load config))))
;
;
;
;(defn consumer! [config-fname topic]
;  (with-open [consumer (KafkaConsumer. (build-properties config-fname))]
;    (.subscribe consumer [topic])
;    (loop [tc 0
;           records []]
;      (let [new-tc (reduce
;                     (fn [tc record]
;                       (let [value  (.value record)
;                             cnt    (get (json/read-str value) "count")
;                             new-tc (+ tc cnt)]
;                         (printf "Consumed record with key %s and value %s, and updated total count to %d\n"
;                                 (.key record)
;                                 value
;                                 new-tc)
;                         new-tc))
;                     tc
;                     records)]
;        (println "Waiting for message in KafkaConsumer.poll")
;        (recur new-tc
;               (seq (.poll consumer (Duration/ofSeconds 1))))))))
;
;(defn -main [& args]
;  (apply consumer! args))