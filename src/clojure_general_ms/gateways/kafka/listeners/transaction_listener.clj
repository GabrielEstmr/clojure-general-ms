(ns clojure-general-ms.gateways.kafka.listeners.transaction-listener
  (:require [clojure.tools.logging :as log]
            [clojure.data.json :as json]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [clojure-general-ms.gateways.kafka.listeners.resources.transaction-resource :as transaction-resource])
  (:import (java.time Duration)
           (java.util Collections)
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
































(defn process-message [message]
  (try
    ;; Your message processing logic goes here
    (println "Processing message:" message)
    ;; Simulate success or error
    (if (= message "error")
      (throw (Exception. "Processing error"))
      (println "Message processed successfully"))
    ;; Return true if processed successfully
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
                processed? (process-message message)]
            (when processed?
              ;; Commit the offset if the message was processed successfully
              (.commitSync consumer))))))))

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