(ns ironmaiden.device.keyboard
  (:require [ironmaiden.device.core :refer [Device]]
            [ironmaiden.device.common :refer [device-common-methods]]
            [ironmaiden.device.input-event :refer [make-reader-channel]]))


(defrecord Keyboard [name ch])

(extend Keyboard Device device-common-methods)

(defn make-keyboard [name path]
  (->Keyboard
    name
    (make-reader-channel path)))