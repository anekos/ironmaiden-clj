(ns ironmaiden.device.keyboard
  (:require [clojure.core.async :refer [close!]]
            [ironmaiden.device.core :refer [Device]]
            [ironmaiden.device.input-event :refer [make-reader-channel]]))


(defrecord Keyboard [name ch] Device
  (channel [this] ch)
  (stop
    [this]
    (close! ch)))


(defn make-keyboard [name path]
  (->Keyboard
    name
    (make-reader-channel path)))
