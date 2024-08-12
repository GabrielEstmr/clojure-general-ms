(ns clojure-general-ms.gateways.kafka.listeners.transaction-listener
  (:require [clojure.tools.logging :as log]
            [clojure.data.json :as json]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [clojure-general-ms.gateways.kafka.listeners.resources.transaction-resource :as transaction-resource])
  (:import (java.time Duration)
           (java.util Collections)
           (org.apache.kafka.clients.consumer ConsumerRecord KafkaConsumer OffsetAndMetadata)
           (org.apache.kafka.common TopicPartition)
           (src.domains.exceptions InternalServerErrorException)))

(defn update-transaction-listener [consumer]
  (future
    (while true
      (try
        (let [usecaseCreateTransaction (:usecaseCreateTransaction (usecase-beans/get-beans))
              records (.poll consumer (Duration/ofMillis 100))]
          (doseq [record records]
            (let [msg (.value record)
                  transaction-resource (json/read-str msg :key-fn keyword)
                  transaction (transaction-resource/to-domain transaction-resource)]
              (log/info "Received message:" (.value record))
              (usecaseCreateTransaction transaction))))
        (catch Exception e
          (throw (InternalServerErrorException. "ERROR ===================================> ")))))))
;
;(defn update-transaction-listener [consumer]
;  (future
;    (loop []
;      (try
;        (let [usecaseCreateTransaction (:usecaseCreateTransaction (usecase-beans/get-beans))
;              records (.poll consumer (Duration/ofMillis 100))]
;          (doseq [record records]
;            (let [msg (.value record)
;                  transaction-resource (json/read-str msg :key-fn keyword)
;                  transaction (transaction-resource/to-domain transaction-resource)]
;              (log/info "Received message:" (.value record))
;              (usecaseCreateTransaction transaction))))
;        (catch Exception e
;          (log/error "ERROR ===================================> ")
;          ;; Wait for a moment before retrying
;
;          (throw (InternalServerErrorException. "ERROR ===================================> "))
;          (Thread/sleep 100)))
;
;      ;; Continue the loop to retry indefinitely
;      (recur))))
;
;
;
;
;
;
;
;
;






;(defn update-transaction-listener [consumer]
;  (loop [tc 0
;         records []]
;    (let [new-tc (reduce
;                   (fn [tc record]
;                     (let [value  (.value record)
;                           cnt    (json/read-str value)
;                           new-tc (+ tc cnt)]
;                       (printf "OK ========================Consumed record with key %s and value %s, and updated total count to %d\n"
;                               (.key record)
;                               value
;                               new-tc)
;                       new-tc))
;                   tc
;                   records)]
;      (println "Waiting for message in KafkaConsumer.poll")
;      (recur new-tc
;             (seq (.poll consumer (Duration/ofSeconds 100)))))))
;
;
;
;
;
;
;(defn update-transaction-listener [consumer]
;  (future
;    (loop []
;      (try
;        (let [usecaseCreateTransaction (:usecaseCreateTransaction (usecase-beans/get-beans))
;              records (.poll consumer (Duration/ofMillis 100))]
;          (doseq [record records]
;            (try
;              (let [msg (.value record)
;                    transaction-resource (json/read-str msg :key-fn keyword)
;                    transaction (transaction-resource/to-domain transaction-resource)]
;                (log/info "================================Received message:" (.value record))
;                ;; Process the transaction
;                (usecaseCreateTransaction transaction)
;                ;; Manually commit the offset only after successful processing
;                (.commitSync consumer (Collections/singletonMap (.topicPartition record) (.offsetAndMetadata record))))
;              (catch Exception e
;                (log/error e "ERROR ================================Error processing message, skipping commit for retry")))))
;        (catch Exception e
;          (log/error e "ERROR ================================Error in update-transaction-listener, retrying...")
;          ;; Wait for a moment before retrying
;          (Thread/sleep 10)))
;      ;; Continue the loop to retry indefinitely
;      (recur))))


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
































;(defn process-message [message consumer record]
;  (try
;    ;; Your message processing logic goes here
;    (println "Processing message:" message)
;    ;; Simulate success or error
;    (if (= message "error")
;      (throw (Exception. "Processing error"))
;      (println "Message processed successfully"))
;    ;; Return true if processed successfully
;
;    (.commitSync consumer (Collections/singletonMap (.topicPartition record) (.offsetAndMetadata record)))
;    (println "=========================== OK")
;    true
;    (catch Exception e
;      (println "Error processing message:" (.getMessage e))
;      ;; Return false to indicate failure
;      false)))

;(defn process-message [message consumer record]
;  (try
;    ;; Your message processing logic goes here
;    (println "Processing message:" message)
;    ;; Simulate success or error
;    (if (= message "error")
;      (throw (Exception. "Processing error"))
;      (println "Message processed successfully"))
;    ;; Return true if processed successfully
;    true
;    (catch Exception e
;      (println "Error processing message:" (.getMessage e))
;      ;; Return false to indicate failure
;      false)))

(defn handler-error-personalized [consumer record]
  (.commitSync consumer {(.topicPartition record) (.offsetAndMetadata record)})
  (println "ERROR FUNCTION")
  (throw (Exception. "Processing error")))


;
;
; (defn process-message [message consumer record]
;  (try
;    ;; Process the message
;    (println "Processing message:" message (.offset record))
;
;    ;; Simulate success or error
;    (if (.equals (str message) "error")
;      (handler-error-personalized consumer record))
;
;    (println "Message processed successfully")
;    ;; Acknowledge the message by committing the offset
;    (.commitSync consumer {(.topicPartition record) (.offsetAndMetadata record)})
;
;
;    (println "Message processed successfully 2")
;
;    ;; Return true to indicate success
;    true
;
;    (catch Exception e
;      (println "Error processing message:" (.getMessage e))
;      ;; Return false to indicate failure
;      false)))


(defn process-message [message ^KafkaConsumer consumer ^ConsumerRecord record]
  (try
    ;; Process the message
    (println "Processing message:" message "Offset:" (.offset record))

    ;; Simulate success or error
    (when (.equals (str message) "error")
      (handler-error-personalized consumer record))

    (println "Message processed successfully")

    ;; Acknowledge the message by committing the offset
    (let [partition (TopicPartition. (.topic record) (.partition record))
          offset (OffsetAndMetadata. (.offset record))]
      (.commitSync consumer {partition offset}))

    (println "Message processed successfully 2")

    ;; Return true to indicate success
    true

    (catch Exception e
      (println "Error processing message:" (.getMessage e))
      ;; Return false to indicate failure
      false)))


(defn update-transaction-listener [consumer]
  (future
    (while true
      (let [records (.poll consumer 100)]
        (doseq [record records]
          (let [message (.value record)
                processed? (process-message message consumer record)]
            (when processed?
              ;; Commit the offset if the message was processed successfully
              (.commitSync consumer))))))))


















(defn consume-messages [^KafkaConsumer consumer]
  (loop []
    (let [records (.poll consumer 1000)]
      (doseq [record records]
        (process-message (.value record) consumer record))
      (recur))))



















;
;
;(defn consume-with-retries [consumer max-retries]
;  (let [records (.poll consumer 1000)]
;    (doseq [record records]
;      (if (process-with-retries record max-retries)
;        (.commitSync consumer) ;; Commit the offset if processing succeeds
;        ;; Handle failed processing here (e.g., log, send to a dead-letter queue)
;        ))))
;
;(defn update-transaction-listener [consumer]
;  (consume-with-retries consumer 10000))