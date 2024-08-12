(ns clojure-general-ms.configs.kafka.listeners-configuration
  (:require [clojure-general-ms.configs.yml.yml-config :as yml-config]
            [clojure-general-ms.gateways.kafka.listeners.transaction-listener :as transaction-listener])
  (:import (java.util Collections Properties)
           (java.util.concurrent Executors)
           [org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer]
           (org.apache.kafka.common.serialization StringDeserializer)))

(defn create-consumer [topic]
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
    (.subscribe consumer (Collections/singletonList topic))
    consumer))

(defn start-listener [listener-starter topic ^Integer concurrency]
  (doseq [_ (range concurrency)]
    (let [consumer (create-consumer topic)]
      (listener-starter consumer))))


(defn start-listeners []
  (let [consumer (create-consumer (yml-config/get-by-path "kafka.topics.transactions.name"))]
    (try
      (while true
        (transaction-listener/update-transaction-listener consumer))
      (finally
        (.close consumer)))))



;(defn start-listeners []
;  (let [executor (Executors/newFixedThreadPool 2)]
;    (dotimes [_ 2]
;      (.submit executor (fn []
;                          (let [consumer (create-consumer (yml-config/get-by-path "kafka.topics.transactions.name"))]
;                            (transaction-listener/update-transaction-listener consumer)))))
;    (.shutdown executor)))


;To set concurrency for your Kafka consumers in Clojure using the Confluent library, ]
; you can create multiple consumer instances, each running in its own thread.
; You already have a function start-consumers, so you can modify it to start multiple consumers.
;(defn start-listeners []
;  (start-listener
;    transaction-listener/update-transaction-listener
;    (yml-config/get-by-path "kafka.topics.transactions.name")
;    (int (yml-config/get-by-path "kafka.topics.transactions.concurrency"))))
