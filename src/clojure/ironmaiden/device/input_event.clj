(ns ironmaiden.device.input-event
  (:require [clojure.core.async :refer [>!! chan sliding-buffer thread]]
            [ironmaiden.device.core :refer [*buffer-size*]]
            [bytebuffer.buff :refer :all]))


(comment
 (define-alien-type
   nil
   (struct timeval
           (sec (unsigned 64))
           (usec (unsigned 64))))

 (define-alien-type
   nil
   (struct input-event-struct
           (time (struct timeval))
           (type (unsigned 16))
           (code (unsigned 16))
           (value (integer 32)))))


(defrecord InputEvent [sec usec type code value])


(defn read-input-event
  ([fin bb]
    (.read fin (.array bb))
    (let [result (read-input-event bb)]
      (.rewind bb)
      result))
  ([bb]
    (let [[sec usec type code value] (unpack bb "LLSSi")]
      (InputEvent. sec usec type code value))))


#_(defn input-event-seq [filepath]
  (let [fin (java.io.FileInputStream. filepath)
        bb (byte-buffer 24)]
    (letfn [(read1 []
              (cons
                (read-input-event fin bb)
                (lazy-seq (read1))))]
      read1)))


(defn make-reader-channel [filepath]
  (let [fin (java.io.FileInputStream. filepath)
        bb (byte-buffer 24)
        ch (chan (sliding-buffer @*buffer-size*))]
    (thread
      (try
        (while (>!! ch (read-input-event fin bb)))
        (finally
          (.close fin))))
    ch))
