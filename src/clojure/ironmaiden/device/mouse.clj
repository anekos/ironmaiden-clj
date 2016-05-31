(ns ironmaiden.device.mouse
  (:require [ironmaiden.device.core :refer [Device]]
            [ironmaiden.device.common :refer [device-common-methods]]
            [ironmaiden.device.input-event :refer [make-reader-channel]]))


(defrecord Mouse [name ch])

(extend Mouse Device device-common-methods)

(defn make-mouse [name path]
  (->Mouse
    name
    (make-reader-channel path)))
