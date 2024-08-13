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

(defn process-and-log-error []
  (println "ERROR FUNC")
  (throw (RuntimeException. "ERROR")))

(defn commit-msg [message ^KafkaConsumer consumer ^ConsumerRecord record]
  (let [partition (TopicPartition. (.topic record) (.partition record))
        offset (OffsetAndMetadata. (inc (.offset record)))]
    (println "COMMIT")
    (println "Processing message:" message "Offset:" (.offset record) "Partition:" partition)
    (.commitSync consumer {partition offset})  ; Commit the offset
    (println "Message committed successfully:" message "Offset:" (.offset record) "Partition:" partition)))

(defn listen [^KafkaConsumer consumer ^ConsumerRecord record]
  (try
    (let [message (.value record)
          partition (TopicPartition. (.topic record) (.partition record))
          offset (OffsetAndMetadata. (inc (.offset record)))]

      (if (not= message "error")
        (commit-msg message consumer record)
        (process-and-log-error)))

    (catch Exception e
      (println "Error processing message:" (.getMessage e))
      (throw e))))  ; Rethrow the exception to handle the offset correctly