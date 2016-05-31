(ns ironmaiden.device.common
  (:require [clojure.core.async :refer [close!]]
            [ironmaiden.device.core :refer [Device]]
            [ironmaiden.device.input-event :refer [make-reader-channel]]))


(def device-common-methods
  {:stop (fn [{ch :ch}]
           (close! ch))
   :channel :ch})
