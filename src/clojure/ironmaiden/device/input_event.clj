(ns ironmaiden.device.input-event
  (:import (ironmaiden Native))
  (:require [clojure.core.async :refer [>!! chan sliding-buffer thread]]
            [bytebuffer.buff :refer :all]
            [ironmaiden.show :refer [Show]]
            [ironmaiden.constants :refer :all]
            [ironmaiden.device.core :refer [*buffer-size*]]))


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


(defrecord InputEvent [sec usec type code value] Show
  (show
    [{type :type code :code value :value}]
    (str "<InputEvent "
         (get-in value-to-symbol [:ev type] type)
         " "
         (get-in value-to-symbol [:key code] code)
         " "
         value
         ">")))


(defn read-input-event
  ([fd bb]
    (if (< 0 (ironmaiden.Native/readEvent fd (.array bb)))
      (let [result (read-input-event bb)]
        (.rewind bb)
        result)
      (throw (Exception. "Could not read from the device"))))
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
  (let [fd (ironmaiden.Native/setupDevice filepath)
        bb (byte-buffer 24)
        ch (chan (sliding-buffer @*buffer-size*))]
    (thread
      (try
        (while (>!! ch (read-input-event fd bb)))
        (finally
          (ironmaiden.Native/closeDevice fd))))
    ch))
