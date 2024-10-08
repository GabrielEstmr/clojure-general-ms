(ns clojure-general-ms.configs.kafka.listener2
  (:require [clojure-general-ms.configs.yml.yml-config :as yml-config])
  (:import (java.util Properties)
           [org.apache.kafka.clients.consumer KafkaConsumer ConsumerRecord]
           [org.apache.kafka.common TopicPartition]
           [org.apache.kafka.clients.consumer ConsumerConfig ConsumerRecords OffsetAndMetadata]
           [java.util.concurrent Executors]
           (org.apache.kafka.common.serialization StringDeserializer)))

;; DO not Remove
;(defn create-consumer []
;  (let [props (doto (Properties.)
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
;
;    (if (not= message "erro")
;
;      (let [partition (TopicPartition. (.topic record) (.partition record))
;            offset (OffsetAndMetadata. (inc (.offset record)))]
;        (println "Processing message:" message "Offset:" (.offset record) "Partition:" partition)
;        (.commitSync consumer {partition offset})  ; Commit the offset
;        (println "Message committed successfully:" message "Offset:" (.offset record) "Partition:" partition))
;
;      ;(let [partition (TopicPartition. (.topic record) (.partition record))
;      ;      offset (OffsetAndMetadata. (inc (.offset record)))]  ; Offset should be committed as the next offset
;      ;  (.commitSync consumer {partition offset}))
;      (throw (RuntimeException. "ERROR")))
;
;
;
;
;    (catch Exception e
;      (println "Error processing message:" (.getMessage e)))))
;
;(defn consume-messages [^KafkaConsumer consumer]
;  (loop []
;    (let [records (.poll consumer 1000)]
;      (when (seq records)
;        (doseq [record records]
;          (process-message (.value record) consumer record)))
;      (recur))))
;
;(defn start-consumer-thread []
;  (let [consumer (create-consumer)]
;    (Thread. (fn [] (consume-messages consumer)))  ; Start consumer in a new thread
;    (.start (Thread. (fn [] (consume-messages consumer)))))) ; Ensure the thread starts



;(defn create-consumer []
;  (let [props (doto (Properties.)
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

;(defn process-message [^KafkaConsumer consumer ^ConsumerRecord record]
;  (try
;    (let [message (.value record)]
;      (if (not= message "erro")
;
;        (let [partition (TopicPartition. (.topic record) (.partition record))
;              offset (OffsetAndMetadata. (inc (.offset record)))]
;          (println "Processing message:" message "Offset:" (.offset record) "Partition:" partition)
;          (.commitSync consumer {partition offset})  ; Commit the offset
;          (println "Message committed successfully:" message "Offset:" (.offset record) "Partition:" partition))
;
;        ;(let [partition (TopicPartition. (.topic record) (.partition record))
;        ;      offset (OffsetAndMetadata. (inc (.offset record)))]  ; Offset should be committed as the next offset
;        ;  (.commitSync consumer {partition offset}))
;        (throw (RuntimeException. "ERROR"))))
;    (catch Exception e
;      (println "Error processing message:" (.getMessage e)))))
;
;(defn consume-messages [^KafkaConsumer consumer]
;  (loop []
;    (let [records (.poll consumer 1000)]
;      (when (seq records)
;        (doseq [record records]
;          (process-message consumer record)))
;      (recur))))
;
;(defn start-consumer-thread []
;  (let [consumer (create-consumer)]
;    (Thread. (fn [] (consume-messages consumer)))  ; Start consumer in a new thread
;    (.start (Thread. (fn [] (consume-messages consumer)))))) ; Ensure the thread starts
;
;
;
;
;
;
;
;
;
;
;
;(defn case-ok []
;  (println "caseOk"))
;
;(defn case-not-ok []
;  (println "caseNotOk"))
;
;(defn execute [input]
;  (if (= input "OK")
;    (case-ok)
;    (case-not-ok)))
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;
;


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


;(defn process-message [^KafkaConsumer consumer ^ConsumerRecord record]
;  (try
;    (let [message (.value record)
;          partition (TopicPartition. (.topic record) (.partition record))
;          offset (OffsetAndMetadata. (inc (.offset record)))]
;
;      (if (not= message "error")
;        (do
;          (println "Processing message:" message "Offset:" (.offset record) "Partition:" partition)
;          (.commitSync consumer {partition offset})  ; Commit the offset
;          (println "Message committed successfully:" message "Offset:" (.offset record) "Partition:" partition))
;        (throw (RuntimeException. "ERROR"))))
;
;    (catch Exception e
;      (println "Error processing message:" (.getMessage e))
;      (throw e))))  ; Rethrow the exception to handle the offset correctly
;
;(defn consume-messages [^KafkaConsumer consumer]
;  (loop []
;    (let [records (.poll consumer 1000)]
;      (when (seq records)
;        (doseq [record records]
;          (try
;            (process-message consumer record)
;            (catch Exception e
;              (println "Error handling message. Will retry on next poll cycle.")))))
;      (recur))))
;
;(defn start-consumer-thread []
;  (let [consumer (create-consumer)]
;    (Thread. (fn [] (consume-messages consumer)))
;    (.start (Thread. (fn [] (consume-messages consumer))))))  ; Ensure the thread starts


;(defn process-message [^ConsumerRecord record]
;  (let [message (.value record)]
;    (if (not= message "error")
;      (do
;        (println "Processing message:" message "Offset:" (.offset record) "Partition:" (.partition record)))
;      (throw (RuntimeException. "ERROR")))))
;
;(defn consume-messages [^KafkaConsumer consumer]
;  (loop []
;    (let [records (.poll consumer 1000)]
;      (when (seq records)
;        (try
;          (doseq [record records]
;            (process-message record))
;          ; Commit the offset only after all messages in the batch are processed
;          (let [last-record (last records)
;                partition (TopicPartition. (.topic last-record) (.partition last-record))
;                offset (OffsetAndMetadata. (inc (.offset last-record)))]
;            (.commitSync consumer {partition offset})
;            (println "Committed offset for batch up to:" (.offset last-record)))
;          (catch Exception e
;            (println "Error handling message. Will retry on next poll cycle.")))))
;    (recur)))
;
;(defn start-consumer-thread []
;  (let [consumer (create-consumer)]
;    (Thread. (fn [] (consume-messages consumer)))
;    (.start (Thread. (fn [] (consume-messages consumer))))))







(defn process-message [^ConsumerRecord record]
  (let [message (.value record)]
    (if (not= message "error")
      (println "Processing message:" message "Offset:" (.offset record) "Partition:" (.partition record))
      (throw (RuntimeException. "ERROR")))))

(defn ack-last-message-only [^KafkaConsumer consumer ^ConsumerRecord last-record]
  (let [partition (TopicPartition. (.topic last-record) (.partition last-record))
        offset (OffsetAndMetadata. (inc (.offset last-record)))]
    (.commitSync consumer {partition offset})
    (println "Committed offset for message:" (.offset last-record))))

(defn process-messages [^KafkaConsumer consumer ^ConsumerRecords records]
  (try
    (doseq [record records]
      (process-message record))
    ; Commit only the last offset
    (ack-last-message-only consumer (last records))
    (catch Exception e
      (println "Error occurred, seeking to the last uncommitted offset.")
      (let [first-record (first records)]
        (.seek consumer (TopicPartition. (.topic first-record) (.partition first-record)) (.offset first-record)))
      (throw e))))  ; Rethrow to trigger retry on the next poll

(defn consume-messages [^KafkaConsumer consumer]
  (loop []
    (let [records (.poll consumer 1000)]
      (when (seq records)
        (try
          ;(doseq [record records]
          ;  (process-message record))  ; Process all records
          ; Commit only the last message
          (process-messages consumer records)
          (catch Exception e
            ;(println "Error handling message. Retrying entire batch.")
            ;(throw e)
            )))
      (recur))))

(defn start-consumer-thread []
  (let [consumer (create-consumer)]
    (.start (Thread. (fn [] (consume-messages consumer))))))
