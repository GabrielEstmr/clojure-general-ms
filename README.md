# clojure-general-ms

## What's next

- [x] Entity validations
- [x] Kafka
- [ ] Postgres
- [ ] Http integration
- [ ] Swagger
- [ ] APM
- [x] tests 
- [ ] tests mocking
- [ ] tests intellij 
- [x] tests coverages (need exclusion)
- [ ] coverage exclusion
- [ ] container tests
- [ ] caching (redis)


lein cloverage for covarege report


















































In Kafka, offsets are typically committed sequentially, meaning when you commit an offset, all previous offsets are implicitly acknowledged. However, there are strategies to only acknowledge the last message in a batch, without committing the offsets of the previous messages. Here’s how you can do it:

### 1. **Manual Offset Management with `seek` Method:**
- Kafka allows you to manually manage offsets using the `seek` method. You can process a batch of messages and only commit the offset of the last message. To handle errors or retries, you can manually move the consumer’s offset back to the unacknowledged messages.

### Example Code:
```clojure
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

(defn consume-messages [^KafkaConsumer consumer]
  (loop []
    (let [records (.poll consumer 1000)]
      (when (seq records)
        (try
          (doseq [record records]
            (process-message record))  ; Process all records
          ; Commit only the last message
          (ack-last-message-only consumer (last records))
          (catch Exception e
            (println "Error handling message. Retrying entire batch."))))
    (recur))))

(defn start-consumer-thread []
  (let [consumer (create-consumer)]
    (Thread. (fn [] (consume-messages consumer)))
    (.start (Thread. (fn [] (consume-messages consumer))))))
```

### 2. **Reprocessing Messages:**
- If you only want to acknowledge the last message and reprocess the previous ones if an error occurs, you can skip committing the offsets for individual messages until the last one. Here’s how to handle it:
    - **Process the entire batch:** Process all messages in a batch, but don’t commit the offset for any except the last one.
    - **Seek on Error:** If an error occurs, use `seek` to manually rewind the consumer to the last uncommitted offset.

### 3. **Using the `seek` Method:**
- After processing a batch, you can use the `seek` method to move the consumer back to the first uncommitted offset before re-polling.

### Example with `seek` on Error:
```clojure
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
```

### Explanation:
- **`ack-last-message-only`:** This function commits only the offset of the last message in the batch.
- **`seek`:** If an error occurs, this method moves the consumer back to the first message in the batch, ensuring that uncommitted messages are reprocessed.

### 4. **Implications and Considerations:**
- **Increased Complexity:** Manually managing offsets adds complexity to your code, but it gives you fine-grained control over message acknowledgment.
- **Performance Trade-offs:** Reprocessing uncommitted messages can increase processing time and system load, especially if errors are frequent.

### Conclusion:
To acknowledge only the last message in a Kafka batch without acknowledging previous messages, you can use manual offset management with the `seek` method to selectively commit offsets. This approach ensures that only the last message is acknowledged, and any earlier messages that fail can be reprocessed.
