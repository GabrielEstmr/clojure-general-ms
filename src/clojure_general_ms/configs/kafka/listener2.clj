(ns clojure-general-ms.configs.kafka.listener2
  (:require [clojure-general-ms.configs.yml.yml-config :as yml-config])
  (:import (java.util Properties)
           [org.apache.kafka.clients.consumer KafkaConsumer ConsumerRecord]
           [org.apache.kafka.common TopicPartition]
           [org.apache.kafka.clients.consumer ConsumerConfig OffsetAndMetadata]
           [java.util.concurrent Executors]
           (org.apache.kafka.common.serialization StringDeserializer)))

;(defn create-consumer []
;  (let [props (doto (Properties.)
;
;                (.put ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.bootstrap-servers"))
;                (.put ConsumerConfig/GROUP_ID_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.group-id"))
;                (.put ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG StringDeserializer)
;                (.put ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG StringDeserializer)
;                (.put ConsumerConfig/AUTO_OFFSET_RESET_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.auto-offset-reset"))
;                (.put ConsumerConfig/MAX_POLL_RECORDS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.max-poll-records")))
;                (.put ConsumerConfig/SESSION_TIMEOUT_MS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.session-timeout-ms")))
;                (.put ConsumerConfig/MAX_POLL_INTERVAL_MS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.max-poll-interval-ms")))
;                (.put ConsumerConfig/ENABLE_AUTO_COMMIT_CONFIG "false"))
;        consumer (KafkaConsumer. props)]
;    (.subscribe consumer [(yml-config/get-by-path "kafka.topics.transactions.name")])
;    consumer))
;
;(defn process-message [message ^KafkaConsumer consumer ^ConsumerRecord record]
;  (try
;    ;; Same process-message function as before
;    (println "MSG: " message "OFFSET: " (.offset record) "PARTITION: " (TopicPartition. (.topic record) (.partition record)))
;    (let [partition (TopicPartition. (.topic record) (.partition record))
;          offset (OffsetAndMetadata. (.offset record))]
;      (.commitSync consumer {partition offset}))
;    (println "MSG OK: " message "OFFSET: " (.offset record) "PARTITION: " (TopicPartition. (.topic record) (.partition record)))
;    (catch Exception e
;      (println "ERROR: " (.getMessage e)))))
;
;(defn consume-messages [^KafkaConsumer consumer]
;  (loop []
;    (let [records (.poll consumer 1000)]
;      (doseq [record records]
;        (process-message (.value record) consumer record))
;      (recur))))
;
;(defn start-consumer-thread []
;  (let [consumer (create-consumer)]
;    (.start (Thread. (fn [] (consume-messages consumer))))))





(defn create-consumer []
  (let [props (doto (Properties.)
                (.put ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.bootstrap-servers"))
                (.put ConsumerConfig/GROUP_ID_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.group-id"))
                (.put ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG StringDeserializer)
                (.put ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG StringDeserializer)
                (.put ConsumerConfig/AUTO_OFFSET_RESET_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.auto-offset-reset"))
                (.put ConsumerConfig/MAX_POLL_RECORDS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.max-poll-records")))
                (.put ConsumerConfig/SESSION_TIMEOUT_MS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.session-timeout-ms")))
                (.put ConsumerConfig/MAX_POLL_INTERVAL_MS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.max-poll-interval-ms")))
                (.put ConsumerConfig/ENABLE_AUTO_COMMIT_CONFIG "false"))
        consumer (KafkaConsumer. props)]
    (.subscribe consumer [(yml-config/get-by-path "kafka.topics.transactions.name")])
    consumer))

(defn process-message [message ^KafkaConsumer consumer ^ConsumerRecord record]
  (try

    (if (not= message "erro")

      (let [partition (TopicPartition. (.topic record) (.partition record))
            offset (OffsetAndMetadata. (inc (.offset record)))]
        (println "Processing message:" message "Offset:" (.offset record) "Partition:" partition)
        (.commitSync consumer {partition offset})  ; Commit the offset
        (println "Message committed successfully:" message "Offset:" (.offset record) "Partition:" partition))

      ;(let [partition (TopicPartition. (.topic record) (.partition record))
      ;      offset (OffsetAndMetadata. (inc (.offset record)))]  ; Offset should be committed as the next offset
      ;  (.commitSync consumer {partition offset}))
      (throw (RuntimeException. "ERROR")))




    (catch Exception e
      (println "Error processing message:" (.getMessage e)))))

(defn consume-messages [^KafkaConsumer consumer]
  (loop []
    (let [records (.poll consumer 1000)]
      (when (seq records)
        (doseq [record records]
          (process-message (.value record) consumer record)))
      (recur))))

(defn start-consumer-thread []
  (let [consumer (create-consumer)]
    (Thread. (fn [] (consume-messages consumer)))  ; Start consumer in a new thread
    (.start (Thread. (fn [] (consume-messages consumer)))))) ; Ensure the thread starts
