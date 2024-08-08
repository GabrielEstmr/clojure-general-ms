(ns clojure-general-ms.configs.kafka.listeners-configuration
  (:require [clojure.tools.logging :as log]
            [clojure-general-ms.configs.yml.yml-config :as yml-config])
  (:import (java.time Duration)
           (java.util Collections Properties)
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
                (.put ConsumerConfig/MAX_POLL_INTERVAL_MS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.max-poll-interval-ms"))))
        consumer (KafkaConsumer. props)]
    (.subscribe consumer (Collections/singletonList topic))
    consumer))

(defn listen-topic [consumer]
  (future
    (while true
      (let [
            records (.poll consumer (Duration/ofMillis 100))]
        (doseq [record records]
          (log/info "Received message:" (.value record)))))))

(defn start-listener [topic ^Integer concurrency]
  (doseq [_ (range concurrency)]
    (let [consumer (create-consumer topic)]
      (listen-topic consumer))))

(defn start-consumers []
  (start-listener
    (yml-config/get-by-path "kafka.topics.transactions.name")
    (int (yml-config/get-by-path "kafka.topics.transactions.concurrency"))))

;To set concurrency for your Kafka consumers in Clojure using the Confluent library, ]
; you can create multiple consumer instances, each running in its own thread.
; You already have a function start-consumers, so you can modify it to start multiple consumers.
